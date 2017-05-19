package com.example.bigyoung.diyview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bigyoung.diyview.R;
import com.example.bigyoung.diyview.base.MyBaseAdapter;
import com.example.bigyoung.diyview.bean.SubjectBean;
import com.example.bigyoung.diyview.utils.FixUrl;
import com.example.bigyoung.diyview.utils.UIUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BigYoung on 2017/5/5.
 */

public class SubjectFragAdapter extends MyBaseAdapter<SubjectBean> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_subject, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myHolder=(MyViewHolder)holder;
        SubjectBean subjectBean = mListBeans.get(position);
        myHolder.mItemSubjectTvTitle.setText(subjectBean.getDes());
        Picasso.with(mContext).load(FixUrl.fixUrlForImg(subjectBean.getUrl())).into(myHolder.mItemSubjectIvIcon);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_subject_iv_icon)
        ImageView mItemSubjectIvIcon;
        @BindView(R.id.item_subject_tv_title)
        TextView mItemSubjectTvTitle;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
