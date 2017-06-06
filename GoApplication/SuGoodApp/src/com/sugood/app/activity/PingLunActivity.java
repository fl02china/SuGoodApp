package com.sugood.app.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.PingLunAdapter;
import com.sugood.app.entity.PingLun;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by wilk on 17/4/26 17:50
 * ganweib@gmail.com
 * describe:
 */

public class PingLunActivity extends BaseActivity {

    ImageView user_order_header_back;
    RecyclerView rcv_pinglun;
    PingLunAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping);
        initView();
        inintData();

    }

    private void initView() {
        user_order_header_back = (ImageView) findViewById(R.id.user_order_header_back);
        user_order_header_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rcv_pinglun = (RecyclerView) findViewById(R.id.rcv_pinglun);
        mAdapter = new PingLunAdapter();
        rcv_pinglun.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv_pinglun.setHasFixedSize(true);
        rcv_pinglun.setAdapter(mAdapter);

    }

    private void inintData() {

        RequestParams params = new RequestParams();
        params.put("shopId", getIntent().getStringExtra("shopId"));
        params.put("page", "1");
        params.put("pageSize", "100");
        HttpUtil.post(Constant.SHOPPINLUN_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("sadas", "onSuccess: " + response.toString());
                List<PingLun> pingluns = JsonUtil.toList(response.toString(), PingLun.class);
                mAdapter.setData(pingluns);
                mAdapter.notifyDataSetChanged();
            }
        });


    }
}
