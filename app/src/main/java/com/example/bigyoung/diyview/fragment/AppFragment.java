package com.example.bigyoung.diyview.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bigyoung.diyview.R;
import com.example.bigyoung.diyview.base.MyBaseFragment;
import com.example.bigyoung.diyview.utils.UIUtils;

import java.util.Random;


/**
 * 创建者     伍碧林
 * 版权       传智播客.黑马程序员
 * 描述	      ${TODO}
 */
public class AppFragment extends MyBaseFragment {

    @Override
    public int loadingDataFromServer() {
        SystemClock.sleep(3000);//模拟耗时的网络请求
        Random random=new Random();
        int i = random.nextInt(3)+2;
        return i;
    }

    @Override
    public View resposeForSuccess(ViewGroup viewGroup) {
        View content = View.inflate(getContext(), R.layout.content_home, null);
        return content;
    }
}
