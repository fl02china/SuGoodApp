package com.sugood.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.StatusAdapter;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.Rows;
import com.sugood.app.entity.Status;
import com.sugood.app.fragment.WillPayFragment;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Package :com.sugood.app.activity
 * Description :
 * Author :Rc3
 * Created at :2017/04/19 14:18.
 */

public class UserTakeawayActivity extends BaseActivity {


    RecyclerView rcv_status;
    ImageView back;
    StatusAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takeaway);
        back = (ImageView) findViewById(R.id.takeaway_user_header_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAdapter = new StatusAdapter();
        rcv_status = (RecyclerView) findViewById(R.id.rc_waimaiStatusR);
        rcv_status.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv_status.setHasFixedSize(true);
        rcv_status.setAdapter(mAdapter);
        initData();


    }

    private void initData() {

        showLoading("");

        RequestParams params = new RequestParams();
        params.put("userId", SugoodApplication.user.getUserId());
        params.put("page", "1");
        params.put("pageSize", "10");
        HttpUtil.post(Constant.DIANDAN_LIST_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("UserTakeawayActivity", "onSuccess: " + response.toString());

                try {
                    List<Rows> rows = JsonUtil.toList(response.getString("rows"), Rows.class);
                    if (response.getInt("total") > 0) {
                        Log.e("UserTakeawayActivity", "onSuccess: " + rows.get(0).getCell().getOrderId());
                        getOrderInfo(rows.get(0).getCell().getTypeOrderId());
                    } else {
                        closeLoading();
                        ToastUtil.setToast(UserTakeawayActivity.this, "还没有订单噢，快去下单吧");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                closeLoading();
            }
        });

    }

    private void getOrderInfo(String orderId) {

        RequestParams params = new RequestParams();
        params.put("typeOrderId", orderId);
        HttpUtil.post(Constant.DINGDAN_STATUS_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("typeOrderId", "onSuccess: " + response.toString());
                try {
                    List<Status> status = JsonUtil.toList(response.getString("content"), Status.class);
                    Log.e("tas", "onSuccess: " + status.size());
                    mAdapter.setData(status);
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                closeLoading();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                closeLoading();
            }
        });


    }

}
