package com.sugood.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.BankListPageAdapter;
import com.sugood.app.adapter.MarketPagerAdapter;
import com.sugood.app.adapter.TakeClassicAdapter;
import com.sugood.app.adapter.TakeawayMarketAdapter;
import com.sugood.app.entity.AdEntity;
import com.sugood.app.entity.ShopList;
import com.sugood.app.entity.ShopMain;
import com.sugood.app.global.Constant;
import com.sugood.app.listener.MarketPageChangeListener;
import com.sugood.app.util.GlideUtil;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.view.FullyGridLayoutManager;
import com.sugood.app.view.MyViewPager;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

import static com.sugood.app.R.id.takeaway_market_car_image;
import static com.sugood.app.R.id.takeaway_market_car_ll;
import static com.sugood.app.R.id.takeaway_market_digital_image;
import static com.sugood.app.R.id.takeaway_market_digital_ll;
import static com.sugood.app.R.id.takeaway_market_famer_market_image;
import static com.sugood.app.R.id.takeaway_market_famer_market_ll;
import static com.sugood.app.R.id.takeaway_market_furniture_ll;
import static com.sugood.app.R.id.takeaway_market_goods_image;
import static com.sugood.app.R.id.takeaway_market_goods_ll;
import static com.sugood.app.R.id.takeaway_market_house_machine_image;
import static com.sugood.app.R.id.takeaway_market_house_machine_ll;
import static com.sugood.app.R.id.takeaway_market_main_vp;
import static com.sugood.app.R.id.takeaway_market_newyear_goods_image;
import static com.sugood.app.R.id.takeaway_market_newyear_goods_ll;
import static com.sugood.app.R.id.takeaway_market_office_image;
import static com.sugood.app.R.id.takeaway_market_office_ll;
import static com.sugood.app.R.id.takeaway_market_study_image;
import static com.sugood.app.R.id.takeaway_market_study_ll;

/**
 * Package :com.sugood.app.activity
 * Description :
 * Author :Rc3
 * Created at :2017/3/27 14:22.
 */

public class TakeawayMarketActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private static final String TAG = TakeawayMarketActivity.class.getSimpleName();

    @BindView(R.id.takeawaw_market_rv)
    XRecyclerView mXRecyclerView;
    EditText takeaway_market_editText;
    List<ImageView> mList;
    List<ImageView> mDotList;
    TakeawayMarketAdapter mTakeawayMarketAdapter;
    Context mContext;
    ScheduledExecutorService mScheduledExecutorService;
    MarketPageChangeListener mChangeListener;
    MarketPagerAdapter mAdapter;
    List<ShopMain> mShopMainList = new ArrayList<>();
    boolean isLoadMore;
    int page = 1;
    List<ShopList> mShopList = new ArrayList<>();
    List<AdEntity> entityList = new ArrayList<>();

    //in the headerview
    MyViewPager vp_takeaway;

    ViewPager mViewPager;
    ImageView mCarImage;
    ImageView mOfficeImage;
    ImageView mGoodsImage;
    ImageView mStudyImage;
    ImageView mDigitalImage;
    ImageView mFamerMarketImage;
    ImageView mNewyearGoodsImage;
    ImageView mHouseMachineImage;
    private int autoCurrIndex = 0;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takeaway_market);
        ButterKnife.bind(this);
        mContext = this;
        initData();
    }

    private void initData() {

        takeaway_market_editText = (EditText) findViewById(R.id.takeaway_market_editText);
        takeaway_market_editText.setOnEditorActionListener(this);
        View headView = LayoutInflater.from(mContext).inflate(R.layout.include_shop_view, null);
        mXRecyclerView.addHeaderView(headView);
        initHeaderView(headView);
        mXRecyclerView.setLayoutManager(new FullyGridLayoutManager(mContext, 2));
        mXRecyclerView.setHasFixedSize(true);
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
        }, 0, 10, TimeUnit.SECONDS);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                vp_takeaway.setFocusable(false);
                requestRvData("", 1, isLoadMore);
                mXRecyclerView.refreshComplete();
                page = 1;
                vp_takeaway.setFocusable(true);

            }

            @Override
            public void onLoadMore() {
                page++;
                isLoadMore = true;
                requestRvData(takeaway_market_editText.getText().toString(), page, isLoadMore);
                isLoadMore = false;
                mXRecyclerView.loadMoreComplete();
            }
        });
        mXRecyclerView.setAdapter(mTakeawayMarketAdapter = new TakeawayMarketAdapter(mShopList));
        mXRecyclerView.refresh();
        mTakeawayMarketAdapter.setListener(new TakeawayMarketAdapter.ItemOnClickListener() {
            @Override
            public void onClick(View v, int position) {

                Intent intent = new Intent(mContext, TakeawayMarketDetailActivity.class);
                intent.putExtra("goodsId", mShopList.get(position).getGoodsId());
                startActivity(intent);
            }
        });
        requestData();
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

    private void initHeaderView(View headView) {

        vp_takeaway = (MyViewPager) headView.findViewById(R.id.vp_takeaway);


        mViewPager = ButterKnife.findById(headView, takeaway_market_main_vp);
        mCarImage = ButterKnife.findById(headView, takeaway_market_car_image);

        mOfficeImage = ButterKnife.findById(headView, takeaway_market_office_image);

        mGoodsImage = ButterKnife.findById(headView, takeaway_market_goods_image);

        mStudyImage = ButterKnife.findById(headView, takeaway_market_study_image);


        mDigitalImage = ButterKnife.findById(headView, takeaway_market_digital_image);


        mFamerMarketImage = ButterKnife.findById(headView, takeaway_market_famer_market_image);


        mNewyearGoodsImage = ButterKnife.findById(headView, takeaway_market_newyear_goods_image);


        mHouseMachineImage = ButterKnife.findById(headView, takeaway_market_house_machine_image);

//        ButterKnife.findById(headView, takeaway_market_car_ll).setOnClickListener(this);
//        ButterKnife.findById(headView, takeaway_market_office_ll).setOnClickListener(this);
//        ButterKnife.findById(headView, takeaway_market_goods_ll).setOnClickListener(this);
//        ButterKnife.findById(headView, takeaway_market_study_ll).setOnClickListener(this);
//        ButterKnife.findById(headView, takeaway_market_digital_ll).setOnClickListener(this);
//        ButterKnife.findById(headView, takeaway_market_famer_market_ll).setOnClickListener(this);
//        ButterKnife.findById(headView, takeaway_market_newyear_goods_ll).setOnClickListener(this);
//        ButterKnife.findById(headView, takeaway_market_house_machine_ll).setOnClickListener(this);


    }

    private void requestRvData(final String str, int page, final boolean isLoadMore) {
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(str)) {
            params.put("intro", str);
        }
        params.put("page", page);
        params.put("pageSize", 20);
        HttpUtil.post(Constant.SHOP_MAIN_LIST_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    final String result = new String(responseBody);
                    Log.i(TAG, "onSuccess: " + new String(responseBody));

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (!isLoadMore) {
                                mShopList.clear();
                            }
                            if (!TextUtils.isEmpty(str) && !isLoadMore) {
                                mShopList.clear();
                            }
                            mShopList.addAll(JsonUtil.toList(result, ShopList.class));
                            mTakeawayMarketAdapter.notifyDataSetChanged();
                        }
                    });
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, final Throwable error) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(mContext, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void requestData() {
        RequestParams params = new RequestParams();
        params.put("cateId", "0");
        HttpUtil.post(Constant.SHOP_MAIN_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + statusCode);
                if (statusCode == 200) {
                    mShopMainList.addAll(JsonUtil.toList(new String(responseBody), ShopMain.class));

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            setImageData(mShopMainList);
                        }
                    });
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, final Throwable error) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScheduledExecutorService.shutdown();
    }

    public void setImageData(List<ShopMain> dataList) {
        if (dataList != null && dataList.size() > 0) {

            BankListPageAdapter adapter = new BankListPageAdapter();
            ArrayList<View> viewList = new ArrayList<>();
            int pages = (dataList.size() - 1) / 8 + 1;
            if (dataList.isEmpty()) {
                pages = 0;
            }
            for (int i = 0; i < pages; i++) {
                GridView gv_bank = new GridView(this);
                viewList.add(gv_bank);
                final TakeClassicAdapter bankGridAdapeter = new TakeClassicAdapter(this);
                gv_bank.setGravity(Gravity.CENTER);
                gv_bank.setNumColumns(4);
                gv_bank.setAdapter(bankGridAdapeter);
                List<ShopMain> bankList = new ArrayList<>();
                if (dataList.size() % 8 == 0) {
                    for (int n = i * 8; n <= (i + 1) * 8 - 1; n++) {
                        bankList.add(dataList.get(n));
                    }
                    bankGridAdapeter.setDataList(bankList);
                    bankGridAdapeter.notifyDataSetChanged();
                } else {

                    if (i < pages - 1) {
                        for (int n = i * 8; n <= (i + 1) * 8 - 1; n++) {
                            bankList.add(dataList.get(n));
                        }
                        bankGridAdapeter.setDataList(bankList);
                        bankGridAdapeter.notifyDataSetChanged();
                    } else {
                        bankGridAdapeter.setDataList(dataList.subList(i * 8, i * 8 + dataList.size() % 8));
                        for (int n = i * 8; n <= i * 8 + dataList.size() % 8 - 1; n++) {
                            bankList.add(dataList.get(n));
                        }
                        bankGridAdapeter.setDataList(bankList);
                        bankGridAdapeter.notifyDataSetChanged();
                    }
                }
                gv_bank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.e(TAG, "onItemClick: " + mShopMainList.get(position).getCateId());
                        ShopMain smain = (ShopMain) bankGridAdapeter.getItem(position);
                        startIntent(smain.getCateId());
                    }
                });

            }
            adapter.setViewSize(viewList);
            vp_takeaway.setAdapter(adapter);
            adapter.notifyDataSetChanged();
//
//            GlideUtil.displayImage(Constant.PHOTOBASEURL + dataList.get(12).getPhoto(), mCarImage);
//            GlideUtil.displayImage(Constant.PHOTOBASEURL + dataList.get(13).getPhoto(), mOfficeImage);
//            GlideUtil.displayImage(Constant.PHOTOBASEURL + dataList.get(15).getPhoto(), mGoodsImage);
//            GlideUtil.displayImage(Constant.PHOTOBASEURL + dataList.get(16).getPhoto(), mStudyImage);
//            GlideUtil.displayImage(Constant.PHOTOBASEURL + dataList.get(17).getPhoto(), mDigitalImage);
//            GlideUtil.displayImage(Constant.PHOTOBASEURL + dataList.get(18).getPhoto(), mFamerMarketImage);

        }


    }

    @OnClick(R.id.takeaway_market_header_back)
    void onBack() {
        finish();

    }


    void startIntent(int shopId) {

        Intent intent = new Intent(this, TakeawayMarketShopDetailActivity.class);
        intent.putExtra("cateId", shopId);
        intent.putExtra("asa", (Serializable) mShopMainList);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.takeaway_market_shoes_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(0) != null)
//                    startIntent(mShopMainList.get(0).getCateId());
//                break;
//            case R.id.takeaway_market_child_clothes_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(1) != null)
//                    startIntent(mShopMainList.get(1).getCateId());
//                break;
//            case R.id.takeaway_market_clothes_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(2) != null)
//                    startIntent(mShopMainList.get(2).getCateId());
//                break;
//            case R.id.takeaway_market_health_care_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(3) != null)
//                    startIntent(mShopMainList.get(3).getCateId());
//                break;
//            case R.id.takeaway_market_diamonds_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(4) != null)
//                    startIntent(mShopMainList.get(4).getCateId());
//                break;
//            case R.id.takeaway_market_outdoor_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(5) != null)
//                    startIntent(mShopMainList.get(5).getCateId());
//                break;
//            case R.id.takeaway_market_wedding_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(6) != null)
//                    startIntent(mShopMainList.get(6).getCateId());
//                break;
//            case R.id.takeaway_market_game_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(7) != null)
//                    startIntent(mShopMainList.get(7).getCateId());
//                break;
//            case R.id.takeaway_market_food_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(8) != null)
//                    startIntent(mShopMainList.get(8).getCateId());
//                break;
//            case R.id.takeaway_market_flower_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(9) != null)
//                    startIntent(mShopMainList.get(9).getCateId());
//                break;
//            case R.id.takeaway_market_building_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(10) != null)
//                    startIntent(mShopMainList.get(10).getCateId());
//                break;
//            case takeaway_market_furniture_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(11) != null)
//                    startIntent(mShopMainList.get(11).getCateId());
//                break;
//            case R.id.takeaway_market_car_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(12) != null)
//                    startIntent(mShopMainList.get(12).getCateId());
//                break;
//            case R.id.takeaway_market_office_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(13) != null)
//                    startIntent(mShopMainList.get(13).getCateId());
//                break;
//            case R.id.takeaway_market_goods_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(14) != null)
//                    startIntent(mShopMainList.get(14).getCateId());
//                break;
//            case R.id.takeaway_market_study_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(15) != null)
//                    startIntent(mShopMainList.get(15).getCateId());
//                break;
//            case takeaway_market_digital_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(16) != null)
//                    startIntent(mShopMainList.get(16).getCateId());
//                break;
//            case takeaway_market_famer_market_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(17) != null)
//                    startIntent(mShopMainList.get(17).getCateId());
//                break;
//            case takeaway_market_newyear_goods_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(18) != null)
//                    startIntent(mShopMainList.get(18).getCateId());
//                break;
//            case takeaway_market_house_machine_ll:
//                if (mShopMainList.size() > 0 && mShopMainList.get(19) != null)
//                    startIntent(mShopMainList.get(19).getCateId());
//                break;
        }
    }

    /**
     * 监听
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            // 当按了搜索之后关闭软键盘
            ((InputMethodManager) takeaway_market_editText.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    TakeawayMarketActivity.this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            requestRvData(takeaway_market_editText.getText().toString(), 1, false);
            return true;
        }
        return false;
    }


}
