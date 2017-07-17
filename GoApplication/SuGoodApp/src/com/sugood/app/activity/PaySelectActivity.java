package com.sugood.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



import com.alipay.sdk.app.PayTask;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.Constants;
import com.sugood.app.R;
import com.sugood.app.alipay.PayResult;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.Wxcont;
import com.sugood.app.global.Constant;
import com.sugood.app.util.AsyncHandler;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.util.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class PaySelectActivity extends BaseActivity {
    String type;
    String orderId;
  String orderDetails;
    boolean bool;
   // JSONObject body;
    String Body;
    private StringBuffer sb;
    private PayReq req;
    private Wxcont wxcont;
    String price ;
    String orderid;
    ResultReceiver mResult;
    private static final int SDK_PAY_FLAG = 1;
    private final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);

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


                   Log.e("223", "resultInfo1111: "+resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {

                        if (!TextUtils.isEmpty(resultInfo)) {
                            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(resultInfo);
                            com.alibaba.fastjson.JSONObject response = jsonObject.getJSONObject("alipay_trade_app_pay_response");
                            String orderNo = response.getString("out_trade_no");
                            if (!TextUtils.isEmpty(orderNo)) {
                                showResult("1",orderNo);

                            }
                        }
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        // Toast.makeText(PaySelectActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(PaySelectActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sb=new StringBuffer();
        setContentView(R.layout.activity_payselect);
        req=new PayReq();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        type = bundle.getString("type");
        price=bundle.getString("price");
        orderDetails=bundle.getString("orderDetails");
        Log.e("sss33333", "price: "+price);
      //  orderId = bundle.getString("orderId");
        orderId = bundle.getString("orderId");
        Body = bundle.getString("Body");

        mResult = new ResultReceiver();
        registerReceiver(mResult, new IntentFilter("ResultActivity"));
        TextView tx_name = (TextView) findViewById(R.id.tx_name);
        TextView tx_time = (TextView) findViewById(R.id.tx_time);
        TextView tx_price = (TextView) findViewById(R.id.tx_price);
        TextView tx_order = (TextView) findViewById(R.id.tx_order);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String time = formatter.format(curDate);


        tx_name.setText(bundle.getString("shopname"));
        tx_price.setText("¥"+price);
        tx_time.setText(time);
        tx_order.setText("订单编号"+orderId);
        final RadioGroup payRroup = (RadioGroup) findViewById(R.id.radiogroup);
        Button payButton = (Button) findViewById(R.id.pay);
        payButton.setText("确定支付 ¥"+price);
        final RadioButton weixin = (RadioButton) payRroup.getChildAt(0);
        final RadioButton ali = (RadioButton) (RadioButton) payRroup.getChildAt(1);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( weixin.isChecked()) {
                    getOrder(1);


                } else if (ali.isChecked()) {

                    getOrder(2);

                } else {

                }
            }
        });





//        sexRroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if(checkedId==weixin.getId()){
//
//
//
//                }
//            }
//        });
    }

    private void wxPay(){
        showLoading("");
        RequestParams params = new RequestParams();
        params.put("mercid", "goodsolo");


        Log.e("TAA", "body: " + Body);
        Log.e("TAA", "orderDetails: " + orderDetails);
        params.put("orderDetails", orderDetails);
        params.put("Body", Body);
        Log.e("TAA" +
                "", "onClick: " + params.toString());


//Constant.SUGOODWX,
        HttpUtil.post(Constant.SUGOODWX, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TAA", "onSuccess1111: " + response.toString());
                closeLoading();
                try {
                    if (response.getInt("code")==1) {
                        Log.e("TAA1111111111", "response: " +  response.toString());
                        wxcont =  JsonUtil.toObject(response.getJSONObject("response").getString("cont"), Wxcont.class);


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

    private void getOrder(final int paytype){
        if (paytype==1){
            wxPay();}else
        {
            alipay();
        }
    }

    /**
     * 支付宝支付订单
     */
    private void alipay() {
        showLoading("");

        String Subject = "速购得";
        RequestParams params = new RequestParams();
        //Log.e("sss", "submitOrder: " + json);
        params.put("Body", Body);
        params.put("Subject", Subject);
        params.put("TotalAmount", price);
        Log.e("sss1111", "Body: " + Body);
        Log.e("sss1111", "Subject: " + Subject);
        Log.e("sss1111", "TotalAmount: " + price);


        HttpUtil.post( Constant.SUGOODSUBMITORDER, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("ssad", "onSuccess: " + response);
                closeLoading();
                try {
                    String orderinfo = response.getString("Body");
               //   tradeno =
                    Log.e("sss1111", "orderinfo: " + orderinfo);
                    payV2(orderinfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                closeLoading();
                Log.e("ssss", "onFailure: " + responseString);
            }
        });


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
                PayTask alipay = new PayTask(PaySelectActivity.this);
                Map<String, String> result = alipay.payV2(orderinfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                System.out.println("2211111111:");
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /*
 * 调起微信支付
 */
    private void sendPayReq() {


        msgApi.registerApp(Constants.APP_ID);
        msgApi.sendReq(req);
     //   finish();
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

    //广播接收事件
    private class ResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {


            showResult("2",wxcont.getOuttradeno());//
        }
    }

    private void showResult(final String paytype,final String outTradeNo){


        showLoading("");
        RequestParams params = new RequestParams();
        params.put("type", paytype);
        params.put("tradeno", outTradeNo);
        Log.e("sss1111", "sss111type: " + type);
        Log.e("sss1111", "sss111outTradeNo: " + outTradeNo);
        Log.e("sss1111", "sss111params: " + params.toString());



        HttpUtil.post(Constant.SUGOODRESULT, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TAA", "onSuccess1111: " + response.toString());
                closeLoading();
                try {
                    if( !response.getBoolean("return_code")){
                        ToastUtil.setToast(PaySelectActivity.this, "支付失败");
                        finish();
                    }else{
                        Intent intent = new Intent();
                        intent.putExtra("type", paytype);
                        intent.putExtra("outTradeNo", outTradeNo);
                        intent.putExtra("price", price.toString());
                        intent.setClass(PaySelectActivity.this, PayresultActivity.class);
                        startActivity(intent);
                        finish();
                        // ToastUtil.setToast(PayresultActivity.this, "支付成功了！");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                Log.e("TAG223322", "onSuccess: " + responseString);
                closeLoading();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TAG223322bb:", "onSuccess: " + response);
                closeLoading();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("TAG223322bb:", "errorResponse: " + errorResponse);
                closeLoading();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("TAA", "onFailure: " + responseString);

                closeLoading();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("1111statusCode", "statusCode: " + statusCode);
                Log.e("TAA1111223", "onFailure: " + errorResponse);
            }
        });






    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mResult);

        getDelegate().onDestroy();

    }

}
