package com.sugood.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.MarketPagerAdapter;
import com.sugood.app.adapter.MarketTypeRvAdapter;
import com.sugood.app.entity.AdEntity;
import com.sugood.app.entity.ShopList;
import com.sugood.app.entity.ShopMain;
import com.sugood.app.entity.TakeawayShop;
import com.sugood.app.global.Constant;
import com.sugood.app.listener.MarketPageChangeListener;
import com.sugood.app.util.DensityUtil;
import com.sugood.app.util.GlideUtil;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.view.MtitlePopupWindow;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

/**
 * Package :com.sugood.app.activity
 * Description :
 * Author :Rc3
 * Created at :2017/3/29 10:26.
 */

public class TakeawayMarketShopDetailActivity extends AppCompatActivity {

    ViewPager mViewPager;

    MarketPagerAdapter mAdapter;
    List<ImageView> mList;
    List<ImageView> mDotList;
    List<ShopList> mMarketTypeList = new ArrayList<>();
    List<ShopMain> shopMainList = new ArrayList<>();
    Context mContext;

    MarketTypeRvAdapter mRvAdapter;
    ScheduledExecutorService mScheduledExecutorService;
    XRecyclerView mRecyclerView;
    MarketPageChangeListener mChangeListener;
    MtitlePopupWindow mtitlePopupWindow;
    ImageView market_type_shopcar_img;
    String cateId;
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
    EditText market_type_search_ed;
    int page = 1;
    boolean isLoadMore = false;
    List<AdEntity> entityList = new ArrayList<>();
    private int autoCurrIndex = 0;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (msg.arg1 != 0) {
                        mViewPager.setCurrentItem(msg.arg1);
                    } else {
                        //false 当从末页调到首页是，不显示翻页动画效果，
                        mViewPager.setCurrentItem(msg.arg1, false);
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supermarket_type_activity);
        mContext = this;
        shopMainList = (List<ShopMain>) getIntent().getSerializableExtra("asa");
        cateId = getIntent().getIntExtra("cateId", 1) + "";
        initView();
        initData();

    }

    private void requestData(String cateId, int page, final boolean isLoadMore) {
        RequestParams params = new RequestParams();
        params.put("cateId", cateId);
        params.put("page", page);
        params.put("pageSize", 10);
        HttpUtil.post(Constant.SHOP_MAIN_LIST_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    final String result = new String(responseBody);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (!isLoadMore) {
                                mMarketTypeList.clear();
                            }
                            mMarketTypeList.addAll(JsonUtil.toList(result, ShopList.class));
                            mRvAdapter.notifyDataSetChanged();
                        }
                    });

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, final Throwable error) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if(TextUtils.isEmpty(error.getMessage().toString())){
                            Toast.makeText(mContext, "time out", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mContext, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }


    private void initView() {

        market_type_shopcar_img = (ImageView) findViewById(R.id.market_type_shopcar_img);
        market_type_search_ed = (EditText)findViewById(R.id.market_type_search_ed);
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
        mMainTv = (TextView) findViewById(R.id.market_main_tv);
        mtitlePopupWindow = new MtitlePopupWindow(this, shopMainList);
        Log.e("asdasasd", "initView: " + shopMainList.toString());
        ImageView mBack = (ImageView) findViewById(R.id.market_type_header_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        market_type_shopcar_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mtitlePopupWindow.showAsDropDown(v);
            }
        });
        mtitlePopupWindow.setOnPopupWindowClickListener(new MtitlePopupWindow.OnPopupWindowClickListener() {
            @Override
            public void onPopupWindowItemClick(int position) {
                mMarketTypeList.clear();
                requestData(shopMainList.get(position).getCateId() + "", 1, false);
            }
        });


        market_type_search_ed.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    RequestParams params = new RequestParams();
                    params.put("shopName", v.getText().toString().trim());
                    params.put("page", String.valueOf(page));
                    params.put("cateId", cateId);
                    params.put("pageSize", "15");
                    HttpUtil.post(Constant.TAKEAWAY_MAIN_LIST_URL, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if (statusCode == 200) {
                                Log.i("Tak", "onSuccess: " + new String(responseBody));

                                final String result = new String(responseBody);
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mMarketTypeList.clear();
                                        mMarketTypeList.addAll(JsonUtil.toList(result, ShopList.class));
                                        mRvAdapter.notifyDataSetChanged();
                                        closeKeyBoard();
//                                        mRecyclerView.smoothScrollBy(0, headerView.getMeasuredHeight());
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, final Throwable error) {
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mContext, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
                return false;
            }
        });
    }
    private void closeKeyBoard() {
        View view = getWindow().getDecorView();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    private void update() {

        mList = new ArrayList<>();
        mDotList = new ArrayList<>();
        //把imageview加入到list集合传入pageradapter
        for (int i = 0; i < entityList.size(); i++) {
            ImageView imageview = new ImageView(mContext);
            GlideUtil.displayImage(Constant.PHOTOBASEURL + entityList.get(i).getPhoto(), imageview);
            imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mList.add(imageview);
        }
        mAdapter = new MarketPagerAdapter(mList, entityList, mContext);
        mViewPager.setAdapter(mAdapter);
        // 设置自动轮播图片，5s后执行，周期是5s
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 0;
                if (autoCurrIndex == entityList.size() - 1) {
                    autoCurrIndex = -1;
                }
                message.arg1 = autoCurrIndex + 1;
                mHandler.sendMessage(message);
            }
        }, 5000, 5000);
    }


    private void initData() {
        //选中状态
        mMainTv.setTextColor(getResources().getColor(R.color.text_red));

//        mList = new ArrayList<>();
//        //把imageview加入到list集合传入pageradapter
//        for (int i = 0; i < 5; i++) {
//            ImageView imageview = new ImageView(mContext);
//            imageview.setBackgroundResource(R.drawable.market_type_banner);
//            imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            mList.add(imageview);
//        }
//
//
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


        View view= LayoutInflater.from(mContext).inflate(R.layout.viewpager,null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(mContext,180)));
        mViewPager = (ViewPager) view.findViewById(R.id.market_type_vp);

        mRecyclerView.addHeaderView(view);
//        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (mViewPager) {
//                    if (!mChangeListener.nowAction) {
//                        mChangeListener.mCurrentItem++;
//                        mHandler.obtainMessage().sendToTarget();
//                    }
//                }
//            }
//        }, 0, 5, TimeUnit.SECONDS);
//>>>>>>> .r30

        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));

        mRvAdapter = new MarketTypeRvAdapter(mContext, mMarketTypeList);


        mRecyclerView.setAdapter(mRvAdapter);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                requestData(cateId, 1, isLoadMore);
                mRecyclerView.refreshComplete();
                page = 1;

            }

            @Override
            public void onLoadMore() {

                page++;
                isLoadMore = true;
                requestData(cateId, page, isLoadMore);
                mRecyclerView.refreshComplete();
                isLoadMore = false;
            }
        });
        mRecyclerView.refresh();

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
                entityList = JsonUtil.toList(response.toString(), AdEntity.class);
                update();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("TAG_AD", "onFailure: " + statusCode + responseString);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mScheduledExecutorService.shutdown();
    }
}
