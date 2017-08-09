package com.sugood.app.application;


import android.app.Application;
import android.content.SharedPreferences;
import android.renderscript.AllocationAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.baidu.location.Poi;
//import com.baidu.mapapi.SDKInitializer;
import com.amap.api.location.AMapLocationClient;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.litesuits.orm.LiteOrm;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mob.MobSDK;
import com.sugood.app.entity.Ctiy;
import com.sugood.app.entity.ShopCarProduct;
import com.sugood.app.entity.User;
import com.sugood.app.entity.UserIDAndPW;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.util.MD5Util;
import com.sugood.app.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.sharesdk.framework.ShareSDK;
import cz.msebera.android.httpclient.Header;
import io.realm.Realm;



public class SugoodApplication extends Application {
    private static final String TAG = SugoodApplication.class.getSimpleName();
    //    public static final String bd_key = "M5GEU66ODRnKbLMphydwkzOEzqiIO518";
    private static SugoodApplication mInstance = null;
    //public static LocationClient mLocationClient = null;
    //声明AMapLocationClient类对象
    public static AMapLocationClient locationClient = null;

    public static String lat="";
    public static String lng="";
    public List<ShopCarProduct> shopCarProductList = new ArrayList<>();

    public static LiteOrm liteOrm;
    private UserIDAndPW uip;
    public static boolean isLogin = false;
    public static User user = null;
    private List<Ctiy> ctiyList;

    private static Boolean isExit = false;
    private static Boolean hasTask = false;
    private Timer tExit = new Timer();
    private TimerTask task = new TimerTask() {

        @Override
        public void run() {
            isExit = false;
            hasTask = true;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        mInstance = this;

        MobSDK.init(this,"1f82ab2d98501","4d7a822640543ccfaade40b4d40c7a4e");
        Fresco.initialize(getApplicationContext());
        // 初始化百度地图
//        SDKInitializer.initialize(getApplicationContext());
//        // 定位
//        initLocationSetting();
        // 初始化高德地图

        if (liteOrm == null) {
            liteOrm = LiteOrm.newSingleInstance(this, "data.db");
        }
        liteOrm.setDebugged(true); // open the log
        loginUser();

    }

    public void setLocationClient(AMapLocationClient locationClient) {
        this.locationClient = locationClient;
    }

    public AMapLocationClient getLocationClient() {
        return locationClient;
    }

    public List<ShopCarProduct> getShopCarProductList() {
        return shopCarProductList;
    }

    public void setShopCarProductList(List<ShopCarProduct> shopCarProductList) {
        this.shopCarProductList = shopCarProductList;
    }

    private void loginUser() {
        RequestParams params = new RequestParams();
        SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        String URl;
        System.out.println("111111111:"+sharedPreferences.getBoolean("istdlogin",false));

        if(sharedPreferences.getBoolean("istdlogin",false)){
            params.put("openid", sharedPreferences.getString("openid", ""));
            params.put("nickname",sharedPreferences.getString("nickname", ""));
            URl=Constant.SUGOOGTHRLOGIN;
        }else{
            params.put("account", sharedPreferences.getString("name", ""));
            params.put("password", MD5Util.getMD5(sharedPreferences.getString("pwd", "")));
            URl=Constant.SUGOOGLOGIN;
        }



        HttpUtil.post(URl, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "onSuccess:＋ login " + response.toString());
                try {
                    if (response.getBoolean("success")) {
                        isLogin = true;
                       // User user1 = JsonUtil.toObject(response.getString("content"), User.class);
                        User user1 = JsonUtil.toObject(response.getString("List"), User.class);
                        user = user1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, "onFailure: " + responseString.toString());
            }
        });


    }


    //    private LocationClient mLocationClient = null;
//    private void initLocationSetting() {
//
//        try {
//            mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
//            mLocationClient.start();
//
//            LocationClientOption option = new LocationClientOption();
//            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//            option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//            int span = 1000;
//            option.setIsNeedAddress(true);
//            option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//            option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
//            option.setOpenGps(true);//可选，默认false,设置是否使用gps
//            option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
//            option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//            option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//            option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//            option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
//            option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
//            mLocationClient.setLocOption(option);
//
//            if (mLocationClient != null && mLocationClient.isStarted()) {
//                System.out.println("--MainActivity-->>" + mLocationClient.requestLocation());
//            } else {
//                System.out.println("MainActivity === locClient is null or not started");
//            }
//
//            try {
//                mLocationClient.registerLocationListener(new MyLocationListener());    //注册监听函数
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


//    public class MyLocationListener implements BDLocationListener {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            try {
//                //Receive Location
//                StringBuffer sb = new StringBuffer(256);
//                sb.append("time : ");
//                sb.append(location.getTime());
//                sb.append("\nerror code : ");
//                sb.append(location.getLocType());
//                sb.append("\nlatitude : ");
//                sb.append(location.getLatitude());
//                sb.append("\nlontitude : ");
//                sb.append(location.getLongitude());
//                sb.append("\nradius : ");
//                locationCity = location.getCity();
//
//                locationAddress = location.getAddrStr();
//                //System.out.println("11111"+ locationAddress);
//                if (locationAddress != null) {
//                    locationAddress = locationAddress.replace("[^\u4E00-\u9FA5]", "");
//                     Log.e(TAG, "onReceiveLocation: " + locationAddress);
//                }
//                if (locationCity != null) {
//                    locationCity = locationCity.replaceAll("[^\u4E00-\u9FA5]", "");
//                     System.out.println("11111=============有没有city>>>" + locationCity + "<<<");
////                    getLocalCity(locationCity);
//                }
//                sb.append(location.getRadius());
//                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//                    sb.append("\nspeed : ");
//                    sb.append(location.getSpeed());// 单位：公里每小时
//                    sb.append("\nsatellite : ");
//                    sb.append(location.getSatelliteNumber());
//                    sb.append("\nheight : ");
//                    sb.append(location.getAltitude());// 单位：米
//                    sb.append("\ndirection : ");
//                    sb.append(location.getDirection());// 单位度
//                    sb.append("\naddr : ");
//                    sb.append(location.getAddrStr());
//                    sb.append("\ndescribe : ");
//                    sb.append("gps定位成功");
//
//                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//                    sb.append("\naddr : ");
//                    sb.append(location.getAddrStr());
//                    //运营商信息
//                    sb.append("\noperationers : ");
//                    sb.append(location.getOperators());
//                    sb.append("\ndescribe : ");
//                    sb.append("网络定位成功");
//                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//                    sb.append("\ndescribe : ");
//                    sb.append("离线定位成功，离线定位结果也是有效的");
//                } else if (location.getLocType() == BDLocation.TypeServerError) {
//                    sb.append("\ndescribe : ");
//                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
//                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
//                    sb.append("\ndescribe : ");
//                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
//                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
//                    sb.append("\ndescribe : ");
//                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
//                }
//                sb.append("\nlocationdescribe : ");
//                sb.append(location.getLocationDescribe());// 位置语义化信息
//                List<Poi> list = location.getPoiList();// POI数据
//                if (list != null) {
//                    sb.append("\npoilist size = : ");
//                    sb.append(list.size());
//                    for (Poi p : list) {
//                        sb.append("\npoi= : ");
//                        sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
//                    }
//                }
//               //System.out.println("-----定位信息-->>>" +sb.toString());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onConnectHotSpotMessage(String s, int i) {
//
//        }
//
//    }



    public static SugoodApplication getInstance() {
        return mInstance;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                if (!hasTask) {
                    tExit.schedule(task, 2000);
                }
            } else {

            }
            System.exit(0);
        }
        return false;
    }

    public List<Ctiy> getCtiyList() {
        return ctiyList;
    }

    public void setCtiyList(List<Ctiy> ctiyList) {
        this.ctiyList = ctiyList;
    }
}
