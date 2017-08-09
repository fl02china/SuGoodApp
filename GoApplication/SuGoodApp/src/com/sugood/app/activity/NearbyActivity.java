package com.sugood.app.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
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
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.TakeawayMainAdapter;
import com.sugood.app.adapter.TakeawayMainPagerAdapter;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.TakeawayShop;
import com.sugood.app.fragment.TakeawayTypeFragment;
import com.sugood.app.global.Constant;
import com.sugood.app.listener.TakeawayPageChangeListener;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

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

public class NearbyActivity extends BaseActivity {
    private static final String TAG = TakeawayMainActivity.class.getSimpleName();


    @BindView(R.id.takeaway_main_rv)
    XRecyclerView mRecyclerView;
    @BindView(R.id.takeaway_search_editText)
    EditText mSeacher;
    @BindView(R.id.takeaway_main_header_back)
    ImageView mBack;
    @BindView(R.id.takeaway_map)
    ImageView takeaway_map;
    ImageView takeaway_all_shops_img;
    ImageView takeaway_most_sold_shops_img;
    ImageView takeaway_best_comment_shops_img;

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
    private boolean isToast = false;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takeaway_main_activity);
        ButterKnife.bind(this);
        mContext = this;
        initData();
        mBack.setVisibility(View.GONE);

        mSeacher.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    RequestParams params = new RequestParams();
                    params.put("shopName", v.getText().toString().trim());
                    params.put("page", String.valueOf(page));
                    params.put("pageSize", "15");
                    params.put("lat", SugoodApplication.lat);
                    params.put("lng", SugoodApplication.lng);
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

        takeaway_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToastUtil.setToast(NearbyActivity.this, "百度地图已经删除");
               // SugoodApplication.locationClient.startLocation();
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
//                            ToastUtil.setToast(NearbyActivity.this, bdLocation.getAddrStr().replace(bdLocation.getCountry(), ""));
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

//    @OnClick(R.id.takeaway_main_header_back)
//    void onBack() {
//        finish();
//    }


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
        params.put("lat", SugoodApplication.lat);
        params.put("lng", SugoodApplication.lng);
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


        View headerView = LayoutInflater.from(mContext).inflate(R.layout.include_takeaway_main, null);
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


        mSeacher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initRVHeaderView(final View headerView) {

        final RelativeLayout mAllTypeLayout = (RelativeLayout) headerView.findViewById(R.id.takeaway_all_shops_rl);
        final RelativeLayout mMostSoldLayout = (RelativeLayout) headerView.findViewById(R.id.takeaway_most_sold_shops_rl);
        final RelativeLayout mBestCommentLayout = (RelativeLayout) headerView.findViewById(R.id.takeaway_best_comment_shops_rl);
        final TextView mAllTypeTv = (TextView) headerView.findViewById(R.id.takeaway_all_shops_tv);
        final TextView mMostSoldTv = (TextView) headerView.findViewById(R.id.takeaway_most_sold_shops_tv);
        final TextView mBestCommentTv = (TextView) headerView.findViewById(R.id.takeaway_best_comment_shops_tv);
        takeaway_all_shops_img = (ImageView) headerView.findViewById(R.id.takeaway_all_shops_img);
        takeaway_most_sold_shops_img = (ImageView) headerView.findViewById(R.id.takeaway_most_sold_shops_img);
        takeaway_best_comment_shops_img = (ImageView) headerView.findViewById(R.id.takeaway_best_comment_shops_img);
        takeaway_all_shops_img.setImageResource(R.drawable.superscript_black_right_icon);
        takeaway_most_sold_shops_img.setImageResource(R.drawable.superscript_black_right_icon);
        takeaway_best_comment_shops_img.setImageResource(R.drawable.superscript_black_right_icon);
        mAllTypeTv.setTextColor(getResources().getColor(R.color.grey));
        mMostSoldTv.setTextColor(getResources().getColor(R.color.grey));
        mBestCommentTv.setTextColor(getResources().getColor(R.color.grey));

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
//
//
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
//
//                            } else {
//                                onRvRefresh(mainType, normalState);
//                            }
//
//                            window.dismiss();
//                        }
//                    });
//                }
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
//
//                            }
//
//                            window.dismiss();
//                        }
//                    });
//                }
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
        final ViewPager mBannerImg = (ViewPager) headerView.findViewById(R.id.vp_classic);
        final LinearLayout mBannerLayout = (LinearLayout) headerView.findViewById(R.id.takeaway_banner_ll);
        final LinearLayout mNearShopLayout = (LinearLayout) headerView.findViewById(R.id.takeaway_near_shop_ll);
        ImageView takeaway_banner_img4 = (ImageView) headerView.findViewById(R.id.takeaway_banner_img4);
        ImageView takeaway_banner_img3 = (ImageView) headerView.findViewById(R.id.takeaway_banner_img3);
        ImageView takeaway_banner_img2 = (ImageView) headerView.findViewById(R.id.takeaway_banner_img2);
        ImageView takeaway_banner_img1 = (ImageView) headerView.findViewById(R.id.takeaway_banner_img1);
        takeaway_banner_img1.setVisibility(View.GONE);
        takeaway_banner_img2.setVisibility(View.GONE);
        takeaway_banner_img3.setVisibility(View.GONE);
        takeaway_banner_img4.setVisibility(View.GONE);
        mViewpager.setVisibility(View.GONE);
        mDotll.setVisibility(View.GONE);
        mBannerImg.setVisibility(View.GONE);
        mBannerLayout.setVisibility(View.GONE);
//        mNearShopLayout.setVisibility(View.GONE);
//        mList = new ArrayList<>();
//        mDotList = new ArrayList<>();
//        mDotList.add((ImageView) headerView.findViewById(R.id.takeaway_dot1));
//        mDotList.add((ImageView) headerView.findViewById(R.id.takeaway_dot2));
//        for (int i = 0; i < 2; i++) {
//            TakeawayTypeFragment fragment = new TakeawayTypeFragment();
//            fragment.setListener(new TakeawayTypeFragment.OnItemClickListener() {
//                @Override
//                public void scroll() {
//
//
//                //    mRecyclerView.smoothScrollBy(0, headerView.getMeasuredHeight());
//                }
//
//                @Override
//                public void onClick(int position) {
//
//                    requestRvData(1, position, normalState, isLoadMore);
//                }
//            });
//            mList.add(fragment);
//
////        }
//        TakeawayMainPagerAdapter adapter = new TakeawayMainPagerAdapter(getSupportFragmentManager(), mList);
//        mViewpager.addOnPageChangeListener(new TakeawayPageChangeListener(mList, mDotList));
//        mViewpager.setAdapter(adapter);

    }

    private void onRvRefresh(int waicate, int xingPing) {

        page = 1;
        requestRvData(page, waicate, xingPing, isLoadMore);
        mRecyclerView.refreshComplete();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }

    }
}
