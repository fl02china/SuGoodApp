package com.sugood.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.MarketPagerAdapter;
import com.sugood.app.adapter.MarketTypeRvAdapter;
import com.sugood.app.entity.AdEntity;
import com.sugood.app.entity.ShopList;
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

import static com.sugood.app.activity.SuperMarketMainActivity.BedProduct;
import static com.sugood.app.activity.SuperMarketMainActivity.CheapProduct;
import static com.sugood.app.activity.SuperMarketMainActivity.CigaretteProduct;
import static com.sugood.app.activity.SuperMarketMainActivity.DailyProduct;
import static com.sugood.app.activity.SuperMarketMainActivity.DrinkProduct;
import static com.sugood.app.activity.SuperMarketMainActivity.FrozenProduct;
import static com.sugood.app.activity.SuperMarketMainActivity.GrainProduct;
import static com.sugood.app.activity.SuperMarketMainActivity.HomeProduct;
import static com.sugood.app.activity.SuperMarketMainActivity.MachineProduct;
import static com.sugood.app.activity.SuperMarketMainActivity.SnacksProduct;

/**
 * Package :com.android.supermarket.supermaket
 * Description :
 * Author :Rc3
 * Created at :2017/2/25 22:29.
 */

public class SuperMarketTypeActivity extends BaseActivity {


    ViewPager mViewPager;

    MarketPagerAdapter mAdapter;
    List<ImageView> mList;
    List<ImageView> mDotList;
    List<ShopList> mMarketTypeList;
    Context mContext;

    MarketTypeRvAdapter mRvAdapter;
    ScheduledExecutorService mScheduledExecutorService;
    XRecyclerView mRecyclerView;
    MarketPageChangeListener mChangeListener;
    /*主页*/
    TextView mMainTv;
    /*烟酒补品*/
    TextView mCigaretteTv;
    /*奶酒饮料*/
    TextView mDrinkTv;
    /*粮油干调*/
    TextView mGrainTv;
    /*家居用品*/
    TextView mHomeTv;
    /*日用百货*/
    TextView mDailyTv;
    /*冷冻配送*/
    TextView mFrozenTv;
    /*休闲食品*/
    TextView mSnacksTv;
    /*家用电器*/
    TextView mMachineTv;
    /*床上用品*/
    TextView mBedTv;
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
        setContentView(R.layout.supermarket_type_activity);
        mContext = this;
        initView();
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

    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.market_type_vp);
        mRecyclerView = (XRecyclerView) findViewById(R.id.market_type_rv);
        mCigaretteTv = (TextView) findViewById(R.id.market_cigarette_tv);
        mDrinkTv = (TextView) findViewById(R.id.market_drink_tv);
        mGrainTv = (TextView) findViewById(R.id.market_grain_tv);
        mDailyTv = (TextView) findViewById(R.id.market_daily_tv);
        mFrozenTv = (TextView) findViewById(R.id.market_frozen_tv);
        mSnacksTv = (TextView) findViewById(R.id.market_snacks_tv);
        mCigaretteTv = (TextView) findViewById(R.id.market_cigarette_tv);
        mMachineTv = (TextView) findViewById(R.id.market_machine_tv);
        mBedTv = (TextView) findViewById(R.id.market_bed_tv);
        mHomeTv = (TextView) findViewById(R.id.market_home_tv);
        mMainTv= (TextView) findViewById(R.id.market_main_tv);
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

        //选中状态
        setHeaderTextbg(getIntent().getIntExtra("key", 1));

        mList = new ArrayList<>();
        //把imageview加入到list集合传入pageradapter
        for (int i = 0; i < 5; i++) {
            ImageView imageview = new ImageView(mContext);
            imageview.setBackgroundResource(R.drawable.market_type_banner);
            imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mList.add(imageview);
        }


//        mDotList = new ArrayList<>();
//        mDotList.add((ImageView) findViewById(R.id.market_type_dot1));
//        mDotList.add((ImageView) findViewById(R.id.market_type_dot2));
//        mDotList.add((ImageView) findViewById(R.id.market_type_dot3));
//        mDotList.add((ImageView) findViewById(R.id.market_type_dot4));
//        mDotList.add((ImageView) findViewById(R.id.market_type_dot5));
//        mAdapter = new MarketPagerAdapter(mList);
//        mViewPager.setCurrentItem(Integer.MAX_VALUE / 4);
//        mViewPager.addOnPageChangeListener(mChangeListener = new MarketPageChangeListener(mList, mDotList));
//        mViewPager.setAdapter(mAdapter);

        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                synchronized (mViewPager) {
                    if (!mChangeListener.nowAction) {
                        mChangeListener.mCurrentItem++;
                        mHandler.obtainMessage().sendToTarget();
                    }
                }
            }
        }, 0, 5, TimeUnit.SECONDS);

        //recyclerview
        mMarketTypeList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            mMarketTypeList.add(new ShopList());
        }

        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));

        mRvAdapter = new MarketTypeRvAdapter(mContext, mMarketTypeList);


        mRecyclerView.setAdapter(mRvAdapter);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mRecyclerView.refreshComplete();

                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mMarketTypeList.add(new ShopList());
                        mRecyclerView.refreshComplete();

                    }
                }, 1000);
            }
        });

    }

    private void setHeaderTextbg(int mSeleted) {
        switch (mSeleted) {
            case CheapProduct:
                mMainTv.setTextColor(getResources().getColor(R.color.text_red));
                break;
            case CigaretteProduct:
                mCigaretteTv.setTextColor(getResources().getColor(R.color.text_red));
                break;
            case GrainProduct:
                mGrainTv.setTextColor(getResources().getColor(R.color.text_red));
                break;
            case HomeProduct:
                mHomeTv.setTextColor(getResources().getColor(R.color.text_red));
                break;
            case DailyProduct:
                mDailyTv.setTextColor(getResources().getColor(R.color.text_red));
                break;
            case FrozenProduct:
                mFrozenTv.setTextColor(getResources().getColor(R.color.text_red));
                break;
            case SnacksProduct:
                mSnacksTv.setTextColor(getResources().getColor(R.color.text_red));
                break;
            case MachineProduct:
                mMachineTv.setTextColor(getResources().getColor(R.color.text_red));
                break;
            case DrinkProduct:
                mDrinkTv.setTextColor(getResources().getColor(R.color.text_red));
                break;
            case BedProduct:
                mBedTv.setTextColor(getResources().getColor(R.color.text_red));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScheduledExecutorService.shutdown();
    }
}
