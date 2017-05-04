package com.example.bigyoung.diyview.protocol;

import com.example.bigyoung.diyview.base.BaseProtocol;
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

public class HomeFragProtocol extends BaseProtocol<HomeResponseBean>{
    private HomeResponseBean mHomeBean;
    private int index;//当前页的索引
    @Override
    public String getUrlCategory() {
        return Constants.HOME_CATE;
    }
    @Override
    public HomeResponseBean parseJsonString(String jsonString) {
        Gson gson = new Gson();
        mHomeBean = gson.fromJson(jsonString, HomeResponseBean.class);
        return mHomeBean;
    }

    @Override
    public String getInterfaceKeyPre() {
        return "home";
    }
}
