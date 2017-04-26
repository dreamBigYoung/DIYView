package com.example.bigyoung.diyview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bigyoung.diyview.R;
import com.example.bigyoung.diyview.bean.HomeResponseBean;
import com.example.bigyoung.diyview.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BigYoung on 2017/4/25.
 */

public class HomeFragAdapter extends RecyclerView.Adapter {
    private List<HomeResponseBean.ItemHomeBean> mItemList;
    private Context mContext;

    public HomeFragAdapter(Context context) {
        this(context, null);
    }

    public HomeFragAdapter(Context context, List<HomeResponseBean.ItemHomeBean> mItemList) {
        this.mContext = context;
        this.mItemList = mItemList;
    }

    /**
     * 更新homeBean
     *
     * @param mItemList
     */
    public void updateHomeBean(List<HomeResponseBean.ItemHomeBean> mItemList) {
        this.mItemList = mItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_home, parent, false);
        HomeViewHolder holder = new HomeViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeResponseBean.ItemHomeBean bean = mItemList.get(position);
        HomeViewHolder homeHolder=(HomeViewHolder)holder;
        homeHolder.mItemAppinfoTvTitle.setText(bean.getName());
        homeHolder.mItemAppinfoTvSize.setText(StringUtils.formatFileSize(bean.getSize()));
        homeHolder.mItemAppinfoRbStars.setRating(bean.getStars());
        homeHolder.mItemAppinfoTvDes.setText(bean.getDes());
    }

    @Override
    public int getItemCount() {
        if (mItemList != null) {
            return mItemList.size();
        }
        return 0;
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
}
