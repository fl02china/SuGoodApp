package com.sugood.app.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.OrderAdapter;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.OrderBean;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.view.RecycleViewDivider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class OrderAllFragment extends BaseFragment {
    XRecyclerView mXRecyclerView;

    List<OrderBean> mList;
    Context mContext;
    OrderAdapter adapter;
    @Override
    protected void initLayout() {
        mContext=getActivity();

        mList = new ArrayList<>();
        mXRecyclerView = (XRecyclerView) findViewById(R.id.user_order);

        mXRecyclerView.setHasFixedSize(true);
        mXRecyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayout.VERTICAL, 5, getResources().getColor(R.color.grey)));
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        adapter = new OrderAdapter(mContext);
        mXRecyclerView.setAdapter(adapter);
        getList();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_order_all;
    }


    void getList(){

        String url = "http://test.goodsolo.com/Speed/Speed/My/WaiOrder";//Speed/My/order
        RequestParams params = new RequestParams();
        params.put("userId", SugoodApplication.user.getUserId());
        params.put("status", "1");
        params.put("page", "1");
        params.put("pageSize", "10");
        System.out.println("params111:"+params);
        HttpUtil.post(url, params, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TAG111", "onSuccess: " + response.toString());
                try {
                    mList = JsonUtil.toList(response.getString("list"), OrderBean.class);
                    Log.e("TAG111222220", "mList: " + mList.get(0).toString());
                   // Log.e("TAG111222221", "mList: " + mList.get(1).toString());
                    Log.e("TAG111222221", "mList: " + mList.get(0).getShopName());
                    adapter.setData(mList);
                    adapter.notifyDataSetChanged();
                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }



            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("TAG111", "onFailure: " + errorResponse);
            }





            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("TAG111", "onFailure: " + responseString);
            }
        });
    }
}
