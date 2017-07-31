package com.sugood.app.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.sugood.app.activity.PaySelectActivity;
import com.sugood.app.adapter.OrderAdapter;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.OrderBean;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.util.ToastUtil;
import com.sugood.app.view.RecycleViewDivider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class OrderAllFragment extends BaseFragment {
    XRecyclerView mXRecyclerView;

    List<OrderBean> mList;
    Context mContext;
    OrderAdapter adapter;


    @Override
    protected void initLayout() {
        mContext = getActivity();

        mList = new ArrayList<>();
        mXRecyclerView = (XRecyclerView) findViewById(R.id.user_order);

        mXRecyclerView.setHasFixedSize(true);
        mXRecyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayout.VERTICAL, 5, getResources().getColor(R.color.grey)));
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        adapter = new OrderAdapter(mContext);
        mXRecyclerView.setAdapter(adapter);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mXRecyclerView.setNestedScrollingEnabled(true);
                getList();
                mXRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mXRecyclerView.setNestedScrollingEnabled(true);

                getList();
                mXRecyclerView.loadMoreComplete();

            }
        });
        mXRecyclerView.refresh();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_order_all;
    }

    @Override
    protected int getStatus() {
        return 1;
    }


    private void cancleOrder(int pos,String type) {

        showLoading("");
        RequestParams params = new RequestParams();
        params.put("orderId", mList.get(pos).getOrderId());

        params.put("type",type);
        HttpUtil.post(Constant.CANCLEORDER, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TUi", "onSuccess: " + response.toString());
                closeLoading();
                try {
                    if (!response.getBoolean("success")) {
                        ToastUtil.setToast(getActivity(), response.getString("message"));
                    } else {
                        ToastUtil.setToast(getActivity(), "提交退款申请成功");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("TUi", "onSuccess: " + responseString);
                closeLoading();
                ToastUtil.setToast(getActivity(), "提交退款申请失败");
            }
        });
    }

    private void tuikuan(int pos,String type,String code) {

        showLoading("");
        RequestParams params = new RequestParams();
        params.put("orderId", mList.get(pos).getOrderId());
        params.put("code", code);
        params.put("type",type);
        HttpUtil.post(Constant.TUIKUAN_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TUi", "onSuccess: " + response.toString());
                closeLoading();
                try {
                    if (!response.getBoolean("success")) {
                        ToastUtil.setToast(getActivity(), response.getString("message"));
                    } else {
                        ToastUtil.setToast(getActivity(), "提交退款申请成功");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("TUi", "onSuccess: " + responseString);
                closeLoading();
                ToastUtil.setToast(getActivity(), "提交退款申请失败");
            }
        });
    }


    void getList() {

        // String url = "http://test.goodsolo.com/Speed/Speed/My/WaiOrder";//Speed/My/order
        RequestParams params = new RequestParams();
        params.put("userId", SugoodApplication.user.getUserId());


        params.put("status", getStatus());
        params.put("page", "1");
        params.put("pageSize", "100");
        System.out.println("params111:" + params);
        HttpUtil.post(Constant.SUGOODWMORDER, params, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TAG111", "onSuccess: " + response.toString());
                try {
                    mList = JsonUtil.toList(response.getString("list"), OrderBean.class);
                    Collections.reverse(mList);
                    adapter.setData(mList);


                    adapter.setOnConnectClickListener(new OrderAdapter.OnConnectClickListener() {

                        @Override
                        public void onOnClick(View view, int position) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mList.get(position).getTel()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                    adapter.setOnLeftClickListener(new OrderAdapter.OnLeftClickListener() {
                        @Override
                        public void onOnClick(View view, int position) {
                            OrderBean order = mList.get(position);
                            switch (order.getStatus()){
                                case 0:
                                    cancleOrder(position,"ele");
                                    tip("取消订单");
                                    break;
                                case 1:
                                    tuikuan(position,"ele","");
                                    tip("申请退款");
                                    break;
                                case 9:
                                case 2:
                                case 13:
                                    tip("联系骑手");
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mList.get(position).getmobile()));
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    break;
                                case 8:
                                    tip("再来一单");
                                    break;
                            }
                        }
                    });
                    adapter.setOnRightClickListener(new OrderAdapter.OnRightClickListener() {
                        @Override
                        public void onOnClick(View view, int position) {
                            OrderBean order = mList.get(position);
                          switch (order.getStatus()){
                              case 0:
                                  JSONObject body = new JSONObject();
                                  JSONObject json = new JSONObject();
                                  double price= (double) order.getNeedPay()/100;
                                  try {
                                      json.put("needPay",price+"");
                                      body.put("type", "1");
                                      body.put("orderId", order.getOrderId());
                                      body.put("shopName", order.getShopName());
                                      Intent intent = new Intent();
                                      intent.putExtra("shopname", order.getShopName());
                                      intent.putExtra("orderId", order.getOrderId());
                                      intent.putExtra("orderDetails", json.toString());
                                      intent.putExtra("Body", body.toString());
                                      intent.putExtra("type", "1");

                                      intent.putExtra("price",price+"");
                                      //   intent.putExtra("time", price.toString());
                                      intent.setClass(mContext, PaySelectActivity.class);
                                      startActivityForResult(intent, 6);
                                  } catch (JSONException e) {
                                      e.printStackTrace();
                                  }
                                  break;

                              case 8:
                                  tip("评价");
                                  break;

                          }

                        }
                    });
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
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
