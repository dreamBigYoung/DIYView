package com.example.bigyoung.diyview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;

import com.astuetz.PagerSlidingTabStripEnter;
import com.example.bigyoung.diyview.base.MyBaseFragment;
import com.example.bigyoung.diyview.factory.FragmentFactory;
import com.example.bigyoung.diyview.utils.LogUtils;
import com.example.bigyoung.diyview.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.sliping_tan)
    PagerSlidingTabStripEnter mSlipingTan;
    @BindView(R.id.content_view)
    ViewPager mContentView;
    private String[] titleSring = {"草莓", "菠萝", "蓝莓"};
    private List<String> titleList;
    private List<Fragment> fragList;
    private MyPagerAdapter mMAdapter;
    private MyPageChangedListener mMyPageChangedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // mContentView= (ViewPager) findViewById(R.id.content_view);
        initData();
        initAdapter();
        initViewPager();
        initTab();
        initEvent();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        /**
         * 添加条目切换监听事件
         */
        mMyPageChangedListener = new MyPageChangedListener();
        mContentView.addOnPageChangeListener(mMyPageChangedListener);
        //设置绘制监听事件,当控件初始化完毕后执行引用操作
        mContentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //设置初始化显示内容
                mMyPageChangedListener.onPageSelected(0);
                mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void initTab() {
        mSlipingTan.setViewPager(mContentView);
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        mContentView.setAdapter(mMAdapter);
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        mMAdapter = new MyPagerAdapter(getSupportFragmentManager());
    }

    /**
     * 初始化数据
     */
    private void initData() {
        titleSring = UIUtils.getStrings(R.array.main_titles);
        titleList = new ArrayList<String>();
        if (titleSring == null)
            return;
        int len = titleSring.length;
        //create list of frag title
        for (int i = 0; i < len; i++) {
            titleList.add(titleSring[i]);
        }
        //创造List of fragment
        fragList = new ArrayList<Fragment>();
        for (int i = 0; i < len; i++) {
            fragList.add(FragmentFactory.createFragment(i));
        }

    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            if (titleList != null)
                return titleList.size();
            return 0;
        }

        @Override
        public Fragment getItem(int position) {
            LogUtils.s("初始化->" + titleSring[position]);
            Fragment fragment = fragList.get(position);
            return fragment;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }

    private class MyPageChangedListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(fragList!=null){
                MyBaseFragment baseFrag= (MyBaseFragment) fragList.get(position);
                //start refreash
                baseFrag.refreshData();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
