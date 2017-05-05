package com.example.bigyoung.diyview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.bigyoung.diyview.R;
import com.example.bigyoung.diyview.base.BaseProtocol;
import com.example.bigyoung.diyview.base.SuperBaseAdapter;
import com.example.bigyoung.diyview.bean.HomeResponseBean;
import com.example.bigyoung.diyview.bean.ItemBean;
import com.example.bigyoung.diyview.protocol.AppFragProtocol;
import com.example.bigyoung.diyview.protocol.GameFragProtocol;
import com.example.bigyoung.diyview.views.LoadingPager;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BigYoung on 2017/4/25.
 */

public class AppFragAdapter extends SuperBaseAdapter<List<ItemBean>> {
    private List<String> mPictures;//轮播图显示图片

    private HomeResponseBean mHomeBeanInstance;//存放调用加载更多获得的结果集
    private int mLoadMoreState = LoadingPager.FAILED_STATE;//加载更多的操作结果

    public AppFragAdapter(Context context, AppFragProtocol baseProtocol, List<String> pictures) {
        this(context, null, baseProtocol, pictures);
    }

    public AppFragAdapter(Context context, List<ItemBean> mItemList, AppFragProtocol baseProtocol, List<String> pictures) {
        super(context, mItemList, baseProtocol);
        this.mPictures = pictures;
    }

    public void setPictures(List<String> pictures) {
        mPictures = pictures;
    }

    @Override
    public RecyclerView.ViewHolder getHeadViewHolder(ViewGroup parent) {
        View headView = LayoutInflater.from(mContext).inflate(R.layout.item_empty_head, parent, false);
        ViewHolderHead headHolder = new ViewHolderHead(headView);
        return headHolder;
    }
    /**
     * 获得加载更多的结果集
     *
     * @param nextPageIndex
     * @return
     * @throws IOException
     */
    @Override
    public List<ItemBean> getLoadMoreList(int nextPageIndex, BaseProtocol<List<ItemBean>> protocol) throws IOException {
        List<ItemBean> itemBeanList = protocol.loadData(nextPageIndex);
        return itemBeanList;
    }

    @Override
    protected void fixHeadViewHolder(RecyclerView.ViewHolder holder) {
    }


    static class ViewHolderHead extends RecyclerView.ViewHolder {
        @BindView(R.id.empty_head)
        LinearLayout mEmptyHead;

        ViewHolderHead(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
