package com.example.bigyoung.diyview.protocol;

import com.example.bigyoung.diyview.bean.HomeResponseBean;
import com.example.bigyoung.diyview.utils.Constants;
import com.example.bigyoung.diyview.utils.HttpUtils;
import com.example.bigyoung.diyview.utils.ResultNetConnection;
import com.example.bigyoung.diyview.views.LoadingPager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by BigYoung on 2017/4/26.
 */

public class HomeFragProtocol {
    private HomeResponseBean mHomeBean;

    public HomeResponseBean loadData() throws IOException {
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
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            //请求成功
            String jsonString = response.body().string();
            Gson gson = new Gson();
            mHomeBean = gson.fromJson(jsonString, HomeResponseBean.class);
            return mHomeBean;
        } else {
            //抛出异常
            throw new IOException();
            //return null;
        }
    }
}
