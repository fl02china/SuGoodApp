package com.sugood.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.TakeawayMainAdapter;
import com.sugood.app.adapter.TakeawayMainPagerAdapter;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.AdEntity;
import com.sugood.app.entity.TakeawayShop;
import com.sugood.app.fragment.TakeawayTypeFragment;
import com.sugood.app.global.Constant;
import com.sugood.app.listener.TakeawayPageChangeListener;
import com.sugood.app.util.GlideUtil;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.util.ToastUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Package :com.android.supermarket.takeaway
 * Description :
 * Author :Rc3
 * Created at :2017/2/27 16:35.
 */

public class TakeawayMainActivity extends BaseActivity {
    private static final String TAG = TakeawayMainActivity.class.getSimpleName();


    @BindView(R.id.takeaway_main_rv)
    XRecyclerView mRecyclerView;
    @BindView(R.id.takeaway_search_editText)
    EditText mSeacher;

    Context mContext;
    int TopRemarkNum = 0;
    int TopSoldNum = 1;
    int normalState = 2;
    int mainType = 0;
    int page = 1;
    boolean isLoadMore;
    List<Fragment> mList;
    List<ImageView> mDotList;
    List<TakeawayShop> mShopList;
    TakeawayMainAdapter rvAdapter;
    View headerView;
    List<AdEntity> EntityList;

    RelativeLayout mAllTypeLayout;
    RelativeLayout mMostSoldLayout;
    RelativeLayout mBestCommentLayout;
    ImageView takeaway_all_shops_img;
    ImageView takeaway_most_sold_shops_img;
    ImageView takeaway_best_comment_shops_img;


    TextView mAllTypeTv;
    TextView mMostSoldTv;
    TextView mBestCommentTv;

    ImageView takeaway_banner_img;
    ImageView takeaway_banner_img1;
    ImageView takeaway_banner_img2;
    ImageView takeaway_banner_img3;
    ImageView takeaway_banner_img4;
    ViewPager vp_classic;
    @BindView(R.id.takeaway_map)
    ImageView takeaway_map;
    private boolean isToast = false;
    private int autoCurrIndex = 0;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (msg.arg1 != 0) {
                        vp_classic.setCurrentItem(msg.arg1);
                    } else {
                        //false 当从末页调到首页是，不显示翻页动画效果，
                        vp_classic.setCurrentItem(msg.arg1, false);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takeaway_main_activity);
        ButterKnife.bind(this);
        mContext = this;

        initData();
        takeaway_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ToastUtil.setToast(TakeawayMainActivity.this, "百度地图已经删除");
//                isToast = false;
//                showLoading("获取位置");
//                SugoodApplication.mLocationClient.start();
//                SugoodApplication.mLocationClient.registerLocationListener(new BDLocationListener() {
//                    @Override
//                    public void onReceiveLocation(BDLocation bdLocation) {
//                        closeLoading();
//                        Log.e("LOCAtion", "onReceiveLocation: " + bdLocation.getAddrStr());
//                        if (!isToast) {
//                            isToast = true;
//                            ToastUtil.setToast(TakeawayMainActivity.this, bdLocation.getAddrStr().replace(bdLocation.getCountry(), ""));
//                            SugoodApplication.mLocationClient.stop();
//                        }
//
//                    }
//
//                    @Override
//                    public void onConnectHotSpotMessage(String s, int i) {
//
//                    }
//                });
            }
        });

    }

    @OnClick(R.id.takeaway_main_header_back)
    void onBack() {
        finish();
    }


    private void requestRvData(int page, int waicate, int xiaoPing, final boolean isLoadMore) {
        RequestParams params = new RequestParams();
        if (waicate != 0) {
            params.put("WaiCate", String.valueOf(waicate));
        }
        if (xiaoPing == TopRemarkNum) {
            params.put("xiaoPing", 0);
        } else if (xiaoPing == TopSoldNum) {
            params.put("xiaoPing", 1);
        }
        params.put("page", String.valueOf(page));
        params.put("pageSize", "15");
        HttpUtil.post(Constant.TAKEAWAY_MAIN_LIST_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    Log.i(TAG, "onSuccess: " + new String(responseBody));

                    final String result = new String(responseBody);
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            if (!isLoadMore) {
                                mShopList.clear();
                            }
                            mShopList.addAll(JsonUtil.toList(result, TakeawayShop.class));
                            rvAdapter.notifyDataSetChanged();
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

    private void initData() {


        headerView = LayoutInflater.from(mContext).inflate(R.layout.include_takeaway_main, null);
        initRVHeaderView(headerView);
        mRecyclerView.addHeaderView(headerView);
        mShopList = new ArrayList<>();

        rvAdapter = new TakeawayMainAdapter(mContext, mShopList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(rvAdapter);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.setNestedScrollingEnabled(true);
                onRvRefresh(0, normalState);
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.setNestedScrollingEnabled(true);
                page++;
                isLoadMore = true;
                requestRvData(page, 0, normalState, isLoadMore);
                mRecyclerView.loadMoreComplete();
                isLoadMore = false;
            }
        });

        mRecyclerView.refresh();


        mSeacher.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    RequestParams params = new RequestParams();
                    params.put("shopName", v.getText().toString().trim());
                    params.put("page", String.valueOf(page));
                    params.put("pageSize", "15");
                    HttpUtil.post(Constant.TAKEAWAY_MAIN_LIST_URL, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if (statusCode == 200) {
                                Log.i(TAG, "onSuccess: " + new String(responseBody));

                                final String result = new String(responseBody);
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {

                                        mShopList.clear();

                                        mShopList.addAll(JsonUtil.toList(result, TakeawayShop.class));
                                        rvAdapter.notifyDataSetChanged();
                                        closeKeyBoard();
                                        mRecyclerView.smoothScrollBy(0, headerView.getMeasuredHeight());
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

    private void initRVHeaderView(final View headerView) {

        mAllTypeLayout = (RelativeLayout) headerView.findViewById(R.id.takeaway_all_shops_rl);
        mMostSoldLayout = (RelativeLayout) headerView.findViewById(R.id.takeaway_most_sold_shops_rl);
        mBestCommentLayout = (RelativeLayout) headerView.findViewById(R.id.takeaway_best_comment_shops_rl);
        takeaway_all_shops_img = (ImageView) headerView.findViewById(R.id.takeaway_all_shops_img);
        takeaway_most_sold_shops_img = (ImageView) headerView.findViewById(R.id.takeaway_most_sold_shops_img);
        takeaway_best_comment_shops_img = (ImageView) headerView.findViewById(R.id.takeaway_best_comment_shops_img);
        vp_classic = (ViewPager) headerView.findViewById(R.id.vp_classic);

        mAllTypeTv = (TextView) headerView.findViewById(R.id.takeaway_all_shops_tv);
        mMostSoldTv = (TextView) headerView.findViewById(R.id.takeaway_most_sold_shops_tv);
        mBestCommentTv = (TextView) headerView.findViewById(R.id.takeaway_best_comment_shops_tv);

//        takeaway_banner_img = (ImageView) headerView.findViewById(R.id.takeaway_banner_img);
        takeaway_banner_img1 = (ImageView) headerView.findViewById(R.id.takeaway_banner_img1);
        takeaway_banner_img2 = (ImageView) headerView.findViewById(R.id.takeaway_banner_img2);
        takeaway_banner_img3 = (ImageView) headerView.findViewById(R.id.takeaway_banner_img3);
        takeaway_banner_img4 = (ImageView) headerView.findViewById(R.id.takeaway_banner_img4);

        takeaway_banner_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInetnt(0);
            }
        });
        takeaway_banner_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInetnt(1);
            }
        });
        takeaway_banner_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInetnt(2);
            }
        });
        takeaway_banner_img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInetnt(3);
            }
        });


        takeaway_all_shops_img.setImageResource(R.drawable.superscript_right_icon);
        takeaway_most_sold_shops_img.setImageResource(R.drawable.superscript_black_right_icon);
        takeaway_best_comment_shops_img.setImageResource(R.drawable.superscript_black_right_icon);
        mAllTypeTv.setTextColor(getResources().getColor(R.color.red));
        mMostSoldTv.setTextColor(getResources().getColor(R.color.grey));
        mBestCommentTv.setTextColor(getResources().getColor(R.color.grey));
//        takeaway_banner_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        /**
         *  请求广告图片
         */
        RequestParams params = new RequestParams();
        params.put("siteId", "93");
        HttpUtil.post(Constant.GOODFOODAD, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TAG_AD", "onSuccess: " + response);
                final List<AdEntity> adEntityList = JsonUtil.toList(response.toString(), AdEntity.class);
                initViewpager(adEntityList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("Failure", "onFailure: " + statusCode + responseString);
            }
        });

        RequestParams adParams = new RequestParams();
        adParams.put("siteId", "96");
        HttpUtil.post(Constant.GOODFOODAD, adParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TAG_AD", "onSuccess: " + response);
                EntityList = JsonUtil.toList(response.toString(), AdEntity.class);
                if (EntityList.size()>0){
                    GlideUtil.displayImage(Constant.PHOTOBASEURL + EntityList.get(0).getPhoto(), takeaway_banner_img1);
                    GlideUtil.displayImage(Constant.PHOTOBASEURL + EntityList.get(1).getPhoto(), takeaway_banner_img2);
                    GlideUtil.displayImage(Constant.PHOTOBASEURL + EntityList.get(2).getPhoto(), takeaway_banner_img3);
                    GlideUtil.displayImage(Constant.PHOTOBASEURL + EntityList.get(3).getPhoto(), takeaway_banner_img4);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("Failure", "onFailure: " + statusCode + responseString);
            }
        });

        mAllTypeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRvRefresh(0, 2);
//                View view = LayoutInflater.from(mContext).inflate(R.layout.takeaway_type_view, null);
//                final PopupWindow window = getPopupWindow(mContext, view, mAllTypeLayout);
//                final LinearLayout viewLayout = (LinearLayout) view.findViewById(R.id.takeaway_type_view_ll);
//                for (int i = 0; i < viewLayout.getChildCount(); i++) {
//                    final int num = i;
//
//                    viewLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mAllTypeTv.setText(((TextView) viewLayout.getChildAt(num)).getText().toString());
//
//                            mainType = num;
//                            window.dismiss();
//                        }
//                    });
//                }
                takeaway_all_shops_img.setImageResource(R.drawable.superscript_right_icon);
                takeaway_most_sold_shops_img.setImageResource(R.drawable.superscript_black_right_icon);
                takeaway_best_comment_shops_img.setImageResource(R.drawable.superscript_black_right_icon);
                mAllTypeTv.setTextColor(getResources().getColor(R.color.red));
                mMostSoldTv.setTextColor(getResources().getColor(R.color.grey));
                mBestCommentTv.setTextColor(getResources().getColor(R.color.grey));

            }
        });
        mMostSoldLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRvRefresh(0, 0);
//                View view = LayoutInflater.from(mContext).inflate(R.layout.takeaway_sold_view, null);
//                final PopupWindow window = getPopupWindow(mContext, view, mMostSoldLayout);
//                final LinearLayout viewLayout = (LinearLayout) view.findViewById(R.id.takeaway_sold_view_ll);
//                for (int i = 0; i < viewLayout.getChildCount(); i++) {
//                    final int num = i;
//                    viewLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mMostSoldTv.setText(((TextView) viewLayout.getChildAt(num)).getText().toString());
//                            if (num == 0) {
//                                onRvRefresh(mainType, TopSoldNum);
//                            } else {
//                                onRvRefresh(mainType, normalState);
//                            }
//
//                            window.dismiss();
//                        }
//                    });
//                }
//
//
                takeaway_all_shops_img.setImageResource(R.drawable.superscript_black_right_icon);
                takeaway_most_sold_shops_img.setImageResource(R.drawable.superscript_right_icon);
                takeaway_best_comment_shops_img.setImageResource(R.drawable.superscript_black_right_icon);
                mAllTypeTv.setTextColor(getResources().getColor(R.color.grey));
                mMostSoldTv.setTextColor(getResources().getColor(R.color.red));
                mBestCommentTv.setTextColor(getResources().getColor(R.color.grey));
            }
        });

        mBestCommentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRvRefresh(0, 1);
//                View view = LayoutInflater.from(mContext).inflate(R.layout.takeaway_comment_view, null);
//                final PopupWindow window = getPopupWindow(mContext, view, mBestCommentLayout);
//                final LinearLayout viewLayout = (LinearLayout) view.findViewById(R.id.takeaway_comment_view_ll);
//                for (int i = 0; i < viewLayout.getChildCount(); i++) {
//                    final int num = i;
//                    viewLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mBestCommentTv.setText(((TextView) viewLayout.getChildAt(num)).getText().toString());
//                            if (num == 0) {
//                                onRvRefresh(mainType, normalState);
//                            } else {
//                                onRvRefresh(mainType, TopRemarkNum);
//                            }
//
//                            window.dismiss();
//                        }
//                    });
//                }
//
                takeaway_all_shops_img.setImageResource(R.drawable.superscript_black_right_icon);
                takeaway_most_sold_shops_img.setImageResource(R.drawable.superscript_black_right_icon);
                takeaway_best_comment_shops_img.setImageResource(R.drawable.superscript_right_icon);
                mAllTypeTv.setTextColor(getResources().getColor(R.color.grey));
                mMostSoldTv.setTextColor(getResources().getColor(R.color.grey));
                mBestCommentTv.setTextColor(getResources().getColor(R.color.red));
            }
        });

        final ViewPager mViewpager = (ViewPager) headerView.findViewById(R.id.takeaway_main_vp);
        final LinearLayout mDotll = (LinearLayout) headerView.findViewById(R.id.takeaway_dot_ll);
//        final ImageView mBannerImg = (ImageView) headerView.findViewById( R.id.takeaway_banner_img);
        final LinearLayout mBannerLayout = (LinearLayout) headerView.findViewById(R.id.takeaway_banner_ll);
        final LinearLayout mNearShopLayout = (LinearLayout) headerView.findViewById(R.id.takeaway_near_shop_ll);
        mList = new ArrayList<>();
        mDotList = new ArrayList<>();
        mDotList.add((ImageView) headerView.findViewById(R.id.takeaway_dot1));
        mDotList.add((ImageView) headerView.findViewById(R.id.takeaway_dot2));
        for (int i = 0; i < 2; i++) {
            TakeawayTypeFragment fragment = new TakeawayTypeFragment();
            fragment.setListener(new TakeawayTypeFragment.OnItemClickListener() {
                @Override
                public void scroll() {


                    mRecyclerView.smoothScrollBy(0, headerView.getMeasuredHeight());
                }

                @Override
                public void onClick(int position) {

                    requestRvData(1, position, normalState, isLoadMore);
                }
            });
            mList.add(fragment);

        }
        TakeawayMainPagerAdapter adapter = new TakeawayMainPagerAdapter(getSupportFragmentManager(), mList);
        mViewpager.addOnPageChangeListener(new TakeawayPageChangeListener(mList, mDotList));
        mViewpager.setAdapter(adapter);

    }

    private void onRvRefresh(int waicate, int xingPing) {

        page = 1;
        requestRvData(page, waicate, xingPing, isLoadMore);
        mRecyclerView.refreshComplete();
    }


    private void startInetnt(int position) {
        Intent intent = new Intent();
        if (EntityList.get(position).getType().equals("1")) {
            intent.putExtra("tuanId", EntityList.get(position).getDiyid());
            intent.putExtra("shopId", "");
            intent.setClass(TakeawayMainActivity.this, TuanGouActivity.class);
            startActivity(intent);
        } else if (EntityList.get(position).getType().equals("2")) {
            intent.putExtra("shopId", EntityList.get(position).getDiyid());
            intent.setClass(TakeawayMainActivity.this, ShopDetailActivity.class);
            startActivity(intent);
        } else if (EntityList.get(position).getType().equals("3")) {
            intent.putExtra("shopId", EntityList.get(position).getDiyid());
            intent.setClass(TakeawayMainActivity.this, TakeawayShopDetailActivity.class);
            startActivity(intent);
        } else if (EntityList.get(position).getType().equals("4")) {
            intent.setClass(TakeawayMainActivity.this, TakeawayMarketDetailActivity.class);
            intent.putExtra("goodsId", EntityList.get(position).getDiyid());
            startActivity(intent);
        } else if (EntityList.get(position).getType().equals("5")) {
            intent.setClass(TakeawayMainActivity.this, TakeawayMarketShopDetailActivity.class);
            intent.putExtra("cateId", EntityList.get(position).getDiyid());
            startActivity(intent);
//                        intent.putExtra("asa", (Serializable) mShopMainList);
        } else {
            ToastUtil.setToast(TakeawayMainActivity.this, "正在建设中。。。");
        }

    }

    private PopupWindow getPopupWindow(Context context, View contentView, View targetView) {
        PopupWindow popupwindow = new PopupWindow(context);
        popupwindow.setOutsideTouchable(true);
        popupwindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupwindow.setContentView(contentView);
        popupwindow.showAsDropDown(targetView);
        return popupwindow;
    }

    private void closeKeyBoard() {
        View view = getWindow().getDecorView();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private ArrayList<ImageView> imageList = new ArrayList<ImageView>();

    private void initViewpager(final List<AdEntity> adEntityList) {
        for (int i = 0; i < adEntityList.size(); i++) {
            ImageView iv = new ImageView(mContext);
            GlideUtil.displayImage(Constant.PHOTOBASEURL + adEntityList.get(i).getPhoto(), iv);
            imageList.add(iv);
        }
        ImageAdapter pagerAdapter = new ImageAdapter(mContext, imageList, adEntityList);
        vp_classic.setAdapter(pagerAdapter);
        // 设置自动轮播图片，5s后执行，周期是5s
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 0;
                if (autoCurrIndex == adEntityList.size() - 1) {
                    autoCurrIndex = -1;
                }
                message.arg1 = autoCurrIndex + 1;
                mHandler.sendMessage(message);
            }
        }, 5000, 5000);
    }

    class ImageAdapter extends PagerAdapter {
        private Context context;
        private List<ImageView> imageList;
        private List<AdEntity> adEntityList;

        public ImageAdapter(Context context, List<ImageView> imageList, List<AdEntity> adEntityList) {
            this.context = context;
            this.imageList = imageList;
            this.adEntityList = adEntityList;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            container.addView(imageList.get(position));

            imageList.get(position).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    if (adEntityList.get(position).getType().equals("1")) {
                        intent.putExtra("tuanId", adEntityList.get(position).getDiyid());
                        intent.putExtra("shopId", "");
                        startActivity(intent);
                        intent.setClass(TakeawayMainActivity.this, TuanGouActivity.class);
                    } else if (adEntityList.get(position).getType().equals("2")) {
                        intent.putExtra("shopId", adEntityList.get(position).getDiyid());
                        intent.setClass(TakeawayMainActivity.this, ShopDetailActivity.class);
                        startActivity(intent);
                    } else if (adEntityList.get(position).getType().equals("3")) {
                        intent.putExtra("shopId", adEntityList.get(position).getDiyid());
                        intent.setClass(TakeawayMainActivity.this, TakeawayShopDetailActivity.class);
                        startActivity(intent);
                    } else if (adEntityList.get(position).getType().equals("4")) {
                        intent.setClass(TakeawayMainActivity.this, TakeawayMarketDetailActivity.class);
                        intent.putExtra("goodsId", adEntityList.get(position).getDiyid());
                        startActivity(intent);
                    } else if (adEntityList.get(position).getType().equals("5")) {
                        intent.setClass(TakeawayMainActivity.this, TakeawayMarketShopDetailActivity.class);
                        intent.putExtra("cateId", adEntityList.get(position).getDiyid());
                        startActivity(intent);
//                        intent.putExtra("asa", (Serializable) mShopMainList);
                    }else {
                        ToastUtil.setToast(TakeawayMainActivity.this,"暂不支持");
                    }

                }
            });

            return imageList.get(position);
        }

        @Override
        public int getCount() {
            return imageList.size();
        }


        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(imageList.get(position));
        }


    }


}
