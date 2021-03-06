package com.example.bigyoung.diyview.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义ViewPager，用于在左右滑动时，请求父亲不拦截事件
 */
public class ChildViewPager extends ViewPager {

    private float mDownX;
    private float mDownY;

    public ChildViewPager(Context context) {
        super(context);
    }

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    /**
        什么时候会来到onTouchEvent中?
            1.当前控件拦截了事件
            2.事件传递给下一级之后,下一级拦截了事件,但是没有处理事件(事件没有被消费)
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的坐标
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 记录手指移动的坐标
                float moveX = ev.getRawX();
                float moveY = ev.getRawY();
                // 计算手指移动的偏移量
                int diffX = (int) (moveX - mDownX + .5f);
                int diffY = (int) (moveY - mDownY + .5f);
                if (Math.abs(diffX) > Math.abs(diffY)) {//水平滚动
                    //孩子处理事件-->申请父容器不拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {//垂直滚动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(ev);
    }
}
