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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.MarketPagerAdapter;
import com.sugood.app.adapter.TakeawayMarketLikeadapter;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.AdEntity;
import com.sugood.app.entity.ShopCarProduct;
import com.sugood.app.entity.ShopList;
import com.sugood.app.entity.ShopListDetail;
import com.sugood.app.entity.TakeawayShop;
import com.sugood.app.entity.TakeawayShopInfo;
import com.sugood.app.global.Constant;
import com.sugood.app.listener.MarketPageChangeListener;
import com.sugood.app.util.GlideUtil;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.util.ToastUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import io.realm.Realm;

/**
 * Package :com.sugood.app.activity
 * Description :
 * Author :Rc3
 * Created at :2017/3/28 14:55.
 */

public class TakeawayMarketDetailActivity extends AppCompatActivity {

    private static final String TAG = TakeawayMarketDetailActivity.class.getSimpleName();
    @BindView(R.id.takeaway_market_shop_dot1)
    ImageView mImageView1;
    @BindView(R.id.takeaway_market_shop_dot2)
    ImageView mImageView2;
    @BindView(R.id.takeaway_market_shop_dot3)
    ImageView mImageView3;
    @BindView(R.id.takeaway_market_shop_dot4)
    ImageView mImageView4;
    @BindView(R.id.takeaway_market_shop_dot5)
    ImageView mImageView5;
    @BindView(R.id.takeaway_market_shop_dot6)
    ImageView mImageView6;
    @BindView(R.id.takeaway_market_shop_detail_product_desc_image1)
    ImageView mBanner1;
    @BindView(R.id.takeaway_market_shop_detail_product_desc_image2)
    ImageView mBanner2;
    @BindView(R.id.takeaway_market_shop_detail_product_desc_image3)
    ImageView mBanner3;
    @BindView(R.id.takeaway_market_shop_detail_product_desc_image4)
    ImageView mBanner4;
    @BindView(R.id.takeaway_market_shop_detail_product_desc_image5)
    ImageView mBanner5;
    @BindView(R.id.takeaway_market_shop_detail_product_name)
    TextView mTitle;
    @BindView(R.id.takeaway_market_shop_detail_product_desc)
    TextView mDesc;
    @BindView(R.id.takeaway_market_shop_detail_price)
    TextView mPrice;
    @BindView(R.id.takeaway_market_shop_detail_enter_shop_btn)
    Button mShopBtn;
    @BindView(R.id.takeaway_market_shop_detail_header_back)
    ImageView mBack;
    @BindView(R.id.takeaway_market_shop_detail_vp)
    ViewPager mViewPager;
    @BindView(R.id.takeaway_market_shop_detail_product_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.takeaway_market_shop_detail_farvorite_img)
    ImageView mGoodsFarvorite;

    @BindView(R.id.add_shopcar_btn)
    Button mAddShopCar;
    @BindView(R.id.shop_buynow_btn)
    Button mBuy;

    List<ShopListDetail> mShopDetailList = new ArrayList<>();
    ShopListDetail shopListDetail;
    Context mContext;
    ScheduledExecutorService mScheduledExecutorService;
    MarketPageChangeListener mChangeListener;
    MarketPagerAdapter mAdapter;
    List<ImageView> mList;
    List<ImageView> mDotList;
    List<ShopList> list = new ArrayList<>();
    List<AdEntity> adEntityList = new ArrayList<>();
    String goodsId;
    TakeawayShopInfo.EleBean shop = new TakeawayShopInfo.EleBean();
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mViewPager.setCurrentItem(mChangeListener.mCurrentItem);
        }
    };
    TakeawayMarketLikeadapter adapter;
    Realm mRealm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takeaway_market_shop_detail_view);
        ButterKnife.bind(this);
        mContext = this;

        goodsId = getIntent().getStringExtra("goodsId");
        RequestParams params = new RequestParams();
        params.put("goodsId", goodsId);
        HttpUtil.post(Constant.SHOP_MAIN_LIST_DETAIL_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    Log.i(TAG, "onSuccess: " + new String(responseBody));
                    mShopDetailList.addAll(JsonUtil.toList(new String(responseBody), ShopListDetail.class));

                    new Handler().post(new Runnable() {


                        @Override
                        public void run() {
                            setData(mShopDetailList);
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

        mGoodsFarvorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                farvorite();
            }
        });
    }

    /**
     * 商品收藏
     */
    private void farvorite() {
        RequestParams params = new RequestParams();
        params.put("userId", SugoodApplication.user.getUserId());
        params.put("goodsId", goodsId);
        Log.e(TAG, "farvorite: " + getIntent().getStringExtra("shopId"));
        HttpUtil.post(Constant.GOODS_COLLECtION_ADD, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.setToast(TakeawayMarketDetailActivity.this, "收藏成功");
                            }
                        });
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, final byte[] responseBody, Throwable error) {
                        Log.i(TAG, "onFailure: " + new String(responseBody));

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.setToast(TakeawayMarketDetailActivity.this, "已收藏该商店");
                            }
                        });
                    }

                }
        );
    }

    @OnClick(R.id.takeaway_market_shop_detail_header_back)
    void back() {
        finish();
    }

    @OnClick(R.id.takeaway_market_shop_detail_enter_shop_btn)
    void enterShop() {
        Intent intent = new Intent(mContext, TakeawayMarketShopActivity.class);
        intent.putExtra("shopId", mShopDetailList.get(0).getShopId());
        startActivity(intent);
    }

//    private void update() {
//
//        mList = new ArrayList<>();
//        mDotList = new ArrayList<>();
//        //把imageview加入到list集合传入pageradapter
//        for (int i = 0; i < adEntityList.size(); i++) {
//            ImageView imageview = new ImageView(mContext);
//            GlideUtil.displayImage(Constant.PHOTOBASEURL + adEntityList.get(i).getPhoto(), imageview);
//            imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            mList.add(imageview);
//        }
//        mAdapter = new MarketPagerAdapter(mList, adEntityList, mContext);
//        mViewPager.setAdapter(mAdapter);
//
//    }

    private void initData(String imageUrl) {
//
//        /**
//         *  请求广告图片
//         */
//        RequestParams photoParams = new RequestParams();
//        photoParams.put("siteId", "97");
//        HttpUtil.post(Constant.GOODFOODAD, photoParams, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                super.onSuccess(statusCode, headers, response);
//                Log.e("TAG_AD", "onSuccess: " + response);
//                adEntityList = JsonUtil.toList(response.toString(), AdEntity.class);
//                update();
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
//                Log.e("TAG_AD", "onFailure: " + statusCode + responseString);
//            }
//        });

        mList = new ArrayList<>();
        //把imageview加入到list集合传入pageradapter
        for (int i = 0; i < 6; i++) {
            ImageView imageview = new ImageView(mContext);
            GlideUtil.displayImage(imageUrl, imageview);

            mList.add(imageview);
        }
        mAdapter = new MarketPagerAdapter(mList);

        mDotList = new ArrayList<>();

        mDotList.add(mImageView1);
        mDotList.add(mImageView2);
        mDotList.add(mImageView3);
        mDotList.add(mImageView4);
        mDotList.add(mImageView5);
        mDotList.add(mImageView6);

        mViewPager.setCurrentItem(Integer.MAX_VALUE / 4);
        mViewPager.addOnPageChangeListener(mChangeListener = new MarketPageChangeListener(mList, mDotList));
        mViewPager.setAdapter(mAdapter);

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

        for (int i = 0; i < 9; i++) {
            list.add(new ShopList());
        }

        adapter = new TakeawayMarketLikeadapter(list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);

    }

    private void setData(List<ShopListDetail> list) {
        shopListDetail = list.get(0);
        mTitle.setText(shopListDetail.getTitle());
        mDesc.setText(shopListDetail.getIntro());
        GlideUtil.displayImage(Constant.PHOTOBASEURL + shopListDetail.getPhoto(), mBanner1);
//        GlideUtil.displayImage(Constant.PHOTOBASEURL + shopListDetail.getPhoto(), mBanner2);
//        GlideUtil.displayImage(Constant.PHOTOBASEURL + shopListDetail.getPhoto(), mBanner3);
//        GlideUtil.displayImage(Constant.PHOTOBASEURL + shopListDetail.getPhoto(), mBanner4);
//        GlideUtil.displayImage(Constant.PHOTOBASEURL + shopListDetail.getPhoto(), mBanner5);
        initData(Constant.PHOTOBASEURL + shopListDetail.getPhoto());
        mPrice.setText(Double.parseDouble(shopListDetail.getPrice()+"")/100 + "元");

    }


    @OnClick(R.id.shop_buynow_btn)
    void buy() {
        if (SugoodApplication.isLogin) {
            if (shopListDetail != null) {
                SugoodApplication.getInstance().getShopCarProductList().clear();
                add();
                Intent intent = new Intent();
                shop.setLogistics(0.0);
                shop.setShopId(Integer.parseInt(shopListDetail.getShopId()));
                shop.setShopName(shopListDetail.getTitle());
                intent.putExtra("shop", shop);
                intent.putExtra("type", "shop");
                intent.setClass(mContext, TakeawaySubmitOrderActivity.class);
                startActivity(intent);
            }


        } else {
            startActivity(new Intent(mContext, LoginActivity.class));
        }
    }

    @OnClick(R.id.add_shopcar_btn)
    void addShopCar() {
        add();
        Toast.makeText(mContext, "加入购物车成功", Toast.LENGTH_SHORT).show();

    }


    private void add() {
        List<ShopCarProduct> shopCarList = SugoodApplication.getInstance().getShopCarProductList();
        ShopCarProduct shopCar = new ShopCarProduct();
        shopCar.setFoodAmount(1);
        shopCar.setFoodName(shopListDetail.getTitle());
        shopCar.setProductId(goodsId);
        shopCar.setFoodPrice(Double.parseDouble(shopListDetail.getPrice() + "") / 100 + "");
        shopCarList.add(shopCar);
        SugoodApplication.getInstance().setShopCarProductList(shopCarList);


        //加入数据库

   /*     mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                 ShopCarProduct shopCar = realm.createObject(ShopCarProduct.class);
                shopCar.setFoodAmount(1);

                shopCar.setFoodName(shopListDetail.getTitle());
                shopCar.setProductId(goodsId);
                shopCar.setFoodPrice(parsePriceInt(shopListDetail.getPrice()));
                ShopCarList shopCarList=realm.createObject(ShopCarList.class);
                RealmList<ShopCarProduct> list=new RealmList<ShopCarProduct>();
                list.add(shopCar);
                shopCarList.setShopCarProducts(list);

            }
        });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScheduledExecutorService.shutdown();
    }

//    private String parsePrice(int price) {
//        String thePrice = price + "";
//        return thePrice.substring(0, thePrice.length() - 2) + "." + thePrice.substring(thePrice.length() - 2);
//    }
//
//    private String parsePriceInt(int price) {
//        String thePrice = price + "";
//        return thePrice.substring(0, thePrice.length() - 2);
//    }
}
