package com.sugood.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.AddressAdapter;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.UserAddress;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Package :com.android.supermarket.user
 * Description :
 * Author :Rc3
 * Created at :2017/3/8 17:50.
 */

public class ManageAddressActivity extends AppCompatActivity {


    XRecyclerView mXRecyclerView;
    List<UserAddress> mList;
    Button mAddAddressBtn;
    Context mContext;
    ImageView mBack;
    AddressAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_address_activity);
        mContext = this;
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        RequestParams params = new RequestParams();
        params.put("userId", SugoodApplication.user.getUserId());
        HttpUtil.post(Constant.SUGOODADDR, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TAG", "onSuccess: " + response.toString());
                mList.clear();
                mList = JsonUtil.toList(response.toString(), UserAddress.class);
                adapter.setList(mList);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.user_manager_address_header_back);
        mXRecyclerView = (XRecyclerView) findViewById(R.id.user_manager_address_rv);
        mAddAddressBtn = (Button) findViewById(R.id.user_add_new_address);
        mList = new ArrayList<>();
        adapter = new AddressAdapter(mContext);
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mXRecyclerView.setHasFixedSize(true);
        mXRecyclerView.setAdapter(adapter);


        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mXRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {

            }
        });

        adapter.setOnItemOnClickListener(new AddressAdapter.ItemViewOnClickListener() {
            @Override
            public void onItemOnClick(View view, int position) {
                Log.e("TAgg", "onItemOnClick: " + position);
                Intent intent = new Intent(mContext, AddAddrActivity.class);
                intent.putExtra("address", mList.get(position-1));
                startActivityForResult(intent, 0);
            }
        });
        mAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageAddressActivity.this, AddAddressActivity.class));
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            initData();
        }
    }
}
