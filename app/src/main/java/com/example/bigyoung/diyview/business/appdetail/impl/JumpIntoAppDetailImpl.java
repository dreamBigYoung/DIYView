package com.example.bigyoung.diyview.business.appdetail.impl;


import android.content.Intent;

import com.example.bigyoung.diyview.activity.appdetail.AppDetailActivity;
import com.example.bigyoung.diyview.business.appdetail.JumpIntoAppDetail;
import com.example.bigyoung.diyview.utils.UIUtils;

/**
 * Created by BigYoung on 2017/5/6.
 */

public class JumpIntoAppDetailImpl implements JumpIntoAppDetail {
    @Override
    public void jumIntoAppDetailActivity(String keyword) {
        Intent intent = new Intent(UIUtils.getContext(), AppDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //传值
        intent.putExtra("packageName", keyword);
        UIUtils.getContext().startActivity(intent);
    }
}
