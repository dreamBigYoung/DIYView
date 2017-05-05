package com.example.bigyoung.diyview.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigyoung.diyview.adapter.GameFragAdapter;
import com.example.bigyoung.diyview.adapter.HomeFragAdapter;
import com.example.bigyoung.diyview.base.MyBaseFragment;
import com.example.bigyoung.diyview.bean.ItemBean;
import com.example.bigyoung.diyview.protocol.GameFragProtocol;
import com.example.bigyoung.diyview.protocol.HomeFragProtocol;
import com.example.bigyoung.diyview.views.LoadingPager;

import java.io.IOException;
import java.util.List;

/**
 * 创建者     伍碧林
 * 版权       传智播客.黑马程序员
 * 描述	      ${TODO}
 */
public class GameFragment extends MyBaseFragment {
    private RecyclerView mRecycleView;
    private GameFragAdapter mAdapter;

    private LinearLayoutManager mManager;
    private int mCurrentIndex = 0;//当前页对应的索引
    private List<ItemBean> mItemBeanList;

    /**
     * 初始化显示内容
     */
    @Override
    protected void initContent() {
        mAdapter = new GameFragAdapter(mContext, new GameFragProtocol(), null);
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
        GameFragProtocol protocol =  new GameFragProtocol();
        try {
            mItemBeanList = protocol.loadData(mCurrentIndex);
            //判断获得数据是否为空
            if (mItemBeanList == null ||  mItemBeanList.size() == 0)
                return LoadingPager.EMPTY_STATE;
            return LoadingPager.SUCCESS_STATE;
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.FAILED_STATE;
        }
    }

    @Override
    public View resposeForSuccess(ViewGroup viewGroup) {
        if(mItemBeanList!=null){
            mAdapter.updateListBean(mItemBeanList);
            mAdapter.notifyDataSetChanged();
        }
        return mRecycleView;
    }
}
