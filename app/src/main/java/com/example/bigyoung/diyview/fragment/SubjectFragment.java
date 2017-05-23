package com.example.bigyoung.diyview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bigyoung.diyview.adapter.AppFragAdapter;
import com.example.bigyoung.diyview.adapter.SubjectFragAdapter;
import com.example.bigyoung.diyview.base.MyBaseFragment;
import com.example.bigyoung.diyview.bean.ItemBean;
import com.example.bigyoung.diyview.bean.SubjectBean;
import com.example.bigyoung.diyview.protocol.AppFragProtocol;
import com.example.bigyoung.diyview.protocol.SubjectFragProtocol;
import com.example.bigyoung.diyview.utils.UIUtils;
import com.example.bigyoung.diyview.views.LoadingPager;

import java.io.IOException;
import java.util.List;

/**
 * 创建者     伍碧林
 * 版权       传智播客.黑马程序员
 * 描述	      ${TODO}
 */
public class SubjectFragment extends MyBaseFragment {
    private RecyclerView mRecycleView;
    private SubjectFragAdapter mAdapter;

    private LinearLayoutManager mManager;
    private int mCurrentIndex = 0;//当前页对应的索引
    private List<SubjectBean> mItemBeanList;

    @Override
    protected void initContent() {
        mAdapter = new SubjectFragAdapter();
        //设置布局
        mManager = new LinearLayoutManager(mContext);
        //完善recycleV
        mRecycleView = new RecyclerView(mContext);
        mRecycleView.setLayoutManager(mManager);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public int loadingDataFromServer() {
        //执行请求
        SubjectFragProtocol protocol = new SubjectFragProtocol();
        try {
            mItemBeanList = protocol.loadData(mCurrentIndex);
            //判断获得数据是否为空
            if (mItemBeanList == null || mItemBeanList.size() == 0)
                return LoadingPager.EMPTY_STATE;
            return LoadingPager.SUCCESS_STATE;
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.FAILED_STATE;
        }
    }

    @Override
    public View resposeForSuccess(ViewGroup viewGroup) {
        if (mItemBeanList != null) {
            mAdapter.updateListBeans(mItemBeanList);
            mAdapter.notifyDataSetChanged();
        }
        return mRecycleView;
    }

}
