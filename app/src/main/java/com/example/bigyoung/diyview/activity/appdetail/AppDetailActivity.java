package com.example.bigyoung.diyview.activity.appdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bigyoung.diyview.R;
import com.example.bigyoung.diyview.bean.ItemBean;
import com.example.bigyoung.diyview.protocol.AppDetailProtocol;
import com.example.bigyoung.diyview.utils.MyToast;
import com.example.bigyoung.diyview.utils.UIUtils;
import com.example.bigyoung.diyview.views.LoadingPager;

import java.io.IOException;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by BigYoung on 2017/5/6.
 */

public class AppDetailActivity extends AppCompatActivity {

    private LoadingPager mLoadingPager;
    private int mCurrentIndex;
    private ItemBean mItemBeanList;
    private String mPackageName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingPager = new LoadingPager(AppDetailActivity.this) {
            @Override
            public int loadingDataFormServer() {
                //执行请求
                AppDetailProtocol protocol = new AppDetailProtocol();
                try {
                    mItemBeanList = protocol.loadData(mPackageName);
                    //判断获得数据是否为空
                    if (mItemBeanList == null)
                        return LoadingPager.EMPTY_STATE;
                    return LoadingPager.SUCCESS_STATE;
                } catch (IOException e) {
                    e.printStackTrace();
                    return LoadingPager.FAILED_STATE;
                }
            }

            @Override
            public View resposeForSuccess(ViewGroup viewGroup) {
                return LinearLayout.inflate(AppDetailActivity.this, R.layout.content_home, null);
            }
        };
        mLoadingPager.setBackgroundColor(getResources().getColor(R.color.color_appdetail));
        setContentView(mLoadingPager);
        mPackageName = getIntent().getStringExtra("packageName");
        mLoadingPager.startLoadingData();
    }
}
