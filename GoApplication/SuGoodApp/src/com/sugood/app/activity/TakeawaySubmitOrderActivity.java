package com.sugood.app.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.Constants;
import com.sugood.app.MD5;
import com.sugood.app.R;
import com.sugood.app.adapter.TakeawaySchedulerAdapter;
import com.sugood.app.adapter.TakerawayCustomerAdapter;
import com.sugood.app.alipay.PayResult;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.ShopCarProduct;
import com.sugood.app.entity.TakeawayShop;
import com.sugood.app.entity.TakeawayShopInfo;
import com.sugood.app.entity.UserAddress;
import com.sugood.app.entity.Wxcont;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.util.OtherUtils;
import com.sugood.app.util.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Package :com.android.supermarket.takeaway
 * Description :
 * Author :Rc3
 * Created at :2017/3/2 15:08.
 */

public class TakeawaySubmitOrderActivity extends BaseActivity {
    private static final String TAG = TakeawaySubmitOrderActivity.class.getSimpleName();
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    RelativeLayout mTimeLayout;
    RelativeLayout mCustomerLayout;
    RelativeLayout mAddressLayout;
    RelativeLayout mRemarkLayout;
    RelativeLayout mPayLayout;
    Context mContext;
    View mView;
    TextView mRemarkTV;
    Spinner mSpinner;
    RelativeLayout rl_address;
    RelativeLayout rl_choose_address;
    TextView market_address_tv;
    TextView market_address_name_tv;
    TextView tv_shopName;
    TextView takeaway_submit_order_food_name1;
    TextView takeaway_submit_order_food_amount1;
    TextView takeaway_submit_order_food_price1;
    TextView tv_allPrice;
    Button btn_submit;
    TextView tv_log;
    TextView tv_allpricce;
    ImageView mBack;
    UserAddress userAddress;
    String mRemark;
    TakeawayShopInfo.EleBean shop;
    Double price = 0.00;
    JSONObject json;
    RecyclerView mRv;
    private Wxcont wxcont;
    private StringBuffer sb;
    private Map<String,String> resultunifiedorder;
    private PayReq req;
    private final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);

    List<ShopCarProduct> shopCarList = new ArrayList<>();
    // Realm realm = Realm.getDefaultInstance();
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    Log.e(TAG, "resultInfo1111: "+resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(TakeawaySubmitOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
/*
                        RealmResults<ShopCarList> list = realm.where(ShopCarList.class).findAll();
                        list.addChangeListener(new RealmChangeListener<RealmResults<ShopCarList>>() {
                            @Override
                            public void onChange(RealmResults<ShopCarList> element) {
                                element.deleteAllFromRealm();
                            }
                        });*/


                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(TakeawaySubmitOrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takeaway_submit_order_activity);
        mContext = this;
        sb=new StringBuffer();
        req=new PayReq();
        initView();
        initData();

    }

    private void initData() {


        RequestParams params = new RequestParams();
        params.put("userId", SugoodApplication.user.getUserId());
        params.put("shopId", shop.getShopId());

        HttpUtil.post(Constant.SUGOODISNEW, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    boolean isnew = response.getBoolean("success");
                    CountPrice(isnew);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        mTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = LayoutInflater.from(mContext).inflate(R.layout.takeaway_scheduler, null);
                PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setOutsideTouchable(true);
                mView.setVisibility(View.VISIBLE);
                popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
                popupWindow.showAtLocation(mTimeLayout, Gravity.BOTTOM, 0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mView.setVisibility(View.GONE);
                    }
                });
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.takeaway_scheduler_rv);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(new TakeawaySchedulerAdapter(mContext));

            }
        });

        mCustomerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.takeaway_customer_item, null);
                PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setOutsideTouchable(true);
                mView.setVisibility(View.VISIBLE);
                popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
                popupWindow.showAtLocation(mTimeLayout, Gravity.BOTTOM, 0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mView.setVisibility(View.GONE);
                    }
                });
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.takeaway_customer_rv);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(new TakerawayCustomerAdapter(mContext));
            }
        });
        mAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext, TakeawayAddressActivity.class), 0);
            }
        });

        mRemarkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("remark", mRemarkTV.getText().toString());
                intent.setClass(mContext, TakeawayRemarkActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        mPayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(mContext, TakeawayPayActivity.class));
            }
        });


    }


    private void CountPrice(boolean isnew) {


        int count = 0;
        double allp = 0.0;
        for (int i = 0; i < shopCarList.size(); i++) {
            price = price + Double.parseDouble(shopCarList.get(i).getFoodPrice());
            allp = allp + Double.parseDouble(shopCarList.get(i).getFoodPrice());
            count = count + shopCarList.get(i).getFoodAmount();
        }
//        if (isnew) {
//            if (shop.getIsNew() == 1) {
//                price = price - shop.getNewMoney();
//            }
//        }
//        if (!TextUtils.isEmpty(shop.getTypeId())) {
//            if (shop.getTypeId().equals("1")) {
//                if (price > shop.getMinAmount()) {
//                    price = price - shop.getAmount();
//                }
//            }
//        }

        tv_allPrice.setText("￥" + (price + shop.getLogistics()));
        tv_allpricce.setText("￥" + (price + shop.getLogistics()));
        price = price + shop.getLogistics();
        json = new JSONObject();
        try {
            json.put("userId", SugoodApplication.user.getUserId());
            if (userAddress != null) {
                json.put("addrId", userAddress.getId());
            }
            json.put("logistics", shop.getLogistics());
            json.put("shopId", shop.getShopId());
            json.put("orderNum", count);
            json.put("newMoney", allp - price + shop.getLogistics());
            json.put("totalPrice", allp);
            json.put("needPay", price);
            json.put("message", mRemark);

            JSONArray array = new JSONArray();
            for (int i = 0; i < shopCarList.size(); i++) {
                JSONObject json = new JSONObject();
                json.put("productId", shopCarList.get(i).getProductId());
                json.put("num", shopCarList.get(i).getFoodAmount());
                json.put("price", shopCarList.get(i).getFoodPrice());
                array.put(json);
            }
            json.put("details", array);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initView() {
       /* //查询数据库
        RealmResults<ShopCarList> list = realm.where(ShopCarList.class).findAll();
        Log.i(TAG, "CountPrice: " + list.size());


        for (int i = 0; i < list.size(); i++) {
            shopCarList.addAll(list.get(i).getShopCarProducts());
        }*/
        shopCarList = SugoodApplication.getInstance().getShopCarProductList();

        mRv = (RecyclerView) findViewById(R.id.submit_rv);
        mRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRv.setAdapter(new SubmitAdapter());
        mRemarkTV = (TextView) findViewById(R.id.submit_order_remark);
        mBack = (ImageView) findViewById(R.id.takeaway_submit_order_back);
        shop = (TakeawayShopInfo.EleBean) getIntent().getSerializableExtra("shop");
        mTimeLayout = (RelativeLayout) findViewById(R.id.takeaway_submit_order_time_rl);
        mView = findViewById(R.id.takeaway_submit_order_popupwindow_bg);
        mCustomerLayout = (RelativeLayout) findViewById(R.id.takeaway_submit_order_customer_rl);
        mAddressLayout = (RelativeLayout) findViewById(R.id.takeaway_submit_order_address_rl);
        mRemarkLayout = (RelativeLayout) findViewById(R.id.takeaway_submit_order_remark_rl);
        mPayLayout = (RelativeLayout) findViewById(R.id.takeaway_submit_order_payway_rl);
        rl_address = (RelativeLayout) findViewById(R.id.rl_address);
        rl_choose_address = (RelativeLayout) findViewById(R.id.rl_choose_address);
        market_address_name_tv = (TextView) findViewById(R.id.market_address_name_tv);
        market_address_tv = (TextView) findViewById(R.id.market_address_tv);
        //  tv_shopName = (TextView) findViewById(R.id.tv_shop_name);
      /*  takeaway_submit_order_food_name1 = (TextView) findViewById(R.id.takeaway_submit_order_food_name1);
        takeaway_submit_order_food_amount1 = (TextView) findViewById(R.id.takeaway_submit_order_food_amount1);
        takeaway_submit_order_food_price1 = (TextView) findViewById(R.id.takeaway_submit_order_food_price1);*/
        tv_allpricce = (TextView) findViewById(R.id.tv_allpricce);
        tv_log = (TextView) findViewById(R.id.tv_log);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_allPrice = (TextView) findViewById(R.id.tv_allPrice);


        tv_log.setText("￥=" + shop.getLogistics());
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userAddress == null) {
                    ToastUtil.setToast(TakeawaySubmitOrderActivity.this, "请添加地址");
                    return;
                }
                showLoading("");
               //aliPay();

               wxPay();
            }

            private void wxPay(){
                try {
                    String type = getIntent().getStringExtra("type");
                    if (type.equals("shop")) {
                        json.put("type", "3");
                    } else if (type.equals("waimai")) {
                        json.put("type", "1");
                    } else {
                        json.put("type", "2");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                RequestParams params = new RequestParams();
                params.put("mercid", "goodsolo");

                String type = getIntent().getStringExtra("type");
                if (type.equals("shop")) {
                    params.put("attach", "3");
                } else if (type.equals("waimai")) {
                    params.put("attach", "1");
                } else {
                    params.put("attach", "2");
                }

                params.put("orderDetails", json);
                Log.e("TAA" +
                        "", "onClick: " + json.toString());
                String ur = "http://test.goodsolo.com/Speed/Speed/pay";

//Constant.SUGOODWX,
                HttpUtil.post(ur, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.e("TAA", "onSuccess1111: " + response.toString());
                        closeLoading();
                        try {
                            if (response.getInt("code")==1) {
                                 wxcont =  JsonUtil.toObject(response.getJSONObject("response").getString("cont"), Wxcont.class);
                                Log.e("TAA1111111111", "onSuccess: " +  SugoodApplication.getInstance().getPackageName());

                                genPayReq();//生成签名参数
                                sendPayReq();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.e("TAA", "onFailure: " + responseString);

                        closeLoading();
                    }
                });
            }

            private void getOrder() {

                try {
                    String type = getIntent().getStringExtra("type");
                    if (type.equals("shop")) {
                        json.put("type", "3");
                    } else if (type.equals("waimai")) {
                        json.put("type", "1");
                    } else {
                        json.put("type", "2");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                RequestParams params = new RequestParams();
                params.put("orderDetails", json);
                Log.e("TAA" +
                        "", "onClick: " + json.toString());

                String ur = " http://test.goodsolo.com/Speed/Speed/alipay/placeOrder";
// HttpUtil.post(Constant.SUGOODDOWNORDER, params, new JsonHttpResponseHandler()
                HttpUtil.post(Constant.SUGOODDOWNORDER, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.e("TAA", "onSuccess: " + response.toString());
                        closeLoading();
                        try {
                            if (response.getBoolean("success")) {
                                Log.e("TAA1111111111", "onSuccess: " + response.toString());
                                Intent intent = new Intent();
                                intent.putExtra("orderId", response.getString("orderId"));
                                intent.setClass(mContext, PaySelectActivity.class);
                                startActivityForResult(intent, 6);
                                submitOrder(response.getString("orderId"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.e("TAA", "onFailure: " + responseString);
                        closeLoading();
                    }
                });
            }
        });


    }

    /*
 * 调起微信支付
 */
    private void sendPayReq() {


        msgApi.registerApp(Constants.APP_ID);
        msgApi.sendReq(req);
        Log.i("11111>>>>>", req.partnerId);
    }
    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }
    private void genPayReq() {

        req.appId = Constants.APP_ID;
        req.partnerId = Constants.MCH_ID;

        req.prepayId =wxcont.getPrepayid();
     //   Log.e("Simon", "----"+wxcont.getPackagestr());
        req.packageValue =  "Sign=WXPay";
        req.nonceStr =wxcont.getNoncestr();
        req.timeStamp =wxcont.getTimestamp();


//        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
//        signParams.add(new BasicNameValuePair("appid", req.appId));
//        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
//        signParams.add(new BasicNameValuePair("package", req.packageValue));
//        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
//        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
//        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = wxcont.getSign();

        sb.append("sign\n"+req.sign+"\n\n");



        //Log.e("Simon", "----"+signParams.toString());

    }
//    private String genAppSign(List<NameValuePair> params) {
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < params.size(); i++) {
//            sb.append(params.get(i).getName());
//            sb.append('=');
//            sb.append(params.get(i).getValue());
//            sb.append('&');
//        }
//        sb.append("key=");
//        sb.append(Constants.API_KEY);
//
//        this.sb.append("sign str\n"+sb.toString()+"\n\n");
//        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
//        Log.e("Simon","----"+appSign);
//        return appSign;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == 111) {

                userAddress = (UserAddress) data.getSerializableExtra("address");
                rl_address.setVisibility(View.VISIBLE);
                rl_choose_address.setVisibility(View.GONE);
                market_address_tv.setText(userAddress.getAreaStr() + userAddress.getInfo());
                market_address_name_tv.setText(userAddress.getXm() + "  " + userAddress.getTel());
                try {
                    json.put("addrId", userAddress.getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == 110) {
                userAddress = null;
                rl_address.setVisibility(View.GONE);
                rl_choose_address.setVisibility(View.VISIBLE);
                market_address_tv.setText("");
                market_address_name_tv.setText("");
            } else {
                Log.e("RRRRRRRR", "onActivityResult: " + data.getStringExtra("remark"));
                mRemark = data.getStringExtra("remark");
                mRemarkTV.setText(data.getStringExtra("remark"));
            }

        }
    }

    /**
     * 支付宝支付业务
     *
     * @param orderinfo
     */
    public void payV2(final String orderinfo) {
//        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
//            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialoginterface, int i) {
//                            //
//                            finish();
//                        }
//                    }).show();
//            return;
//        }
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
//                EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
                PayTask alipay = new PayTask(TakeawaySubmitOrderActivity.this);
                Map<String, String> result = alipay.payV2(orderinfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * 提交订单
     */
    private void submitOrder(String id) {

//        Log.e("JSON", "submitOrder: "+ JsonUtil.toJson(mList.get(ps)));

        try {
            json.put("orderId", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String Subject = "速购得";
        RequestParams params = new RequestParams();
        Log.e("sss", "submitOrder: " + json);
        params.put("Body", json);
        params.put("Subject", Subject);
        params.put("TotalAmount", price);

        HttpUtil.post(Constant.SUGOODSUBMITORDER, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("ssad", "onSuccess: " + response);
                try {
                    String orderinfo = response.getString("Body");
                    payV2(orderinfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("ssss", "onFailure: " + responseString);
            }
        });


    }



    class SubmitAdapter extends RecyclerView.Adapter<SubmitAdapter.MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.submit_rv_item, parent, false));
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            for (int i = 0; i < shopCarList.size(); i++) {
                Log.i(TAG, "initView: " + shopCarList.get(i).getFoodName());
            }
            ShopCarProduct product = shopCarList.get(position);
            holder.mShop.setText(product.getFoodName());
            holder.mProduct.setText("x" + product.getFoodAmount());
            holder.mNum.setText("￥=" + product.getFoodPrice());
        }

        @Override
        public int getItemCount() {
            return shopCarList.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.submit_shop)
            TextView mShop;
            @BindView(R.id.submit_product)
            TextView mProduct;
            @BindView(R.id.product_mun)
            TextView mNum;

            public MyHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}

