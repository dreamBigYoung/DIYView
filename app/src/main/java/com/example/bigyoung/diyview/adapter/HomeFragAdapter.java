package com.example.bigyoung.diyview.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.bigyoung.diyview.R;
import com.example.bigyoung.diyview.base.BaseProtocol;
import com.example.bigyoung.diyview.base.MyApplication;
import com.example.bigyoung.diyview.base.SuperBaseAdapter;
import com.example.bigyoung.diyview.bean.HomeResponseBean;
import com.example.bigyoung.diyview.bean.ItemBean;
import com.example.bigyoung.diyview.utils.Constants;
import com.example.bigyoung.diyview.utils.FixUrl;
import com.example.bigyoung.diyview.utils.UIUtils;
import com.example.bigyoung.diyview.views.ChildViewPager;
import com.example.bigyoung.diyview.views.LoadingPager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BigYoung on 2017/4/25.
 */

public class HomeFragAdapter extends SuperBaseAdapter<HomeResponseBean> {
    private List<String> mPictures;//轮播图显示图片

    private HomeResponseBean mHomeBeanInstance;//存放调用加载更多获得的结果集
    private int mLoadMoreState = LoadingPager.FAILED_STATE;//加载更多的操作结果

    public HomeFragAdapter(Context context, BaseProtocol<HomeResponseBean> baseProtocol, List<String> pictures) {
        this(context, null, baseProtocol, pictures);
    }

    public HomeFragAdapter(Context context, List<ItemBean> mItemList, BaseProtocol baseProtocol, List<String> pictures) {
        super(context, mItemList, baseProtocol);
        this.mPictures = pictures;
    }

    public void setPictures(List<String> pictures) {
        mPictures = pictures;
    }

    /**
     * 获得加载更多的结果集
     *
     * @param nextPageIndex
     * @return
     * @throws IOException
     */
    @Override
    public List<ItemBean> getLoadMoreList(int nextPageIndex, BaseProtocol<HomeResponseBean> protocol) throws IOException {
        HomeResponseBean homeResponseBean = protocol.loadData(nextPageIndex);
        if (homeResponseBean != null)
            return homeResponseBean.getList();
        return null;
    }

    @Override
    public RecyclerView.ViewHolder getHeadViewHolder(ViewGroup parent) {
        View headView = LayoutInflater.from(mContext).inflate(R.layout.item_home_pictures, parent, false);
        ViewHolderHead headHolder = new ViewHolderHead(headView);
        return headHolder;
    }

    @Override
    protected void fixHeadViewHolder(RecyclerView.ViewHolder holder) {
        ViewHolderHead headHolder = (ViewHolderHead) holder;
        headHolder.refreshHolderView(mPictures);
    }


    static class ViewHolderHead extends RecyclerView.ViewHolder {
        @BindView(R.id.item_home_picture_pager)
        ChildViewPager mItemHomePicturePager;
        @BindView(R.id.item_home_picture_container_indicator)
        LinearLayout mItemHomePictureContainerIndicator;
        private List<String> mPictureUrls;

        /**
         * 自动轮播的Task
         */
        private AutoScrollTask mAutoScrollTask;

        ViewHolderHead(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        /**
         * 数据和视图的绑定
         */
        public void refreshHolderView(List<String> pictureUrls) {
            //保存数据集到成员变量中
            mPictureUrls = pictureUrls;

        /*--------------- mItemHomePicturePager绑定 ---------------*/
            //view->mItemHomePicturePager
            //data-->局部变量
            //data+view
            mItemHomePicturePager.setAdapter(new HomePicturePagerAdapter());
            if (mPictureUrls == null)
                return;
        /*---------------mItemHomePictureContainerIndicator绑定  ---------------*/
            for (int i = 0; i < mPictureUrls.size(); i++) {
                ImageView ivIndicator = new ImageView(UIUtils.getContext());
                //设置默认时候的点的src
                ivIndicator.setImageResource(R.drawable.indicator_normal);

                //选择默认选中第一个点
                if (i == 0) {
                    ivIndicator.setImageResource(R.drawable.indicator_selected);
                }
//            int sixDp = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6,
//                    UIUtils.getResources().getDisplayMetrics()) + .5f);

                int width = UIUtils.dip2Px(6);//6dp
                int height = UIUtils.dip2Px(6);//6dp
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
                params.leftMargin = UIUtils.dip2Px(6);//6dp
                params.bottomMargin = UIUtils.dip2Px(6);//6dp

                mItemHomePictureContainerIndicator.addView(ivIndicator, params);
            }

            //监听ViewPager的页面切换操作
            mItemHomePicturePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    //处理position
                    position = position % mPictureUrls.size();

                    //控制Indicator选中效果
                    for (int i = 0; i < mPictureUrls.size(); i++) {
                        ImageView ivIndicator = (ImageView) mItemHomePictureContainerIndicator.getChildAt(i);
                        //1.还原默认效果
                        ivIndicator.setImageResource(R.drawable.indicator_normal);
                        //2.选中应该选中的
                        if (position == i) {
                            ivIndicator.setImageResource(R.drawable.indicator_selected);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            //设置viewPager页面的初始位置
            int curItem = Integer.MAX_VALUE / 2;
            //对curItem做偏差处理
            int diff = Integer.MAX_VALUE / 2 % mPictureUrls.size();
            curItem = curItem - diff;
            mItemHomePicturePager.setCurrentItem(curItem);
//        mItemHomePicturePager.setCurrentItem(mPictureUrls.size() * 1000);

            //实现自动轮播
            if (mAutoScrollTask == null) {
                mAutoScrollTask = new AutoScrollTask();
                mAutoScrollTask.start();
            }

            //按下去的时候停止轮播
            mItemHomePicturePager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mAutoScrollTask.stop();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            mAutoScrollTask.stop();
                            break;
                        case MotionEvent.ACTION_UP:
                            mAutoScrollTask.start();
                            break;
                    }
                    return false;
                }
            });
        }

        /**
         * 实现自动轮播的Runnable对象
         */
        class AutoScrollTask implements Runnable {
            /**
             * 开始滚动
             */
            public void start() {
                stop();
                MyApplication.getMainThreadHandler().postDelayed(this, 3000);
            }

            /**
             * 结束滚动
             */
            public void stop() {
                MyApplication.getMainThreadHandler().removeCallbacks(this);
            }

            @Override
            public void run() {
                //切换ViewPager
                int currentItem = mItemHomePicturePager.getCurrentItem();
                currentItem++;
                mItemHomePicturePager.setCurrentItem(currentItem);
                start();
            }
        }

        class HomePicturePagerAdapter extends PagerAdapter {

            @Override
            public int getCount() {
                if (mPictureUrls != null) {
//                return mPictureUrls.size();
                    return Integer.MAX_VALUE;
                }
                return 0;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                //处理position
                position = position % mPictureUrls.size();

                //view
                ImageView iv = new ImageView(UIUtils.getContext());
                iv.setScaleType(ImageView.ScaleType.FIT_XY);

                //data
                String url = mPictureUrls.get(position);

                //data+view
                Picasso.with(UIUtils.getContext()).load(FixUrl.fixUrlForImg(url)).into(iv);

                //把view加入到容器中
                container.addView(iv);

                //返回具体的view
                return iv;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        }
    }

}
