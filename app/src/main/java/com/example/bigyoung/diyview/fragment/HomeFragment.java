package com.example.bigyoung.diyview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigyoung.diyview.R;
import com.example.bigyoung.diyview.adapter.HomeFragAdapter;
import com.example.bigyoung.diyview.base.MyBaseFragment;
import com.example.bigyoung.diyview.bean.HomeResponseBean;
import com.example.bigyoung.diyview.utils.Constants;
import com.example.bigyoung.diyview.utils.HttpUtils;
import com.example.bigyoung.diyview.utils.MyToast;
import com.example.bigyoung.diyview.utils.ResultNetConnection;
import com.example.bigyoung.diyview.views.LoadingPager;
import com.google.gson.Gson;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.HttpStream;


/**
 * 创建者     SJY
 */
public class HomeFragment extends MyBaseFragment {

    private HomeResponseBean mHomeBean;
    //private View mContent;
    private RecyclerView mRecycleView;
    private HomeFragAdapter mAdapter;
    private Context mContext;
    private LinearLayoutManager mManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getContext();
        initContent();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 初始化显示内容
     */
    private void initContent() {
        mAdapter = new HomeFragAdapter(mContext);
        //设置布局
        mManager = new LinearLayoutManager(mContext);
        //完善recycleV
        mRecycleView = new RecyclerView(mContext);
        mRecycleView.setLayoutManager(mManager);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public int loadingDataFromServer() {
        //创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //拼接要访问的URL
        String url = Constants.HOST_URL + Constants.HOME_CATE;
        //设置参数对应map
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("index", 0);
        //转为urlParams
        String urlParamsByMap = HttpUtils.getUrlParamsByMap(mapParams);
        //拼接
        url = url + "?" + urlParamsByMap;
        //创建request请求对象
        Request request = new Request.Builder().get().url(url).build();
        //执行请求
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                //请求成功
                String jsonString = response.body().string();
                Gson gson = new Gson();
                mHomeBean = gson.fromJson(jsonString, HomeResponseBean.class);
                //判断获得数据是否为空
                if (mHomeBean == null || mHomeBean.getList() == null || mHomeBean.getList().size() == 0)
                    return LoadingPager.EMPTY_STATE;
                return LoadingPager.SUCCESS_STATE;
            } else {
                return ResultNetConnection.resultNetFailed(mContext);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResultNetConnection.resultNetFailed(mContext);
        }
    }

    @Override
    public View resposeForSuccess(ViewGroup viewGroup) {
        if(mHomeBean!=null){
            mAdapter.updateHomeBean(mHomeBean.getList());
            mAdapter.notifyDataSetChanged();
        }
        return mRecycleView;
    }

}
