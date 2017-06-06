package com.sugood.app.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.TakeawayMarketLikeadapter;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.ShopInfo;
import com.sugood.app.entity.ShopList;
import com.sugood.app.global.Constant;
import com.sugood.app.util.GlideUtil;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Package :com.sugood.app.activity
 * Description :
 * Author :Rc3
 * Created at :2017/3/28 17:46.
 */

public class TakeawayMarketShopActivity extends AppCompatActivity {

    @BindView(R.id.takeaway_market_shop_detail_rv)
    XRecyclerView mXRecyclerView;
    @BindView(R.id.takeaway_shop_info_img)
    CircleImageView mShopImg;
    @BindView(R.id.takeaway_shop_info_tel)
    TextView mShopTel;
    @BindView(R.id.takeaway_market_shop_detail_header_back)
    ImageView mBack;
    @BindView(R.id.takeaway_shop_info_address)
    TextView mShopAddress;
    //    @BindView(R.id.takeaway_market_shop_farvorite_img)
//    ImageView mComment;
    @BindView(R.id.takeaway_market_shop_text_tv)
    TextView mTitle;
    @BindView(R.id.takeaway_market_shop_farvorite_img)
    ImageView mShoucang;

    Context mContext;
    boolean isLoadMore;
    int page = 1;
    TakeawayMarketLikeadapter mTakeawayMarketLikeadapter;
    List<ShopList> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takeaway_market_shop_view);
        ButterKnife.bind(this);
        mContext = this;
        initData();
        requestData();
        mShopTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + mShopTel.getText());
                intent.setData(data);
                startActivity(intent);
            }
        });
        mShoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SugoodApplication.isLogin) {
                    farvorite();
                } else {
                    startActivity(new Intent(TakeawayMarketShopActivity.this, LoginActivity.class));
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
        params.put("type", "2");
        Log.e("ssas", "farvorite: " + getIntent().getStringExtra("shopId"));
        HttpUtil.post(Constant.SHOP_COLLECtION_ADD, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.setToast(TakeawayMarketShopActivity.this, "收藏成功");
                            }
                        });
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, final byte[] responseBody, Throwable error) {
                        Log.i("sasa", "onFailure: " + new String(responseBody));

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.setToast(TakeawayMarketShopActivity.this, "已收藏该商店");
                            }
                        });
                    }

                }
        );
    }

    private void requestData() {
        RequestParams params = new RequestParams();
        params.put("shopId", getIntent().getStringExtra("shopId"));
        HttpUtil.post(Constant.SHOP_INFO_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("TakeawayopActivity", "onSuccess: " + new String(responseBody));
                final ShopInfo shopInfo = JsonUtil.toObject(new String(responseBody), ShopInfo.class);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        setText(shopInfo);
                    }
                });
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

    private void requestShopData(int page, final boolean isLoadMore) {

        RequestParams shopParams = new RequestParams();
        shopParams.put("shopId", getIntent().getStringExtra("shopId"));
        shopParams.put("pageSize", 10);
        shopParams.put("page", String.valueOf(page));
        HttpUtil.post(Constant.SHOP_PRODUCT_URL, shopParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    final String result = new String(responseBody);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (!isLoadMore) {
                                list.clear();
                            }
                            list.addAll(JsonUtil.toList(result, ShopList.class));
                            mTakeawayMarketLikeadapter.notifyDataSetChanged();

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


    private void setText(ShopInfo shopInfo) {
        GlideUtil.displayImage(Constant.PHOTOBASEURL + shopInfo.getPhoto(), mShopImg);
        mShopTel.setText(shopInfo.getTel());
        mShopAddress.setText(shopInfo.getAddr());
        mTitle.setText(shopInfo.getShopName());

    }

    private void initData() {


        mXRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));


        mXRecyclerView.setAdapter(mTakeawayMarketLikeadapter = new TakeawayMarketLikeadapter(list));
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                requestShopData(1, isLoadMore);
                mXRecyclerView.refreshComplete();
                page = 1;
            }

            @Override
            public void onLoadMore() {
                isLoadMore = true;
                page++;
                requestShopData(page, isLoadMore);
                mXRecyclerView.loadMoreComplete();
                isLoadMore = false;
            }
        });
        mXRecyclerView.refresh();
    }

    @OnClick(R.id.takeaway_market_shop_detail_header_back)
    void onBack() {
        finish();
    }

    @OnClick(R.id.takeaway_market_shop_farvorite_img)
    void onComment() {
        Intent intent = new Intent(mContext, AllCommentActivity.class);
        intent.putExtra("shopId", getIntent().getStringExtra("shopId"));
        startActivity(intent);
    }

}
