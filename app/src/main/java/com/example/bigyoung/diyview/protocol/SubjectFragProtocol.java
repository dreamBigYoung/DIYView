package com.example.bigyoung.diyview.protocol;

import com.example.bigyoung.diyview.base.BaseProtocol;
import com.example.bigyoung.diyview.bean.SubjectBean;
import com.example.bigyoung.diyview.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by BigYoung on 2017/4/26.
 */

public class SubjectFragProtocol extends BaseProtocol<List<SubjectBean>> {
    private List<SubjectBean> mSubjectBean;
    private int index;//当前页的索引

    @Override
    public String getUrlCategory() {
        return Constants.SUBJECT_CATE;
    }

    @Override
    public List<SubjectBean> parseJsonString(String jsonString) {
        Gson gson = new Gson();
        mSubjectBean = gson.fromJson(jsonString, new TypeToken<List<SubjectBean>>() {
        }.getType());
        return mSubjectBean;
    }

    @Override
    public String getInterfaceKeyPre() {
        return "subject";
    }
}
