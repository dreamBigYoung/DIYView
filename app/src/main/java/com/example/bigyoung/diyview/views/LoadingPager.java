package com.example.bigyoung.diyview.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.bigyoung.diyview.R;
import com.example.bigyoung.diyview.base.MyApplication;

/**
 * Created by BigYoung on 2017/4/23.
 */

public abstract class LoadingPager extends FrameLayout {
    /**
     * LodingPager的四种状态
     */
    public final int LONDING_STATE = 1;
    public final int FAILED_STATE = 2;
    public final int SUCCESS_STATE = 3;
    public final int EMPTY_STATE = 4;

    public int mCurrentState = LONDING_STATE;
    private View mError;
    private View mEmpty;
    private View mLoading;

    private View mSuccess;

    public LoadingPager(Context context) {
        this(context, null);
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mError = LinearLayout.inflate(context, R.layout.pager_error, LoadingPager.this);
        mEmpty = LinearLayout.inflate(context, R.layout.pager_empty, LoadingPager.this);
        mLoading = LinearLayout.inflate(context, R.layout.pager_loading, LoadingPager.this);

    }

    /**
     * 加载数据的操作
     */
    public void startLoadingData() {
        //置为加载状态，显示加载状态下的图片
        setCurrentState(LONDING_STATE);
        refreashViewByCurrentState();
        //开启加载任务
        new Thread(new NetConnecttionForData()).start();
    }

    /**
     * 联网操作放在子线程中
     */
    private class NetConnecttionForData implements Runnable {

        @Override
        public void run() {
            //获取联网结果,重置当前状态
            setCurrentState(loadingDataFormServer());
            //back to main thread to operation UI
            MyApplication.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    operationByCurrentState();
                }
            });
        }
    }

    /**
     * 根据当前状态重置页面
     */
    private void operationByCurrentState() {
        switch (mCurrentState) {
            case SUCCESS_STATE:
                resposeForSuccess(LoadingPager.this);
                refreashViewByCurrentState();
                break;
            case FAILED_STATE:
                refreashViewByCurrentState();
                break;
            case EMPTY_STATE:
                refreashViewByCurrentState();
                break;
            default:
                break;
        }
    }

    /**
     * 更新页面
     *
     */
    public void refreashViewByCurrentState() {
        mEmpty.setVisibility(mCurrentState==EMPTY_STATE?View.VISIBLE:View.INVISIBLE);
    }

    /**
     * 设置当前状态
     *
     * @param current_state
     */
    public void setCurrentState(int current_state) {
        this.mCurrentState = current_state;
    }

    /**
     * 设置加载成功View
     * @param success
     */
    public void setSuccess(View success) {
        mSuccess = success;
    }
    /**
     * @return 当前状态
     */
    public abstract int loadingDataFormServer();

    /**
     * 处理加载成功
     */
    public abstract View resposeForSuccess(ViewGroup viewGroup);

}
