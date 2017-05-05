package com.example.bigyoung.diyview.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.bigyoung.diyview.views.LoadingPager;

/**
 * Created by BigYoung on 2017/4/23.
 */

public abstract class MyBaseFragment extends Fragment {

    public Context mContext;

    private LoadingPager mLoadingPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        initContent();
        mLoadingPager = new LoadingPager(mContext) {
            @Override
            public int loadingDataFormServer() {
                return MyBaseFragment.this.loadingDataFromServer();
            }

            @Override
            public View resposeForSuccess(ViewGroup viewGroup) {
                return MyBaseFragment.this.resposeForSuccess(viewGroup);
            }
        };
        FrameLayout.LayoutParams layoutParams;
        return mLoadingPager;
    }
    /**
     * 初始化显示内容
     */
    protected abstract void initContent();

    /**
     * 刷新数据
     */
    public void refreshData(){
        //loading pager开始联网获得数据
        mLoadingPager.startLoadingData();
    }

    /**
     * 从服务器端获取数据
     *
     * @return
     */
    public abstract int loadingDataFromServer();

    /**
     * 响应成功接收数据
     */
    public abstract View resposeForSuccess(ViewGroup viewGroup);
}
