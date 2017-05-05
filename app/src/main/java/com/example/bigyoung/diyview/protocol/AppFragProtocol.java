package com.example.bigyoung.diyview.protocol;

import com.example.bigyoung.diyview.base.BaseProtocol;
import com.example.bigyoung.diyview.bean.ItemBean;
import com.example.bigyoung.diyview.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by BigYoung on 2017/4/26.
 */

public class AppFragProtocol extends BaseProtocol<List<ItemBean>> {
    private List<ItemBean> mAppBean;
    private int index;//当前页的索引

    @Override
    public String getUrlCategory() {
        return Constants.APP_CATE;
    }

    @Override
    public List<ItemBean> parseJsonString(String jsonString) {
        Gson gson = new Gson();
        mAppBean = gson.fromJson(jsonString, new TypeToken<List<ItemBean>>() {
        }.getType());
        return mAppBean;
    }

    @Override
    public String getInterfaceKeyPre() {
        return "app";
    }
}
