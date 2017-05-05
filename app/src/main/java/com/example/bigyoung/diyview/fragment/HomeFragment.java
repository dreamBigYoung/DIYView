package com.example.bigyoung.diyview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigyoung.diyview.adapter.HomeFragAdapter;
import com.example.bigyoung.diyview.base.MyBaseFragment;
import com.example.bigyoung.diyview.bean.HomeResponseBean;
import com.example.bigyoung.diyview.protocol.HomeFragProtocol;
import com.example.bigyoung.diyview.views.LoadingPager;

import java.io.IOException;


/**
 * 创建者     SJY
 */
public class HomeFragment extends MyBaseFragment {

    private HomeResponseBean mHomeBean;

    //private View mContent;
    private RecyclerView mRecycleView;
    private HomeFragAdapter mAdapter;

    private LinearLayoutManager mManager;
    private int mCurrentIndex=0;//当前页对应的索引

    /**
     * 初始化显示内容
     */
    protected void initContent() {
        mAdapter = new HomeFragAdapter(mContext,new HomeFragProtocol(),null);
        //设置布局
        mManager = new LinearLayoutManager(mContext);
        //完善recycleV
        mRecycleView = new RecyclerView(mContext);
        mRecycleView.setLayoutManager(mManager);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public int loadingDataFromServer() {
        //执行请求
        HomeFragProtocol homePro = new HomeFragProtocol();
        try {
            mHomeBean = homePro.loadData(mCurrentIndex);
            //判断获得数据是否为空
            if (mHomeBean == null || mHomeBean.getList() == null || mHomeBean.getList().size() == 0)
                return LoadingPager.EMPTY_STATE;
            return LoadingPager.SUCCESS_STATE;
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.FAILED_STATE;
        }
    }

    @Override
    public View resposeForSuccess(ViewGroup viewGroup) {
        if (mHomeBean != null) {
            mAdapter.updateListBean(mHomeBean.getList());
            mAdapter.setPictures(mHomeBean.getPicture());
            mAdapter.notifyDataSetChanged();
        }
        return mRecycleView;
    }

    public void setCurrentIndex(int currentIndex) {
        mCurrentIndex = currentIndex;
    }
}
