package com.sugood.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.BankListPageAdapter;
import com.sugood.app.adapter.GuessYouLikeListAdapter;
import com.sugood.app.adapter.HomeGVAdapter;
import com.sugood.app.adapter.ZuliaoFunAdapter;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.AdEntity;
import com.sugood.app.entity.Ctiy;
import com.sugood.app.entity.Food;
import com.sugood.app.entity.GuessYouLikeProductInfo;
import com.sugood.app.global.Constant;
import com.sugood.app.listener.MarketPageChangeListener;
import com.sugood.app.util.GlideUtil;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.util.OtherUtils;
import com.sugood.app.util.ToastUtil;
import com.sugood.app.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationClientOption.AMapLocationProtocol;
import com.amap.api.location.AMapLocationListener;

import androidkun.com.versionupdatelibrary.entity.VersionUpdateConfig;
import cz.msebera.android.httpclient.Header;

/**
 * 首页
 *
 * @author wtb
 */
public class HomeActivity extends BaseActivity implements TextView.OnEditorActionListener {
    private static final String TAG = HomeActivity.class.getSimpleName();

    private Context mContext;
    private long exitTime = 0;

    private GridView home_top_gv, home_down_gv;
    private HomeGVAdapter upGVAdapter = null;
    private HomeGVAdapter downGVAdapter = null;

    private ArrayList<ImageView> imageList = new ArrayList<ImageView>();
    private TextView tvLocalCity;
    private ViewPager vp_home;
    private EditText home_search;
    private SimpleDraweeView homeAd1;
    private SimpleDraweeView homeAd2;
    private SimpleDraweeView homeAd3;
    private SimpleDraweeView homeAd4;
    private ImageView iv_textmessage;
    private View headerView;

    private ListView lv_like_shop;
    ArrayList<Food> foodList;
    ZuliaoFunAdapter mAdapter;
    List<String> textList;
    List<Integer> list;

    private static final int MESSAGE_INIT_DATA_SUCCESS = 1;

    //高德地图
    //public AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    public static String locationCity = "";
    public static String locationAddress = "";


    // 美团开放API接口
    private final String URI = "";

    // 猜你喜欢的商品信息
    private List<GuessYouLikeProductInfo> guessYouLikeProductInfoList = null;
    // 商品信息适配器
    private GuessYouLikeListAdapter merchAdapter = null;
    // 请求数据地址
    public static final String guess_you_like_url = Constant.COM_URL + "";
    private TextView tv_merchs_info;
    private ViewPager vpClassic;

    private LayoutInflater inflater;
    private int headerHeight; // 头高度
    private int lastHeaderPadding; // 最后一次调用Move Header的Padding
    private boolean isBack; // 从Release 转到 pull
    private int headerState = DONE; // 头部状态
    static final private int RELEASE_To_REFRESH = 0; // 释放刷新:一直下拉屏幕时显示
    static final private int PULL_To_REFRESH = 1; // 正在刷新：放开屏幕后显示
    static final private int REFRESHING = 2; // 正在刷新
    static final private int DONE = 3;

    private int currentPage = 1;
    List<AdEntity> EntityList;
    ScheduledExecutorService mScheduledExecutorService;
    MarketPageChangeListener mChangeListener;
    //设置当前 第几个图片 被选中
    private int autoCurrIndex = 0;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (msg.arg1 != 0) {
                        vp_home.setCurrentItem(msg.arg1);
                    } else {
                        //false 当从末页调到首页是，不显示翻页动画效果，
                        vp_home.setCurrentItem(msg.arg1, false);
                    }
                    break;
            }
//            vp_home.setCurrentItem(mChangeListener.mCurrentItem);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = HomeActivity.this;
        initLocation();

        startLocation();
        initCategory();// 初
        initView();// 初始化各控件
        showLoading("定位中");
        checkupdate();//检查更新
        //initNetData();//上传经纬度
    }

    //上传经纬度
    private void initNetData(String lat
    ,String lng,String cityName) {

        RequestParams params = new RequestParams();
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("cityName",cityName);

//        params.put("lat", SugoodApplication.mLocationClient.getLastKnownLocation().getLatitude()+"");
//        params.put("lng", SugoodApplication.mLocationClient.getLastKnownLocation().getLongitude()+"");
//        params.put("cityName", SugoodApplication.mLocationClient.getLastKnownLocation().getCity());
//        Log.e(TAG, "initNetData: "+SugoodApplication.mLocationClient.getLastKnownLocation().getLatitude() );
//        Log.e(TAG, "initNetData: "+SugoodApplication.mLocationClient.getLastKnownLocation().getLongitude() );
//        Log.e(TAG, "initNetData: " + SugoodApplication.getLocalCity());
//        params.put("lat", "20.912420741522");
//        params.put("lng", "110.08775659179");
//        params.put("cityName", "湛江市");
        HttpUtil.post(Constant.UPLOADLATLONG, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "onSuccess: " + response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                List<Ctiy> list = JsonUtil.toList(response.toString(), Ctiy.class);
                SugoodApplication.getInstance().setCtiyList(list);
                initData();
                Log.e(TAG, "onSuccess: " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, "onFailure: " + responseString);
            }
        });


        /**
         *  请求广告图片
         */
        RequestParams photoParams = new RequestParams();
        photoParams.put("siteId", "94");
        HttpUtil.post(Constant.GOODFOODAD, photoParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TAG_AD", "onSuccess: " + response);
                EntityList = JsonUtil.toList(response.toString(), AdEntity.class);
                if (EntityList.size() > 0) {
                    homeAd1.setImageURI(Constant.PHOTOBASEURL + EntityList.get(0).getPhoto());
                    homeAd2.setImageURI(Constant.PHOTOBASEURL + EntityList.get(1).getPhoto());
                    homeAd3.setImageURI(Constant.PHOTOBASEURL + EntityList.get(2).getPhoto());
                    homeAd4.setImageURI(Constant.PHOTOBASEURL + EntityList.get(3).getPhoto());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("Failure", "onFailure: " + statusCode + responseString);
            }
        });

        /**
         *  请求广告图片
         */
        RequestParams adParams = new RequestParams();
        adParams.put("siteId", "91");
        HttpUtil.post(Constant.GOODFOODAD, adParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TAG_AD", "onSuccess: " + response);
                List<AdEntity> adEntityList = JsonUtil.toList(response.toString(), AdEntity.class);
                initViewpager(adEntityList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("Failure", "onFailure: " + statusCode + responseString);
            }
        });


    }

    private void initData() {

       // showLoading("加载中");

        RequestParams params = new RequestParams();
        params.put("page", "1");
        params.put("pageSize", "9999");
        params.put("parentId", "69");
        HttpUtil.post(Constant.GOODFOOD, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, "onFailure: " + responseString);
                closeLoading();
                ToastUtil.setToast(HomeActivity.this, "获取位置失败，请重新打开");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "onSuccess: " + response.toString());
                closeLoading();
                currentPage = currentPage + 1;
                foodList = (ArrayList<Food>) JsonUtil.toList(response.toString(), Food.class);
                mAdapter = new ZuliaoFunAdapter(HomeActivity.this);
                mAdapter.setData(foodList);
                lv_like_shop.setAdapter(mAdapter);
//                Utility.setListViewHeightBasedOnChildren(lv_like_shop);
                mAdapter.notifyDataSetChanged();
            }
        });


    }


    private ScrollView sc;
    private LinearLayout globleLayout;
    private LinearLayout header;
    private Animation anim;
    private ImageView iv_anim_first;
    private ImageView iv_header_fresh_anim;
    private TextView tv_text;
    private AnimationDrawable ad;

    /**
     * 初始化各控件
     */

    public void initView() {

        headerView = LayoutInflater.from(mContext).inflate(R.layout.home_header, null);
//        initViewpager();
        vpClassic = (ViewPager) headerView.findViewById(R.id.vp_classic);
        initClassicPage();
        tvLocalCity = (TextView) findViewById(R.id.tv_home_city);
//        home_top_gv = (GridView) findViewById(R.id.home_top_gv);
        home_down_gv = (GridView) findViewById(R.id.home_down_gv);
        home_search = (EditText) findViewById(R.id.home_search);
        home_search.setOnEditorActionListener(this);
        // 初始化中间商品信息控件
//        tv_merchs_info = (TextView) findViewById(R.id.tv_load_info);
        lv_like_shop = (ListView) findViewById(R.id.lv_like_shop);
        lv_like_shop.addHeaderView(headerView);

        homeAd1 = (SimpleDraweeView) headerView.findViewById(R.id.homeAd1);
        homeAd2 = (SimpleDraweeView) headerView.findViewById(R.id.homeAd2);
        homeAd3 = (SimpleDraweeView) headerView.findViewById(R.id.homeAd3);
        homeAd4 = (SimpleDraweeView) headerView.findViewById(R.id.homeAd4);
        homeAd1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startInetnt(0);
            }
        });
        homeAd2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startInetnt(1);
            }
        });
        homeAd3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startInetnt(2);
            }
        });
        homeAd4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startInetnt(3);
            }
        });

        // ScrollView
//        sc = (ScrollView) findViewById(R.id.sv_first_sc);
        // 整体布局
        globleLayout = (LinearLayout) headerView.findViewById(R.id.globleLayout);
        // 布局加载器
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 头部布局
        header = (LinearLayout) inflater.inflate(R.layout.first_header, null);
        tv_text = (TextView) header.findViewById(R.id.tv_first_refresh_text);
        iv_header_fresh_anim = (ImageView) header.findViewById(R.id.iv_header_anim);
        iv_header_fresh_anim.setBackgroundResource(R.drawable.frame);
        ad = (AnimationDrawable) iv_header_fresh_anim.getBackground();
        // 头部动画
        anim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        // 动画应用到的控件
        iv_anim_first = (ImageView) header.findViewById(R.id.iv_first_refresh);
        // 计算头部高度
        measureView(header);
        headerHeight = header.getMeasuredHeight();
        lastHeaderPadding = (-1 * headerHeight);
        header.setPadding(10, lastHeaderPadding, 0, 20);
        header.invalidate();
        // 添加头部布局
        globleLayout.addView(header, 0);
        anim.setFillAfter(true);// 动画结束后保持动画
        // 为ScrollView绑定监听

        lv_like_shop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE || foodList.size() - 1 == lv_like_shop.getLastVisiblePosition()) {
//
//                    ToastUtil.setToast(HomeActivity.this, "uuuadsuasuda");
//
//                    loadMore();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        lv_like_shop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("shopId", foodList.get(position - 1).getShopId());
                intent.setClass(HomeActivity.this, ShopDetailActivity.class);
                startActivity(intent);
            }
        });

        iv_textmessage = (ImageView) findViewById(R.id.iv_textmessage);
        iv_textmessage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SugoodApplication.isLogin) {
                    Intent intent = new Intent();
                    intent.setClass(HomeActivity.this, NotificationActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtil.setToast(HomeActivity.this, "请先登录");
                }
            }
        });
    }

    private void startInetnt(int position) {
        Intent intent = new Intent();
        if (EntityList.get(position).getType().equals("1")) {
            intent.putExtra("tuanId", EntityList.get(position).getDiyid());
            intent.putExtra("shopId", "");
            intent.setClass(HomeActivity.this, TuanGouActivity.class);
            startActivity(intent);
        } else if (EntityList.get(position).getType().equals("2")) {
            intent.putExtra("shopId", EntityList.get(position).getDiyid());
            intent.setClass(HomeActivity.this, ShopDetailActivity.class);
            startActivity(intent);
        } else if (EntityList.get(position).getType().equals("3")) {
            intent.putExtra("shopId", EntityList.get(position).getDiyid());
            intent.setClass(HomeActivity.this, TakeawayShopDetailActivity.class);
            startActivity(intent);
        } else if (EntityList.get(position).getType().equals("4")) {
            intent.setClass(HomeActivity.this, TakeawayMarketDetailActivity.class);
            intent.putExtra("goodsId", EntityList.get(position).getDiyid());
            startActivity(intent);
        } else if (EntityList.get(position).getType().equals("5")) {
            intent.setClass(HomeActivity.this, TakeawayMarketShopDetailActivity.class);
            intent.putExtra("cateId", EntityList.get(position).getDiyid());
            startActivity(intent);
//                        intent.putExtra("asa", (Serializable) mShopMainList);
        } else {
            ToastUtil.setToast(HomeActivity.this, "正在建设中。。。");
        }

    }

    private void initClassicPage() {

        List<View> viewList = new ArrayList<>();
        BankListPageAdapter adapter = new BankListPageAdapter();
        for (int i = 0; i < 2; i++) {
            home_top_gv = new GridView(this);
            viewList.add(home_top_gv);
            upGVAdapter = new HomeGVAdapter(mContext);
            home_top_gv.setGravity(Gravity.CENTER);
            home_top_gv.setNumColumns(4);
            home_top_gv.setAdapter(upGVAdapter);
            List<String> bankList = new ArrayList<>();
            List<Integer> imgList = new ArrayList<>();

            for (int n = i * 8; n <= (i + 1) * 8 - 1; n++) {
                bankList.add(textList.get(n));
                imgList.add(list.get(n));
            }
            upGVAdapter.setDataList(imgList, bankList);
            upGVAdapter.notifyDataSetChanged();
            adapter.setViewSize((ArrayList<View>) viewList);
            vpClassic.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        }


    }

    private void loadMore() {

        RequestParams params = new RequestParams();
        params.put("page", currentPage);
        params.put("pageSize", "40");
        params.put("parentId", "69");
        HttpUtil.post(Constant.GOODFOOD, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, "onFailure: " + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "onSuccess: " + response.toString());
                currentPage = currentPage + 1;
                List<Food> foodList1 = JsonUtil.toList(response.toString(), Food.class);
                foodList.addAll(foodList1);
                mAdapter.setData(foodList);
//                Utility.setListViewHeightBasedOnChildren(lv_like_shop);
                mAdapter.notifyDataSetChanged();
            }
        });


    }

    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        SugoodApplication.locationClient = new AMapLocationClient(HomeActivity.this);
        locationOption = getDefaultOption();
        //设置定位参数
        SugoodApplication.locationClient.setLocationOption(locationOption);
        // 设置定位监听
        SugoodApplication.locationClient.setLocationListener(locationListener);

    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    public static String getLocalCity() {
        if (locationCity != null && !"".equals(locationCity)) {
            return locationCity;
        }
        return null;
    }


    private void checkupdate(){

        RequestParams params = new RequestParams();
        int version = OtherUtils.getAppVersion(SugoodApplication.getInstance());
        params.put("version", version+"");
        Log.e(TAG, "version112: " + version);
        HttpUtil.post(Constant.CHECKUPDATE, params, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "response112: " + response.toString());
                try {
                    if (response.getBoolean("session")) {
                        showUpdateDialog(response.getString("version"));
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "onSuccess: " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, "onFailure: " + responseString);
            }
        });
    }

    private void showUpdateDialog(final String url){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */

        final AlertDialog.Builder updateDialog =
                new AlertDialog.Builder(this);

        updateDialog.setTitle("更新提示");
        updateDialog.setMessage("有新版本更新");
        updateDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        VersionUpdateConfig.getInstance()//获取配置实例
                                .setContext(HomeActivity.this)//设置上下文
                                .setDownLoadURL(url)//设置文件下载链接
                                .setNotificationIconRes(R.drawable.logo)//设置通知图标
                                .setNotificationSmallIconRes(R.drawable.yes_icon)//设置通知小图标
                                .setNotificationTitle("速购得更新")//设置通知标题
                                .startDownLoad();//开始下载
                    }
                });
        updateDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        updateDialog.show();
    }




    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: " + location.getLocationType() + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                    sb.append("提供者    : " + location.getProvider() + "\n");

                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : " + location.getSatellites() + "\n");
                    sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append("省            : " + location.getProvince() + "\n");
                    sb.append("市            : " + location.getCity() + "\n");
                    sb.append("城市编码 : " + location.getCityCode() + "\n");
                    sb.append("区            : " + location.getDistrict() + "\n");
                    sb.append("区域 码   : " + location.getAdCode() + "\n");
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    //定位完成的时间
                    //sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                    sb.append("定位时间: " + location.getTime() + "\n");
                    locationCity = location.getCity();

                    locationAddress = location.getAddress();
                    if (locationAddress != null) {
                        locationAddress = locationAddress.replace("[^\u4E00-\u9FA5]", "");
                        Log.e(TAG, "onReceiveLocation: " + locationAddress);
                    }
                    if (locationCity != null) {
                        locationCity = locationCity.replaceAll("[^\u4E00-\u9FA5]", "");

                    }
                    initNetData(location.getLatitude()+"",location.getLongitude()+"",locationCity);
                    tvLocalCity.setText(locationCity);
                } else {
                    //定位失败
                    closeLoading();
                    ToastUtil.setToast(HomeActivity.this, "获取位置失败，请重新打开");
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                    tvLocalCity.setText("定位失败");
                }
                //定位之后的回调时间
                sb.append("回调时间: " + System.currentTimeMillis() + "\n");
//                sb.append("回调时间: " + Utils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

                //解析定位结果，
                String result = sb.toString();
                System.out.println("-----定位信息-->>>" + sb.toString());
            } else {
                System.out.println("-----定位失败-->>>");
            }
        }
    };

    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    public void startLocation() {
        //根据控件的选择，重新设置定位参数
        //resetOption();
        // 设置定位参数
        SugoodApplication.locationClient.setLocationOption(locationOption);
        // 启动定位
        SugoodApplication.locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void stopLocation() {
        // 停止定位
        SugoodApplication.locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void destroyLocation() {
        if (null != SugoodApplication.locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            SugoodApplication.locationClient.onDestroy();
            SugoodApplication.locationClient = null;
            locationOption = null;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }

//    private void initLocalCity() {
//        if (TextUtils.isEmpty(getLocalCity())) {
//            tvLocalCity.setText("定位失败");
//        } else {
//            tvLocalCity.setText(getLocalCity());
//        }
//
//    }

    private void initViewpager(final List<AdEntity> adEntityList) {
        for (int i = 0; i < adEntityList.size(); i++) {
            ImageView iv = new ImageView(mContext);
            GlideUtil.displayImage(Constant.PHOTOBASEURL + adEntityList.get(i).getPhoto(), iv);
            imageList.add(iv);
        }

        vp_home = (ViewPager) findViewById(R.id.vp_home);
        ImageAdapter pagerAdapter = new ImageAdapter(mContext, imageList, adEntityList);
        vp_home.setAdapter(pagerAdapter);

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
//
//        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (vp_home) {
//                    if (!mChangeListener.nowAction) {
//                        mChangeListener.mCurrentItem++;
//                        mHandler.obtainMessage().sendToTarget();
//                    }
//                }
//            }
//        }, 0, 5, TimeUnit.SECONDS);
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

            imageList.get(position).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    if (adEntityList.get(position).getType().equals("1")) {
                        intent.putExtra("tuanId", adEntityList.get(position).getDiyid());
                        intent.putExtra("shopId", "");
                        intent.setClass(HomeActivity.this, TuanGouActivity.class);
                    } else if (adEntityList.get(position).getType().equals("2")) {
                        intent.putExtra("shopId", adEntityList.get(position).getDiyid());
                        intent.setClass(HomeActivity.this, ShopDetailActivity.class);
                    } else if (adEntityList.get(position).getType().equals("3")) {
                        intent.putExtra("shopId", adEntityList.get(position).getDiyid());
                        intent.setClass(HomeActivity.this, TakeawayShopDetailActivity.class);
                    } else if (adEntityList.get(position).getType().equals("4")) {
                        intent.setClass(HomeActivity.this, TakeawayMarketDetailActivity.class);
                        intent.putExtra("goodsId", adEntityList.get(position).getDiyid());
                    } else if (adEntityList.get(position).getType().equals("5")) {
                        intent.setClass(HomeActivity.this, TakeawayMarketShopDetailActivity.class);
                        intent.putExtra("cateId", adEntityList.get(position).getDiyid());
//                        intent.putExtra("asa", (Serializable) mShopMainList);
                    }
                    startActivity(intent);


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

    /**
     * 初始化分类
     */
    public void initCategory() {

        //
        textList = new ArrayList<String>();
        textList.add("美食");
        textList.add("外卖");
        textList.add("商城");
        textList.add("休闲");
        textList.add("市场");
        textList.add("丽人");
        textList.add("机票");
        textList.add("黄页");

        textList.add("电影");
        textList.add("积分");
        textList.add("汽车票");
        textList.add("医疗");
        textList.add("旅游");
        textList.add("酒店");
        textList.add("超市");
        textList.add("团购");

        list = new ArrayList<>();
        list.add(R.drawable.meishi);
        list.add(R.drawable.waimai);
        list.add(R.drawable.shangcheng);
        list.add(R.drawable.xiuxianyule);
        list.add(R.drawable.shichang);
        list.add(R.drawable.liren);
        list.add(R.drawable.jipiao);
        list.add(R.drawable.huangye);

        list.add(R.drawable.dianying);
        list.add(R.drawable.jifen);
        list.add(R.drawable.qichepiao);
        list.add(R.drawable.yiliao);
        list.add(R.drawable.lvyou);
        list.add(R.drawable.jiudian);
        list.add(R.drawable.chaoshi);
        list.add(R.drawable.tuangou);


//        // 假数据
//        List<String> textList1 = new ArrayList<String>();
//        textList1.add("二手车");
//        textList1.add("二手房");
//        textList1.add("人才招聘");
//        textList1.add("本地服务");
//        textList1.add("好大夫");
//        textList1.add("旅游");
//        textList1.add("汽车订票");
//        textList1.add("违章查询");
//        textList1.add("房贷计算");
//        textList1.add("一元抢购");
//        downGVAdapter = new HomeGVAdapter(mContext, textList1);
//        home_down_gv.setAdapter(downGVAdapter);

    }


    /**
     * 拿到头部高度,onCreate里面得不到
     */
    private void measureView(View childView) {
        LayoutParams p = childView.getLayoutParams();
        if (p == null) {
            p = new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int height = p.height;
        int childHeightSpec;
        if (height > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(height,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        childView.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 通过状态来改变头部视图
     */
    private void changeHeaderViewByState() {
        switch (headerState) {
            case PULL_To_REFRESH:
                // 是由RELEASE_To_REFRESH状态转变来的
                if (isBack) { // 向上送
                    isBack = false;
                    // 开启动画
                    iv_anim_first.startAnimation(anim);
                    ad.start();
                    tv_text.setText("下拉刷新");
                }
                tv_text.setText("下拉刷新");
                break;
            case RELEASE_To_REFRESH: // 向下拖：这里只有右边的进度动画
                iv_anim_first.setVisibility(View.VISIBLE);
                iv_header_fresh_anim.setVisibility(View.VISIBLE);
                tv_text.setVisibility(View.VISIBLE);
                iv_anim_first.startAnimation(anim); // 右边的进度动画
                tv_text.setText("松手刷新");
                break;
            case REFRESHING:
                lastHeaderPadding = 0;
                header.setPadding(10, lastHeaderPadding, 0, 20);
                header.invalidate();
                iv_header_fresh_anim.setVisibility(View.VISIBLE);
                iv_anim_first.setVisibility(View.VISIBLE);
                tv_text.setText("载入中...");
                ad.start();
                break;
            case DONE: // 向上送
                lastHeaderPadding = -1 * headerHeight;
                header.setPadding(10, lastHeaderPadding, 0, 20);
                header.invalidate();
                iv_header_fresh_anim.setVisibility(View.GONE);
                tv_text.setText("下拉刷新");
                break;
            default:
                break;
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

    //搜索
    private void search() {


        String searchContent = home_search.getText().toString();
        if (!TextUtils.isEmpty(searchContent)) {
            showLoading("");
            RequestParams params = new RequestParams();
            params.put("shopName", searchContent);
            params.put("parentId", "69");
            params.put("page", "1");
            params.put("pageSize", "9999");
            HttpUtil.post(Constant.SUGOOODGOODFOOODSEARCH, params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                    Log.e("TAG", "onSuccess: " + response.toString());
                    foodList.clear();
                    foodList = (ArrayList<Food>) JsonUtil.toList(response.toString(), Food.class);
                    mAdapter.setData(foodList);
//                    Utility.setListViewHeightBasedOnChildren(lv_like_shop);
                    mAdapter.notifyDataSetChanged();
                    lv_like_shop.setSelection(1);
                    closeLoading();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Log.e("TAG", "onFailure: " + responseString);
                    closeLoading();
                }
            });


        }


    }

    /**
     * 初始化监听
     */
    private void initListener() {

    }

    /**
     * 监听
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            Log.e(TAG, "onEditorAction: " + "sadas");
            // 当按了搜索之后关闭软键盘
            ((InputMethodManager) home_search.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    HomeActivity.this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            search();
            return true;
        }
        return false;
    }


}
