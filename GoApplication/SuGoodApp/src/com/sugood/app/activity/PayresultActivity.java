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

                    Bundle bundle=new Bundle();
                    bundle.putInt("type",1);

                    intent2.putExtras(bundle);
                    //    intent.setClass(MineActivity.this, UserOrderActivity.class);
                    intent2.setClass(PayresultActivity.this, OrderManagerActivity.class);
                    startActivity(intent2);
                    finish();
                } else {
//                    Intent intent = new Intent();
//                    intent.setClass(MineActivity.this, LoginActivity.class);
//                    startActivity(intent);
                    ToastUtil.setToast(PayresultActivity.this, "请先登录");
                }


            }
        });



    }


}
