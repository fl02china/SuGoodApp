package com.sugood.app.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.UserOrderAdapter;
import com.sugood.app.alipay.PayResult;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.UserOrder;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.util.ToastUtil;
import com.sugood.app.view.RecycleViewDivider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


public class ServiceDemandActivity extends BaseActivity {


    XRecyclerView mXRecyclerView;

    List<UserOrder> mList;
    Context mContext;
    UserOrderAdapter adapter;
    ImageView user_order_header_back;
    private long exitTime = 0;


    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
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
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(ServiceDemandActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ServiceDemandActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_order_activity);
        mContext = this;
        if (!SugoodApplication.isLogin) {
            ToastUtil.setToast(this, "请先登录账号");
            Intent intent = new Intent();
            intent.setClass(this, LoginActivity.class);
            startActivityForResult(intent, 0);
            return;
        } else {
            initView();
            initData();
        }
    }

    private void initData() {

        mList = new ArrayList<>();
        mXRecyclerView.setHasFixedSize(true);
        mXRecyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayout.VERTICAL, 5, getResources().getColor(R.color.grey)));
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        adapter = new UserOrderAdapter(mContext);
        mXRecyclerView.setAdapter(adapter);

        RequestParams params = new RequestParams();
        params.put("userId", SugoodApplication.user.getUserId());
        HttpUtil.post(Constant.SUGOODORDER, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TAG", "onSuccess: " + response.toString());
                mList = JsonUtil.toList(response.toString(), UserOrder.class);
                adapter.setData(mList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

        adapter.setViewOnClickListener(new UserOrderAdapter.TextViewViewOnClickListener() {
            @Override
            public void onOnClick(View view, int position) {
                submitOrder(position);
            }
        });

        adapter.setTKOnClickListener(new UserOrderAdapter.TextOnClickListener() {
            @Override
            public void onOnClick(View view, int position) {
                tuikuan(position);
            }
        });


        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mXRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
//               mList.add(new UserOrder());
                mXRecyclerView.loadMoreComplete();
            }
        });
    }

    private void tuikuan(int pos) {


        showLoading("");
        RequestParams params = new RequestParams();
        params.put("orderId", mList.get(pos).getOrderId());
        params.put("code", mList.get(pos).getCode());
        params.put("type", mList.get(pos).getType());
        HttpUtil.post(Constant.TUIKUAN_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TUi", "onSuccess: " + response.toString());
                closeLoading();
                try {
                    if (!response.getBoolean("success")) {
                        ToastUtil.setToast(ServiceDemandActivity.this, response.getString("message"));
                    } else {
                        ToastUtil.setToast(ServiceDemandActivity.this, "提交退款申请成功");
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
                ToastUtil.setToast(ServiceDemandActivity.this, "提交退款申请失败");
            }
        });
    }

    /**
     * 提交订单
     */
    private void submitOrder(int ps) {

//        Log.e("JSON", "submitOrder: "+ JsonUtil.toJson(mList.get(ps)));

        String body = mList.get(ps).getProductName();
        String Subject = "速购得";
        RequestParams params = new RequestParams();
        params.put("Body", JsonUtil.toJson(mList.get(ps)));
        params.put("Subject", Subject);
        params.put("TotalAmount", mList.get(ps).getPrice());

        HttpUtil.post(Constant.SUGOODSUBMITORDER, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("ssad", "onSuccess: " + response);
                try {
                    String orderinfo = response.getString("Body");
                    payV2(orderinfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("ssss", "onFailure: " + responseString);
            }
        });


    }

    private void initView() {
        user_order_header_back = (ImageView) findViewById(R.id.user_order_header_back);
        mXRecyclerView = (XRecyclerView) findViewById(R.id.user_order_rv);
        user_order_header_back.setVisibility(View.GONE);

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
                PayTask alipay = new PayTask(ServiceDemandActivity.this);
                Map<String, String> result = alipay.payV2(orderinfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            initView();
            if (SugoodApplication.isLogin) {
                initData();
            } else {
                ToastUtil.setToast(ServiceDemandActivity.this, "请登录，再查看订单。");
            }

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }

    }
}
