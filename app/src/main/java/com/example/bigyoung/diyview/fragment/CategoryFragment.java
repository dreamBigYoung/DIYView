package com.example.bigyoung.diyview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bigyoung.diyview.base.MyBaseFragment;
import com.example.bigyoung.diyview.utils.UIUtils;

/**
 * 创建者     伍碧林
 * 版权       传智播客.黑马程序员
 * 描述	      ${TODO}
 */
public class CategoryFragment extends MyBaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setText(this.getClass().getSimpleName());//"HomeFragment"
        return tv;
    }

    @Override
    public int loadingDataFromServer() {
        return 0;
    }

    @Override
    public View resposeForSuccess(ViewGroup viewGroup) {
        return null;
    }
}
