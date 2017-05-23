package com.example.bigyoung.diyview.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.bigyoung.diyview.utils.UIUtils;

import java.util.List;

/**
 * Created by BigYoung on 2017/5/5.
 */

public abstract class MyBaseAdapter<T> extends RecyclerView.Adapter {
    public List<T> mListBeans;
    public Context mContext;
    public MyBaseAdapter(){
        mContext= UIUtils.getContext();
    }
    public void updateListBeans(List<T> listBeans){
        this.mListBeans=listBeans;
    }

/*
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
*/

    @Override
    public int getItemCount() {
        if(mListBeans!=null)
            return mListBeans.size();
        return 0;
    }
}
