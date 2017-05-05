package com.example.bigyoung.diyview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bigyoung.diyview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BigYoung on 2017/5/4.
 */
public class ItemBeanViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_appinfo_iv_icon)
    public ImageView mItemAppinfoIvIcon;
    @BindView(R.id.item_appinfo_tv_title)
    public TextView mItemAppinfoTvTitle;
    @BindView(R.id.item_appinfo_rb_stars)
    public RatingBar mItemAppinfoRbStars;
    @BindView(R.id.item_appinfo_tv_size)
    public TextView mItemAppinfoTvSize;
    @BindView(R.id.item_appinfo_tv_des)
    public TextView mItemAppinfoTvDes;

    public ItemBeanViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
