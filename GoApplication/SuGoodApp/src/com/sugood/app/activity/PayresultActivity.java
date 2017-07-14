package com.sugood.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

public class PayresultActivity extends BaseActivity {
    String type;
    String tradeno;
    String price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payresult);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
         type = bundle.getString("type");
        price= bundle.getString("price");
         tradeno=bundle.getString("outTradeNo");
        Button orderButton = (Button) findViewById(R.id.order);
       TextView tx_price = (TextView) findViewById(R.id.price);
        tx_price.setText(price);
        //getresult(type,tradeno);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SugoodApplication.isLogin) {
                    //Toast.makeText(mContext, "订单", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent();
                    intent2.setClass(PayresultActivity.this, UserOrderActivity.class);
                    startActivity(intent2);
                } else {
//                    Intent intent = new Intent();
//                    intent.setClass(MineActivity.this, LoginActivity.class);
//                    startActivity(intent);
                    ToastUtil.setToast(PayresultActivity.this, "请先登录");
                }


            }
        });



    }

    private void getresult( String paytype,String outTradeNo){
       showLoading("");
        RequestParams params = new RequestParams();
        params.put("type", paytype);
        params.put("tradeno", outTradeNo);
        Log.e("sss1111", "sss111type: " + type);
        Log.e("sss1111", "sss111outTradeNo: " + outTradeNo);
        Log.e("sss1111", "sss111params: " + params.toString());
         //  String ur = "http://test.goodsolo.com/Speed/Speed/pay";
       String url = "http://test.goodsolo.com/Speed/Speed/pay/query";

        HttpUtil.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TAA", "onSuccess1111: " + response.toString());
                closeLoading();
                try {
                    if( !response.getBoolean("return_code")){
                        ToastUtil.setToast(PayresultActivity.this, "支付失败了！");
                        finish();
                    }else{
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
}
