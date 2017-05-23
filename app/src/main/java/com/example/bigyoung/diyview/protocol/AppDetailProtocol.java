package com.example.bigyoung.diyview.protocol;

import android.support.annotation.NonNull;

import com.example.bigyoung.diyview.base.BaseProtocol;
import com.example.bigyoung.diyview.bean.ItemBean;
import com.example.bigyoung.diyview.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BigYoung on 2017/4/26.
 */

public class AppDetailProtocol extends BaseProtocol<ItemBean> {
    private ItemBean mAppDetailBean;
    private int index;//当前页的索引
    private String mMPackageName;

    @Override
    public String getUrlCategory() {
        return Constants.APPDETAIL_CATE;
    }

    @Override
    public ItemBean parseJsonString(String jsonString) {
        Gson gson = new Gson();
        mAppDetailBean = gson.fromJson(jsonString,ItemBean.class);
        return mAppDetailBean;
    }

    @Override
    public String getInterfaceKeyPre() {
        return "detail_"+mMPackageName;
    }

    /**
     * 开始加载数据从服务器端
     * @param packageName
     */
    public ItemBean loadData(String packageName) throws IOException {
        this.mMPackageName=packageName;
        return super.loadData(0);
    }
    @NonNull
    @Override
    public Map<String, Object> getUrlParamMap(int index) {
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("packageName", mMPackageName);
        return mapParams;
    }
}
