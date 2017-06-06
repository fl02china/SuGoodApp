package com.sugood.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.AddressAdapter;
import com.sugood.app.adapter.MarketAddressAdapter;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.UserAddress;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;

import org.json.JSONArray;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Package :com.android.supermarket.takeaway
 * Description :
 * Author :Rc3
 * Created at :2017/3/2 17:01.
 */

public class TakeawayAddressActivity extends BaseActivity {

    XRecyclerView mRecyclerView;
    Context mContext;
    List<UserAddress> mList;
    ImageView mBack;
    TextView mManager;
    Button mAdd;
    MarketAddressAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takeaway_address_activity);
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
                Log.e("TAG", "onSuccess: address " + response.toString());
                mList = JsonUtil.toList(response.toString(), UserAddress.class);
                adapter.setList(mList);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

        adapter.setOnItemOnClickListener(new AddressAdapter.ItemViewOnClickListener() {
            @Override
            public void onItemOnClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("address", mList.get(position - 1));
                setResult(111, intent);
                finish();
            }
        });

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.loadMoreComplete();
            }
        });
    }

    private void initView() {
        mAdd = (Button) findViewById(R.id.address_delete);
        mRecyclerView = (XRecyclerView) findViewById(R.id.takeaway_address_rv);
        mBack = (ImageView) findViewById(R.id.takeaway_address_header_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(110, intent);
                finish();
            }
        });
        mManager = (TextView) findViewById(R.id.takeaway_address_manager_tv);
        mManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).isEdit()) {
                        mList.get(i).setEdit(false);
                    } else {
                        mList.get(i).setEdit(true);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(mContext, AddAddrActivity.class), 0);
            }
        });
        adapter = new MarketAddressAdapter(mContext);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            setResult(110, intent);
            finish();
        }

        return false;

    }

}
