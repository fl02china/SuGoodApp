package com.sugood.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.CClassicAdapter;
import com.sugood.app.adapter.ClaassicAdapter;
import com.sugood.app.adapter.CommonAdapter;
import com.sugood.app.adapter.SXClassicAdapter;
import com.sugood.app.adapter.ViewHolder;
import com.sugood.app.adapter.ZuliaoFunAdapter;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.AdEntity;
import com.sugood.app.entity.Classic;
import com.sugood.app.entity.Food;
import com.sugood.app.entity.MeiShiLeiXing;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


/**
 * Created by wilk on 17/2/20 11:20
 * ganweib@gmail.com
 * describe:
 */
public class GoodFoodActivity extends BaseActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private Button btn_all;
    private Button btn_near;
    private Button btn_choose;
    private Button btn_back;
    private EditText edtSearch;
    private Button btn_location;
    private TextView tv_address;
    private PopupWindow popAll;
    private PopupWindow popNear;
    private PopupWindow popChoose;
    SimpleDraweeView simpleDraweeView;
    private ListView lv_food;
    private ZuliaoFunAdapter mAdapter;

    //list header 广告位
    private View viewHeader;

    //商家list
    private ArrayList<Food> foodList;
    private boolean isToast = false;
    private Classic mClassic;
    private List<AdEntity> adEntityList;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodfood);
        initViews();
        initNetDatas();
        setClick();
    }

    public void initViews() {
        type = getIntent().getStringExtra("type");
        viewHeader = LayoutInflater.from(this).inflate(R.layout.good_food_header, null);
        tv_address = (TextView) viewHeader.findViewById(R.id.tv_address);
        tv_address.setText("未知位置");
//        try {
//            if (SugoodApplication.mLocationClient != null) {
//                tv_address.setText("地址:" + SugoodApplication.mLocationClient.getLastKnownLocation().getAddress().address.
//                        replace(SugoodApplication.mLocationClient.getLastKnownLocation().getAddress().country
//                                + SugoodApplication.mLocationClient.getLastKnownLocation().getAddress().province, ""));
//            } else {
//                tv_address.setText("未知位置");
//            }
//        } catch (NullPointerException e) {
//            tv_address.setText("未知位置");
//        }

        btn_back = (Button) findViewById(R.id.btn_back);
        edtSearch = (EditText) findViewById(R.id.tv_box_search);
        btn_location = (Button) findViewById(R.id.btn_location);
        simpleDraweeView = (SimpleDraweeView) viewHeader.findViewById(R.id.sdv_bawang);
        btn_near = (Button) findViewById(R.id.btn_near);
        btn_all = (Button) findViewById(R.id.btn_all);
        btn_choose = (Button) findViewById(R.id.btn_choose);
        lv_food = (ListView) findViewById(R.id.list_food);
        edtSearch.setOnEditorActionListener(this);
        lv_food.addHeaderView(viewHeader, null, false);
        foodList = new ArrayList<>();
        simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (adEntityList.get(0).getType().equals("1")) {
                    intent.putExtra("tuanId", adEntityList.get(0).getDiyid());
                    intent.putExtra("shopId", "");
                    intent.setClass(GoodFoodActivity.this, TuanGouActivity.class);
                    startActivity(intent);
                } else if (adEntityList.get(0).getType().equals("2")) {
                    intent.putExtra("shopId", adEntityList.get(0).getDiyid());
                    intent.setClass(GoodFoodActivity.this, ShopDetailActivity.class);
                    startActivity(intent);
                } else if (adEntityList.get(0).getType().equals("3")) {
                    intent.putExtra("shopId", adEntityList.get(0).getDiyid());
                    intent.setClass(GoodFoodActivity.this, TakeawayShopDetailActivity.class);
                    startActivity(intent);
                } else if (adEntityList.get(0).getType().equals("4")) {
                    intent.setClass(GoodFoodActivity.this, TakeawayMarketDetailActivity.class);
                    intent.putExtra("goodsId", adEntityList.get(0).getDiyid());
                    startActivity(intent);
                } else if (adEntityList.get(0).getType().equals("5")) {
                    intent.setClass(GoodFoodActivity.this, TakeawayMarketShopDetailActivity.class);
                    intent.putExtra("cateId", adEntityList.get(0).getDiyid());
                    startActivity(intent);
//                        intent.putExtra("asa", (Serializable) mShopMainList);
                } else {
                    ToastUtil.setToast(GoodFoodActivity.this, "正在建设中。。。");
                }

            }
        });
    }

    public void initNetDatas() {

        showLoading("");
        /**
         *  请求广告图片
         */
        RequestParams photoParams = new RequestParams();
        if ("ms".equals(type)) {
            photoParams.put("siteId", "80");
        } else if ("lr".equals(type)) {
            photoParams.put("siteId", "102");
        } else if ("xx".equals(type)) {
            photoParams.put("siteId", "101");
        }else if ("ly".equals(type)) {
            photoParams.put("siteId", "104");
        }

        HttpUtil.post(Constant.GOODFOODAD, photoParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TAG_AD", "onSuccess: " + response);
                adEntityList = JsonUtil.toList(response.toString(), AdEntity.class);
                if (adEntityList.size() > 0) {
                    simpleDraweeView.setImageURI(Constant.PHOTOBASEURL + adEntityList.get(0).getPhoto());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("Failure", "onFailure: " + statusCode + responseString);
            }
        });

        String areaID = null;
        String cityId = null;
//        Log.e("lass", "initNetDatas: " +
//        Log.e("lass", "initNetDatas: " + SugoodApplication.getInstance().getCtiyList().toString());
        try {
            for (int i = 0; i < SugoodApplication.getInstance().getCtiyList().size(); i++) {
                for (int a = 0; a < SugoodApplication.getInstance().getCtiyList().get(i).getArea().size(); a++) {
                    if ("雷州市"
                            .equals(SugoodApplication.getInstance().getCtiyList().get(i).getArea().get(a).getName())) {
                        areaID = SugoodApplication.getInstance().getCtiyList().get(i).getArea().get(a).getAreaId();
                        cityId = SugoodApplication.getInstance().getCtiyList().get(i).getCity().getCityId();
                    }
                }
            }
        } catch (NullPointerException e) {

            ToastUtil.setToast(GoodFoodActivity.this, "百度定位初始化失败,请重新打开APP");
        }
        Log.e("class", "initNetDatas: " + cityId + areaID);
        RequestParams classidParams = new RequestParams();
        classidParams.put("areaId", areaID);
        classidParams.put("cityId", cityId);
        if ("ms".equals(type)) {
            classidParams.put("parentId", "69");
        } else if ("lr".equals(type)) {
            classidParams.put("parentId", "10");
        } else if ("xx".equals(type)) {
            classidParams.put("parentId", "249");
        }else if ("ly".equals(type)) {
            classidParams.put("parentId", "9");
        }


        HttpUtil.post(Constant.GOODFOODCLASSIC, classidParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("Class", "onSuccess: " + response.toString());
                Log.e("lass", "onSuccess: " + JsonUtil.toList(response.toString(), Classic.class).toString());
                mClassic = JsonUtil.toList(response.toString(), Classic.class).get(0);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("Class", "onFailure: " + responseString);
            }
        });

        RequestParams params = new RequestParams();
        params.put("page", "1");
        params.put("pageSize", "9999");
        if ("ms".equals(type)) {
            params.put("parentId", "69");
        } else if ("lr".equals(type)) {
            params.put("parentId", "10");
        } else if ("xx".equals(type)) {
            params.put("parentId", "6");
        }else if ("ly".equals(type)) {
            params.put("parentId", "9");
        }
        HttpUtil.post(Constant.GOODFOOD, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("ssd", "onSuccess: " + response.toString());
                foodList = (ArrayList<Food>) JsonUtil.toList(response.toString(), Food.class);
                Log.e("1002", "onSuccess: " + foodList.size() + "----" + foodList.toString());
                mAdapter = new ZuliaoFunAdapter(GoodFoodActivity.this);
                mAdapter.setData(foodList);
                lv_food.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                closeLoading();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("x", "onFailure: " + responseString.toString());
            }
        });


    }


    public void setClick() {
        btn_all.setOnClickListener(this);
        btn_choose.setOnClickListener(this);
        btn_near.setOnClickListener(this);
//        btn_search.setOnClickListener(this);
        btn_location.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        lv_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("shopId", foodList.get(position - 1).getShopId());
                intent.setClass(GoodFoodActivity.this, ShopDetailActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_all:
                if (mClassic.getMeiShiLeiXing().size() != 0) {
                    initAllPop();
                    popAll.showAsDropDown(v);
                } else {
                    ToastUtil.setToast(GoodFoodActivity.this, "暂无改地址数据");
                }
                break;
            case R.id.btn_near:
                if (mClassic.getShangQuan().size() != 0) {
                    initNearPop();
                    popAll.showAsDropDown(v);
                } else {
                    ToastUtil.setToast(GoodFoodActivity.this, "暂无改地址数据");
                }
                break;
            case R.id.btn_choose:
                initChoosePop();
                popAll.showAsDropDown(v);
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_location:
                isToast = false;
                showLoading("获取位置");
                //百度地图
//                SugoodApplication.mLocationClient.start();
//                SugoodApplication.mLocationClient.registerLocationListener(new BDLocationListener() {
//                    @Override
//                    public void onReceiveLocation(BDLocation bdLocation) {
//                        closeLoading();
//                        Log.e("LOCAtion", "onReceiveLocation: " + bdLocation.getAddrStr());
//                        if (!isToast) {
//                            isToast = true;
//                            ToastUtil.setToast(GoodFoodActivity.this, bdLocation.getAddrStr().replace(bdLocation.getCountry(), ""));
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
                break;
            default:
                break;
        }

    }

    //搜索
    private void search() {


        String searchContent = edtSearch.getText().toString();
        if (!TextUtils.isEmpty(searchContent)) {
            showLoading("");
            RequestParams params = new RequestParams();
            params.put("shopName", searchContent);
            if ("ms".equals(type)) {
                params.put("parentId", "69");
            } else if ("lr".equals(type)) {
                params.put("parentId", "36");
            } else if ("xx".equals(type)) {
                params.put("parentId", "34");
            }
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
                    mAdapter.notifyDataSetChanged();
                    closeLoading();


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Log.e("TAG", "onFailure: " + responseString);
                }
            });


        }


    }

    // 筛选
    private void initChoosePop() {


        final String[] sts = new String[]{"全部", "50元以下", "50～100元", "100～200元", "200元以上"};

        final String[] min = new String[]{"", "0", "50", "100", "200"};
        final String[] max = new String[]{"", "50", "100", "200", "99999"};

        View pop_choose = getLayoutInflater().inflate(R.layout.pop_all, null, false);
        popAll = new PopupWindow(pop_choose, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ListView allList = (ListView) pop_choose.findViewById(R.id.lv_all);
        CClassicAdapter mAdapter = new CClassicAdapter(this);
        mAdapter.setData(sts);
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, allList);
            mView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += mView.getMeasuredHeight();
        }
        allList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        allList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                reSetData(max[position], min[position], "");
                btn_choose.setText(sts[position]);
                popAll.dismiss();
            }
        });

        final int finalTotalHeight = totalHeight;
        pop_choose.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > finalTotalHeight) {
                        popAll.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });

    }

    //附近
    private void initNearPop() {

        if (mClassic.getShangQuan().size() == 0) {
            return;
        }
        View pop_near = getLayoutInflater().inflate(R.layout.pop_all, null, false);
        popAll = new PopupWindow(pop_near, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ListView allList = (ListView) pop_near.findViewById(R.id.lv_all);
        SXClassicAdapter mAdapter = new SXClassicAdapter(this);
        mAdapter.setData(mClassic.getShangQuan());
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, allList);
            mView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += mView.getMeasuredHeight();
        }
        allList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        allList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                reSetData(mClassic.getShangQuan().get(position).getBusinessId(), "sq");
                btn_near.setText(mClassic.getShangQuan().get(position).getBusinessName());
                popAll.dismiss();
            }
        });
        final int finalTotalHeight = totalHeight;
        pop_near.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > finalTotalHeight) {
                        popAll.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    //全部
    private void initAllPop() {

        if (mClassic.getMeiShiLeiXing().size() == 0) {
            return;
        }
        View pop_all = getLayoutInflater().inflate(R.layout.pop_all, null, false);
        popAll = new PopupWindow(pop_all, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ListView allList = (ListView) pop_all.findViewById(R.id.lv_all);
        ClaassicAdapter mAdapter = new ClaassicAdapter(this);
        MeiShiLeiXing meiShiLeiXing = new MeiShiLeiXing();
        meiShiLeiXing.setCateName("全部");
        if (!mClassic.getMeiShiLeiXing().get(0).getCateName().equals("全部")) {
            mClassic.getMeiShiLeiXing().add(0, meiShiLeiXing);
        }
        mAdapter.setData(mClassic.getMeiShiLeiXing());
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, allList);
            mView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += mView.getMeasuredHeight();
        }
        allList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        allList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                reSetData(mClassic.getMeiShiLeiXing().get(position).getCateId(), "fl");
                btn_all.setText(mClassic.getMeiShiLeiXing().get(position).getCateName());
                popAll.dismiss();
            }
        });
        final int finalTotalHeight = totalHeight;
        pop_all.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > finalTotalHeight) {
                        popAll.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
    }


    //搜索
    private void reSetData(String id, String type) {

        showLoading("");
        RequestParams params = new RequestParams();
        if (!TextUtils.isEmpty(id)) {
            if (type.equals("sq")) {
                params.put("businessId", id);
            } else {
                params.put("cateId", id);
            }
        }
        params.put("parentId", "69");
        params.put("page", "1");
        params.put("pageSize", "9999");
        HttpUtil.post(Constant.GOODFOOD, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TAG", "onSuccess: " + response.toString());
                foodList.clear();
                foodList = (ArrayList<Food>) JsonUtil.toList(response.toString(), Food.class);
                mAdapter.setData(foodList);
                mAdapter.notifyDataSetChanged();
                closeLoading();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("TAG", "onFailure: " + responseString);
            }
        });

    }


    //搜索
    private void reSetData(String max, String min, String s) {

        showLoading("");
        RequestParams params = new RequestParams();
        params.put("maxprice", max);
        params.put("minprice", min);
        params.put("parentId", "69");
        params.put("page", "1");
        params.put("pageSize", "9999");
        HttpUtil.post(Constant.GOODFOOD, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TAG", "onSuccess: " + response.toString());
                foodList.clear();
                foodList = (ArrayList<Food>) JsonUtil.toList(response.toString(), Food.class);
                mAdapter.setData(foodList);
                mAdapter.notifyDataSetChanged();
                closeLoading();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("TAG", "onFailure: " + responseString);
            }
        });

    }

    /**
     * 监听
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            // 当按了搜索之后关闭软键盘
            ((InputMethodManager) edtSearch.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    GoodFoodActivity.this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            search();
            return true;
        }
        return false;
    }


}
