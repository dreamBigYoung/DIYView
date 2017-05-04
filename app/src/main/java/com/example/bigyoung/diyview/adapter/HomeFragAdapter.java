package com.example.bigyoung.diyview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bigyoung.diyview.R;
import com.example.bigyoung.diyview.base.MyApplication;
import com.example.bigyoung.diyview.bean.HomeResponseBean;
import com.example.bigyoung.diyview.bean.ItemBean;
import com.example.bigyoung.diyview.protocol.HomeFragProtocol;
import com.example.bigyoung.diyview.utils.Constants;
import com.example.bigyoung.diyview.utils.FixUrl;
import com.example.bigyoung.diyview.utils.StringUtils;
import com.example.bigyoung.diyview.views.LoadingPager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BigYoung on 2017/4/25.
 */

public class HomeFragAdapter extends RecyclerView.Adapter {
    private List<ItemBean> mItemList;
    private Context mContext;
    private static final int ITEMTYPE_HEAD = 1;
    private static final int ITEMTYPE_BODY = 2;
    private static final int ITEMTYPE_FOOT = 3;

    private HomeResponseBean mHomeBeanInstance;//存放调用加载更多获得的结果集
    private int mLoadMoreState = LoadingPager.FAILED_STATE;//加载更多的操作结果

    public HomeFragAdapter(Context context) {
        this(context, null);
    }

    public HomeFragAdapter(Context context, List<ItemBean> mItemList) {
        this.mContext = context;
        this.mItemList = mItemList;
    }

    @Override
    public int getItemViewType(int position) {
        if (mItemList != null) {
            int size = mItemList.size();
            if (position == 0)
                return ITEMTYPE_HEAD;//头布局
            if (position == (getItemCount() - 1))
                return ITEMTYPE_FOOT;//尾布局
            return ITEMTYPE_BODY;
        }
        return super.getItemViewType(position);
    }

    /**
     * 更新homeBean
     *
     * @param mItemList
     */
    public void updateHomeBean(List<ItemBean> mItemList) {
        this.mItemList = mItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEMTYPE_HEAD:
                View headView = LayoutInflater.from(mContext).inflate(R.layout.item_head_view, parent, false);
                ViewHolderHead headHolder = new ViewHolderHead(headView);
                return headHolder;
            case ITEMTYPE_BODY:
                View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_home, parent, false);
                HomeViewHolder holder = new HomeViewHolder(inflate);
                return holder;
            case ITEMTYPE_FOOT:
                View loadMore = LayoutInflater.from(mContext).inflate(R.layout.item_loadmore, parent, false);
                ViewHolderLoadMore loadMoreHolder = new ViewHolderLoadMore(loadMore);
                return loadMoreHolder;
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int holdType = getItemViewType(position);
        switch (holdType) {
            case ITEMTYPE_HEAD:
                ViewHolderHead headHolder = (ViewHolderHead) holder;
                break;
            case ITEMTYPE_BODY:
                fixBodyHolder((HomeViewHolder) holder, position);
                break;
            case ITEMTYPE_FOOT:
                if (mLoadMoreState != LoadingPager.EMPTY_STATE&&mLoadMoreState!=LoadingPager.LONDING_STATE)//如果不为空，则尝试再次加载更多
                    operationLoadMore(holder);
                break;
        }
    }

    /**
     * 加载更多
     * @param holder
     */
    private void operationLoadMore(RecyclerView.ViewHolder holder) {
        final ViewHolderLoadMore loadMoreHolder = (ViewHolderLoadMore) (holder);
        //点击事件
        loadMoreHolder.mItemLoadmoreTvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击后开启加载任务
                operationLoadMore(loadMoreHolder);
            }
        });
        //不可触摸
        loadMoreHolder.mItemLoadmoreTvRetry.setClickable(false);
        //开启加载任务
        loadMoreHolder.mItemLoadmoreContainerRetry.setVisibility(View.GONE);
        loadMoreHolder.mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
        new Thread(new ConnectForMoreData(loadMoreHolder)).start();
    }

    /**
     * @param holder
     * @param position
     */
    private void fixBodyHolder(HomeViewHolder holder, int position) {
        ItemBean bean = mItemList.get(position - 1);
        HomeViewHolder homeHolder = holder;
        homeHolder.mItemAppinfoTvTitle.setText(bean.getName());
        homeHolder.mItemAppinfoTvSize.setText(StringUtils.formatFileSize(bean.getSize()));
        homeHolder.mItemAppinfoRbStars.setRating(bean.getStars());
        homeHolder.mItemAppinfoTvDes.setText(bean.getDes());
        Picasso.with(mContext).load(FixUrl.fixUrlForImg(bean.getIconUrl())).into(homeHolder.mItemAppinfoIvIcon);
    }

    @Override
    public int getItemCount() {
        if (mItemList != null) {
            return mItemList.size() + 2;//头布局与脚布局
        }
        return 0;
    }

    /**
     * 处理加载更多的结果
     */
    private void operationForLoadMoreResult(ViewHolderLoadMore loadMoreHolder) {
        switch (mLoadMoreState) {
            case LoadingPager.SUCCESS_STATE:
                if (mHomeBeanInstance.getList().size() < Constants.PAGE_SIZE) {
                    loadMoreHolder.mItemLoadmoreContainerLoading.setVisibility(View.GONE);
                    loadMoreHolder.mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                    loadMoreHolder.mItemLoadmoreTvRetry.setText(R.string.no_more_data);
                    //置为已无更多数据
                    mLoadMoreState = LoadingPager.EMPTY_STATE;
                } else {
                    loadMoreHolder.mItemLoadmoreContainerLoading.setVisibility(View.GONE);
                    loadMoreHolder.mItemLoadmoreContainerRetry.setVisibility(View.GONE);
                }
                //更新加载数据
                mItemList.addAll(mHomeBeanInstance.getList());
                notifyDataSetChanged();
                break;
            case LoadingPager.FAILED_STATE:
                loadMoreHolder.mItemLoadmoreContainerLoading.setVisibility(View.GONE);
                loadMoreHolder.mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                loadMoreHolder.mItemLoadmoreTvRetry.setText(R.string.load_data_error);
                //可以触摸
                loadMoreHolder.mItemLoadmoreTvRetry.setClickable(true);
                break;
            case LoadingPager.EMPTY_STATE:
                loadMoreHolder.mItemLoadmoreContainerLoading.setVisibility(View.GONE);
                loadMoreHolder.mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                loadMoreHolder.mItemLoadmoreTvRetry.setText(R.string.no_more_data);
                break;
            default:
                break;
        }
    }

    private class ConnectForMoreData implements Runnable {
        ViewHolderLoadMore loadMoreHolder;

        public ConnectForMoreData(ViewHolderLoadMore loadMoreHolder) {
            this.loadMoreHolder = loadMoreHolder;
        }

        @Override
        public void run() {
            mLoadMoreState = startLoadMore();
            MyApplication.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    operationForLoadMoreResult(loadMoreHolder);
                }
            });
        }

        private int startLoadMore() {
            //开始着手加载
            //执行请求
            HomeFragProtocol homePro = new HomeFragProtocol();
            try {
                //获得待加载的页码
                int nextPageIndex = mItemList.size() / Constants.PAGE_SIZE;
                mHomeBeanInstance = homePro.loadData(nextPageIndex);
                //判断获得数据是否为空
                if (mHomeBeanInstance == null || mHomeBeanInstance.getList() == null || mHomeBeanInstance.getList().size() == 0)
                    return LoadingPager.EMPTY_STATE;
                return LoadingPager.SUCCESS_STATE;
            } catch (IOException e) {
                e.printStackTrace();
                return LoadingPager.FAILED_STATE;
            }
        }
    }

    static class HomeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_appinfo_iv_icon)
        ImageView mItemAppinfoIvIcon;
        @BindView(R.id.item_appinfo_tv_title)
        TextView mItemAppinfoTvTitle;
        @BindView(R.id.item_appinfo_rb_stars)
        RatingBar mItemAppinfoRbStars;
        @BindView(R.id.item_appinfo_tv_size)
        TextView mItemAppinfoTvSize;
        @BindView(R.id.item_appinfo_tv_des)
        TextView mItemAppinfoTvDes;

        HomeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderLoadMore extends RecyclerView.ViewHolder {
        @BindView(R.id.item_loadmore_container_loading)
        LinearLayout mItemLoadmoreContainerLoading;
        @BindView(R.id.item_loadmore_tv_retry)
        TextView mItemLoadmoreTvRetry;
        @BindView(R.id.item_loadmore_container_retry)
        LinearLayout mItemLoadmoreContainerRetry;

        ViewHolderLoadMore(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderHead extends RecyclerView.ViewHolder {
        @BindView(R.id.head_text)
        TextView mHeadText;

        ViewHolderHead(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
