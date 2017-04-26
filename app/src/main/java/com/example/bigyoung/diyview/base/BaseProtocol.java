package com.example.bigyoung.diyview.base;

import com.example.bigyoung.diyview.bean.HomeResponseBean;
import com.example.bigyoung.diyview.utils.Constants;
import com.example.bigyoung.diyview.utils.HttpUtils;
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

public abstract class BaseProtocol<ResultBean> {
    ResultBean mResultBean;
    public ResultBean loadData() throws IOException {
        //创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //拼接要访问的URL
        String url = Constants.HOST_URL + getUrlCategory();
        //设置参数对应map
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("index", getUrlPageIndex());
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
            mResultBean =parseJsonString(response.body().string());
            return mResultBean;
        } else {
            //抛出异常
            throw new IOException();
            //return null;
        }
    }

    /**
     * 获得url的category部分
     * @return
     */
    public abstract String getUrlCategory();

    /**
     * 获得url的category部分的当前页数的param
     * @return
     */
    public abstract int getUrlPageIndex();

    /**
     * 处理jsonString，转为目标数据
     * @param jsonString
     * @return
     */
    public abstract ResultBean parseJsonString(String jsonString);
}
