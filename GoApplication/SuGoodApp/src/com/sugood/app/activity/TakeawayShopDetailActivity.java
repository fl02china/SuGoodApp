package com.sugood.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.TakeawayShopPagerAdapter;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.ShopOrder;
import com.sugood.app.entity.TakeawayShopInfo;
import com.sugood.app.fragment.TakeawayCommentFragment;
import com.sugood.app.fragment.TakeawayDescFragment;
import com.sugood.app.fragment.TakeawayShopcarFragment;
import com.sugood.app.global.Constant;
import com.sugood.app.listener.ViewdisMissListener;
import com.sugood.app.util.GlideUtil;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.util.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Package :com.android.supermarket.takeaway
 * Description :
 * Author :Rc3
 * Created at :2017/2/28 14:37.
 */

public class TakeawayShopDetailActivity extends AppCompatActivity {

    private static final String TAG = TakeawayShopDetailActivity.class.getSimpleName();

    Context mContext;
    List<ShopOrder> mList;
    @BindView(R.id.takeaway_shop_tabs)
    TabLayout mTab;
    @BindView(R.id.takeaway_shopcar_view1)
    View mView;
    @BindView(R.id.takeaway_shop_description_vp)
    ViewPager mViewpager;
    //高斯模糊的view
    @BindView(R.id.takeaway_shop_description_rl)
    RelativeLayout mBlurLayout;
    @BindView(R.id.takeaway_shop_detail_text_tv)
    TextView mShopNameTv;
    @BindView(R.id.takeaway_shop_description_tv)
    TextView mShopName;
    @BindView(R.id.takeaway_shop_detail_img)
    CircleImageView mShopImage;
    @BindView(R.id.takeaway_shop_delivary_price_tv)
    TextView mDeliveryPrice;
    @BindView(R.id.takeaway_shop_work_time)
    TextView mWorktime;
    @BindView(R.id.takeaway_shop_detail_header_back)
    ImageView mBack;
    @BindView(R.id.takeaway_shop_detail_farvorite_img)
    ImageView mFarvorite;
    List<TakeawayShopInfo> shopInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takeaway_shop_detail_activity);
        ButterKnife.bind(this);
        mContext = this;
        initView();
        requestData();

        SugoodApplication.getInstance().getShopCarProductList().clear();

    }

    @OnClick(R.id.takeaway_shop_detail_farvorite_img)
    void collection() {

    }

    private void initData() {
        //renderscript高斯模糊
//<<<<<<< .mine
////        Bitmap bitmap = BlurBitmapUtil.getBlurBitmap(mContext, BitmapFactory.decodeResource(getResources(), R.drawable.shop_icon), 20f);
//||||||| .r24
//        Bitmap bitmap = BlurBitmapUtil.getBlurBitmap(mContext, BitmapFactory.decodeResource(getResources(), R.drawable.shop_icon), 20f);
//=======
//      //  Bitmap bitmap = BlurBitmapUtil.getBlurBitmap(mContext, BitmapFactory.decodeResource(getResources(), R.color.red), 20f);
//>>>>>>> .r25
//
//<<<<<<< .mine
////        mBlurLayout.setBackground(new BitmapDrawable(bitmap));
//||||||| .r24
//        mBlurLayout.setBackground(new BitmapDrawable(bitmap));
//=======
//        //mBlurLayout.setBackground(new BitmapDrawable(bitmap));
//>>>>>>> .r25
//        mBlurLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(mContext, TakeawayAboutShopActivity.class));
//            }
//        });


        //viewpager
        List<Fragment> mFragmentList = new ArrayList<>();
        TakeawayShopcarFragment fragment1 = new TakeawayShopcarFragment();
        TakeawayCommentFragment fragment2 = new TakeawayCommentFragment();
        TakeawayDescFragment fragment3 = new TakeawayDescFragment();
        fragment1.setListner(new ViewdisMissListener() {


            @Override
            public void setViewVisble() {
                mView.setVisibility(View.VISIBLE);
            }

            @Override
            public void setViewGone() {
                mView.setVisibility(View.GONE);
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("shopId", getIntent().getStringExtra("shopId"));
        bundle.putSerializable("shop", shopInfo.get(0).getEle().get(0));

        fragment1.setArguments(bundle);
        fragment2.setArguments(bundle);
        fragment3.setArguments(bundle);
        mFragmentList.add(fragment1);
        mFragmentList.add(fragment2);
        mFragmentList.add(fragment3);


        TakeawayShopPagerAdapter pagerAdapter = new TakeawayShopPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewpager.setAdapter(pagerAdapter);
        mTab.setupWithViewPager(mViewpager);


    }

    private void initView() {
        mViewpager = (ViewPager) findViewById(R.id.takeaway_shop_description_vp);
        mView = findViewById(R.id.takeaway_shopcar_view1);
        mTab = (TabLayout) findViewById(R.id.takeaway_shop_tabs);
        mBlurLayout = (RelativeLayout) findViewById(R.id.takeaway_shop_description_rl);
        mFarvorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SugoodApplication.isLogin) {
                    farvorite();
                } else {
                    startActivity(new Intent(TakeawayShopDetailActivity.this, LoginActivity.class));
                }

            }
        });
    }

    /**
     * 店铺收藏
     */
    private void farvorite() {
        RequestParams params = new RequestParams();
        params.put("userId", SugoodApplication.user.getUserId());
        params.put("shopId", getIntent().getStringExtra("shopId"));
        params.put("type", "1");
        Log.e(TAG, "farvorite: " + getIntent().getStringExtra("shopId"));
        HttpUtil.post(Constant.SHOP_COLLECtION_ADD, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.setToast(TakeawayShopDetailActivity.this, "收藏成功");
                            }
                        });
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, final byte[] responseBody, Throwable error) {
                        Log.i(TAG, "onFailure: " + new String(responseBody));

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.setToast(TakeawayShopDetailActivity.this, "已收藏该商店");
                            }
                        });
                    }

                }
        );
    }

    private void requestData() {


        RequestParams params = new RequestParams();

        params.put("shopId", getIntent().getStringExtra("shopId"));
        HttpUtil.post(Constant.TAKEAWAY_MENU_LIST_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String result = new String(responseBody);
                    shopInfo = JsonUtil.toList(result, TakeawayShopInfo.class);
                    initData();
                    if (shopInfo.get(0).getEle().size() > 0) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                setText(shopInfo.get(0).getEle().get(0));
                            }
                        });

                    }

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

    @OnClick(R.id.takeaway_shop_detail_header_back)
    void onBack() {
        finish();
    }


    public void setText(TakeawayShopInfo.EleBean text) {

        mShopNameTv.setText(text.getShopName());
        mShopName.setText(text.getShopName());
        GlideUtil.displayImage(Constant.PHOTOBASEURL + text.getPhoto(), mShopImage);
        mWorktime.setText(text.getIntro());
        mDeliveryPrice.setText("起送价格 " + text.getSinceMoney() + "元");
    }
}
