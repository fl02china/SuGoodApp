package com.sugood.app.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.adapter.TabPageAdapter;
import com.sugood.app.fragment.OrderAllFragment;
import com.sugood.app.fragment.OrderCancleFragment;
import com.sugood.app.fragment.OrderIngFragment;
import com.sugood.app.fragment.OrderPayFragment;
import com.sugood.app.fragment.OrderPingJiaFragment;
import com.sugood.app.fragment.OrderSCAllFragment;
import com.sugood.app.fragment.OrderSCCancleFragment;
import com.sugood.app.fragment.OrderSCIngFragment;
import com.sugood.app.fragment.OrderSCPayFragment;
import com.sugood.app.fragment.OrderSCPingJiaFragment;
import com.sugood.app.helper.TabRadioHelper;
import com.viewpagerindicator.UnderlinePageIndicator;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class OrderShanChengActivity  extends BaseActivity{
    private TextView back;

    private UnderlinePageIndicator indicator;

    private RadioGroup mTabGroup;
    private ViewPager mPager;
    private UnderlinePageIndicator mIndicator;

    private TabRadioHelper mTabHelper;
    private TabPageAdapter mTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);

        mPager = (ViewPager) findViewById(R.id.pager);
        // mPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.view_padding));
        mIndicator = (UnderlinePageIndicator) findViewById(R.id.indicator);

        back = (TextView) findViewById(R.id.back);
        mTabGroup = (RadioGroup) findViewById(R.id.tab_group);

        indicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
        mTabHelper = new TabRadioHelper(mTabGroup);

        initTab();
    }

    private void initTab() {
        mTabHelper.setViewPager(mPager);

        mTabAdapter = new TabPageAdapter(getSupportFragmentManager(), this);


        mTabAdapter.addTabFragment("orderscAll", OrderSCAllFragment.class, null);
        mTabAdapter.addTabFragment("orderscing", OrderSCIngFragment.class, null);
        mTabAdapter.addTabFragment("orderscWait", OrderSCPayFragment.class, null);
        mTabAdapter.addTabFragment("orderscPinjia", OrderSCPingJiaFragment.class, null);
        mTabAdapter.addTabFragment("orderscCancle", OrderSCCancleFragment.class, null);

//        mTabAdapter.addTabFragment("1", OrderAllFragment.class, null);
//        mTabAdapter.addTabFragment("2", OrderAllFragment.class, null);
//        mTabAdapter.addTabFragment("3", OrderAllFragment.class, null);
//        mTabAdapter.addTabFragment("4", OrderAllFragment.class, null);
//        mTabAdapter.addTabFragment("5", OrderAllFragment.class, null);
        //mTabAdapter.addTabFragment("app", AppMangerFragment.class, null);
        mPager.setAdapter(mTabAdapter);

        mIndicator.setViewPager(mPager);
        mIndicator.setOnPageChangeListener(mTabHelper);
    }
}
