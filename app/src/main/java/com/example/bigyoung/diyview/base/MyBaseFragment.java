package com.example.bigyoung.diyview.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigyoung.diyview.views.LoadingPager;

/**
 * Created by BigYoung on 2017/4/23.
 */

public abstract class MyBaseFragment extends Fragment {

    private Context mMContext;

    private LoadingPager mLoadingPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMContext = getContext();
        mLoadingPager = new LoadingPager(mMContext) {
            @Override
            public int loadingDataFormServer() {
                return MyBaseFragment.this.loadingDataFromServer();
            }

            @Override
            public View resposeForSuccess(ViewGroup viewGroup) {
                return MyBaseFragment.this.resposeForSuccess(viewGroup);
            }
        };
        return mLoadingPager;
    }

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
