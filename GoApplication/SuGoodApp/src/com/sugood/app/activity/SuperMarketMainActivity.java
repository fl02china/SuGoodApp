package com.sugood.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.MarketPagerAdapter;
import com.sugood.app.entity.AdEntity;
import com.sugood.app.global.Constant;
import com.sugood.app.listener.MarketPageChangeListener;
import com.sugood.app.util.GlideUtil;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

/**
 * Package :com.android.supermarket.supermaket
 * Description :
 * Author :Rc3
 * Created at :2017/2/25 12:13.
 */

public class SuperMarketMainActivity extends BaseActivity  {


    final static int CheapProduct = 0;
    final static int CigaretteProduct = 1;
    final static int FrozenProduct = 2;
    final static int DrinkProduct = 3;
    final static int GrainProduct = 4;
    final static int SnacksProduct = 5;
    final static int MachineProduct = 6;
    final static int DailyProduct = 7;
    final static int HomeProduct = 8;
    final static int BedProduct = 9;
    ViewPager mViewPager;
    MarketPagerAdapter mAdapter;
    List<ImageView> mList;
    List<ImageView> mDotList;
    Context mContext;
    /*便利商品*/
    RelativeLayout mCheapProductLayout;
    /*代买香烟*/
    RelativeLayout mCigaretteProductLayout;
    /*冷冻配送*/
    RelativeLayout mFrozenProductLayout;
    /*奶酒饮料*/
    RelativeLayout mDrinkProductLayout;
    /*粮油干调*/
    RelativeLayout mGrainProductLayout;
    /*休闲食品*/
    RelativeLayout mSnacksProductLayout;
    /*家用电器*/
    RelativeLayout mMachineProductLayout;
    /*日用百货*/
    RelativeLayout mDailyProductLayout;
    /*家居用品*/
    RelativeLayout mHomeProductLayout;
    /*床上用品*/
    RelativeLayout mBedProductLayout;
    ScheduledExecutorService mScheduledExecutorService;
    MarketPageChangeListener mChangeListener;
    List<AdEntity> adEntityList = new ArrayList<>();

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mViewPager.setCurrentItem(mChangeListener.mCurrentItem);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supermarket_main_activity);
        mContext = this;
        initViews();
        initData();


    }


    private void update() {

        mList = new ArrayList<>();
        mDotList = new ArrayList<>();
        //把imageview加入到list集合传入pageradapter
        for (int i = 0; i < adEntityList.size(); i++) {
            ImageView imageview = new ImageView(mContext);
            GlideUtil.displayImage(Constant.PHOTOBASEURL + adEntityList.get(i).getPhoto(), imageview);
            imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mList.add(imageview);
        }
        mAdapter = new MarketPagerAdapter(mList, adEntityList, mContext);
        mViewPager.setAdapter(mAdapter);

    }

    private void initData() {

        /**
         *  请求广告图片
         */
        RequestParams photoParams = new RequestParams();
        photoParams.put("siteId", "97");
        HttpUtil.post(Constant.GOODFOODAD, photoParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TAG_AD", "onSuccess: " + response);
                adEntityList = JsonUtil.toList(response.toString(), AdEntity.class);
                update();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("TAG_AD", "onFailure: " + statusCode + responseString);
            }
        });

//        mList = new ArrayList<>();
//        //把imageview加入到list集合传入pageradapter
//        for (int i = 0; i < 6; i++) {
//            ImageView imageview = new ImageView(mContext);
//            imageview.setBackgroundResource(R.drawable.banner_image);
//            imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            mList.add(imageview);
//        }
//        mAdapter = new MarketPagerAdapter(mList);
//
//        mDotList = new ArrayList<>();
//        mDotList.add((ImageView) findViewById(R.id.market_dot1));
//        mDotList.add((ImageView) findViewById(R.id.market_dot2));
//        mDotList.add((ImageView) findViewById(R.id.market_dot3));
//        mDotList.add((ImageView) findViewById(R.id.market_dot4));
//        mDotList.add((ImageView) findViewById(R.id.market_dot5));
//        mDotList.add((ImageView) findViewById(R.id.market_dot6));
//        mViewPager.setCurrentItem(Integer.MAX_VALUE / 4);
//        mViewPager.addOnPageChangeListener(mChangeListener = new MarketPageChangeListener(mList, mDotList));
//        mViewPager.setAdapter(mAdapter);

        //轮播
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                synchronized (mViewPager) {
                    if (!mChangeListener.nowAction) {
                        mChangeListener.mCurrentItem = mChangeListener.mCurrentItem + 1;
                        mHandler.obtainMessage().sendToTarget();
                    }
                }
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.market_main_vp);
        mCheapProductLayout = (RelativeLayout) findViewById(R.id.cheap_product_rl);
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.cheap_product_rl:
                intent = new Intent(mContext, SuperMarketTypeActivity.class);
                intent.putExtra("key", CheapProduct);
                startActivity(intent);
                break;
            case R.id.cigarette_product_rl:
                intent = new Intent(mContext, SuperMarketTypeActivity.class);
                intent.putExtra("key", CigaretteProduct);
                startActivity(intent);
                break;
            case R.id.frozen_product_rl:
                intent = new Intent(mContext, SuperMarketTypeActivity.class);
                intent.putExtra("key", FrozenProduct);
                startActivity(intent);
                break;
            case R.id.drink_product_rl:
                intent = new Intent(mContext, SuperMarketTypeActivity.class);
                intent.putExtra("key", DrinkProduct);
                startActivity(intent);
                break;
            case R.id.grain_product_rl:
                intent = new Intent(mContext, SuperMarketTypeActivity.class);
                intent.putExtra("key", GrainProduct);
                startActivity(intent);
                break;
            case R.id.snacks_product_rl:
                intent = new Intent(mContext, SuperMarketTypeActivity.class);
                intent.putExtra("key", SnacksProduct);
                startActivity(intent);
                break;
            case R.id.machine_product_rl:
                intent = new Intent(mContext, SuperMarketTypeActivity.class);
                intent.putExtra("key", MachineProduct);
                startActivity(intent);
                break;
            case R.id.daily_product_rl:
                intent = new Intent(mContext, SuperMarketTypeActivity.class);
                intent.putExtra("key", DailyProduct);
                startActivity(intent);
                break;
            case R.id.home_product_rl:
                intent = new Intent(mContext, SuperMarketTypeActivity.class);
                intent.putExtra("key", HomeProduct);
                startActivity(intent);
                break;
            case R.id.bed_product_rl:
                intent = new Intent(mContext, SuperMarketTypeActivity.class);
                intent.putExtra("key", BedProduct);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScheduledExecutorService.shutdown();
    }

}
