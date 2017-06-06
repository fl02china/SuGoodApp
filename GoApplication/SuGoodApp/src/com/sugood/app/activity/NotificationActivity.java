package com.sugood.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.UserNotificationAdapter;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.Msg;
import com.sugood.app.entity.UserNotification;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Package :com.android.supermarket.user
 * Description :
 * Author :Rc3
 * Created at :2017/3/10 15:17.
 */

public class NotificationActivity extends AppCompatActivity {


    XRecyclerView mXRecyclerView;
    List<UserNotification> mList;
    LinearLayout noMsg;
    Context mContext;
    ImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_notification_activity);
        mContext = this;
        initView();
        initData();
    }

    private void initData() {

        iv_back = (ImageView) findViewById(R.id.user_notification_header_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mXRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mXRecyclerView.setHasFixedSize(true);
        RequestParams params = new RequestParams();
        params.put("pageSize", "90");
        params.put("page", "1");
        params.put("userId", SugoodApplication.user.getUserId());
        HttpUtil.post(Constant.SUGOODMSG_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("tag", "onFailure: " + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                List<Msg> list = JsonUtil.toList(response.toString(), Msg.class);
                Log.e("tag", "onSuccess: " + response.toString());
                Log.e("list", "onSuccess: " + list.toString());
                if (list.size() == 0 || list == null) {
                    mXRecyclerView.setVisibility(View.GONE);
                    noMsg.setVisibility(View.VISIBLE);
                } else {
                    mXRecyclerView.setVisibility(View.VISIBLE);
                    noMsg.setVisibility(View.GONE);
                }
                mXRecyclerView.setAdapter(new UserNotificationAdapter(mContext, list));
            }
        });

        mList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            mList.add(new UserNotification());
        }
//        mXRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
//        mXRecyclerView.setHasFixedSize(true);


    }

    private void initView() {
        mXRecyclerView = (XRecyclerView) findViewById(R.id.user_notification_rv);
        noMsg = (LinearLayout) findViewById(R.id.noMsg);
    }
}
