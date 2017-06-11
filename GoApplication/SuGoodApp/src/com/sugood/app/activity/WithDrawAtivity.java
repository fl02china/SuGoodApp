package com.sugood.app.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.baidu.platform.comapi.map.B;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.ToastUtil;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Package :com.sugood.app.activity
 * Description :
 * Author :Rc3
 * Created at :2017/04/25 13:50.
 */

public class WithDrawAtivity extends BaseActivity {
    private static final String TAG = WithDrawAtivity.class.getSimpleName();
    ImageView mBack;
    TextView mChoice;
    Context mContext;
    RelativeLayout mRl;
    View mView;
    PopupWindow popupWindow;

    EditText with_draw_account_name_ed; //用户账号
    EditText with_draw_account_price_ed; //提现金额
    EditText with_draw_account_nickname_ed; //提现用户名
    Button btn_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.with_draw_activity);

        mContext = this;
        with_draw_account_name_ed = (EditText) findViewById(R.id.with_draw_account_name_ed);
        with_draw_account_price_ed = (EditText) findViewById(R.id.with_draw_account_price_ed);
        with_draw_account_nickname_ed = (EditText) findViewById(R.id.with_draw_account_nickname_ed);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        mView = findViewById(R.id.with_draw_view);
        mRl = (RelativeLayout) findViewById(R.id.with_draw_all_rl);
        mBack = (ImageView) findViewById(R.id.with_draw_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mChoice = (TextView) findViewById(R.id.with_draw_account_name_picker);
        mChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                View view = LayoutInflater.from(mContext).inflate(R.layout.account_type, null);
                Button alipay = (Button) view.findViewById(R.id.account_alipay);
                Button wechat = (Button) view.findViewById(R.id.account_wechat);
                Button bank = (Button) view.findViewById(R.id.account_bank);
                Button cancel = (Button) view.findViewById(R.id.account_cancel);

                alipay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mChoice.setText("支付宝");
                        popupWindow.dismiss();
                    }
                });
                wechat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mChoice.setText("微信");
                        popupWindow.dismiss();
                    }
                });

                bank.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mChoice.setText("银行卡");
                        popupWindow.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                mView.setVisibility(View.VISIBLE);
                popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
                popupWindow.showAtLocation(mRl, Gravity.BOTTOM, 0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mView.setVisibility(View.GONE);
                    }
                });
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Double.parseDouble(SugoodApplication.user.getMoney()) > 0) {
                    if (mChoice.getText().toString().equals("请选择")) {
                        ToastUtil.setToast(WithDrawAtivity.this, "请选择体现账户类型");
                        return;
                    }
                    if (TextUtils.isEmpty(with_draw_account_name_ed.getText().toString())) {
                        ToastUtil.setToast(WithDrawAtivity.this, "账户名不能为空");
                        return;
                    }
                    if ((Integer.parseInt(with_draw_account_price_ed.getText().toString())) * 100 > Integer.parseInt(SugoodApplication.user.getMoney())) {
                        ToastUtil.setToast(WithDrawAtivity.this, "提现金额不能大于账户余额");
                        return;
                    }
                    if (TextUtils.isEmpty(with_draw_account_price_ed.getText().toString())) {
                        ToastUtil.setToast(WithDrawAtivity.this, "提现金额不能为空");
                        return;
                    }
                    if (TextUtils.isEmpty(with_draw_account_nickname_ed.getText().toString())) {
                        ToastUtil.setToast(WithDrawAtivity.this, "户主姓名不能为空");
                        return;
                    }
                    RequestParams params = new RequestParams();
                    params.put("userId", SugoodApplication.user.getUserId());
                    params.put("bankName", mChoice.getText().toString());
                    params.put("bankNum", with_draw_account_name_ed.getText().toString());
                    params.put("money", with_draw_account_price_ed.getText().toString());
                    params.put("bankRealname", with_draw_account_nickname_ed.getText().toString());

                    String url = "";

                    HttpUtil.post(Constant.TIXIAN_URL, params, new JsonHttpResponseHandler() {

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            Log.e(TAG, "onFailure: " + responseString);
                            ToastUtil.setToast(WithDrawAtivity.this, "提现失败");
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            Log.e(TAG, "onSuccess: " + response.toString());
                            SugoodApplication.user.setMoney((Integer.parseInt(SugoodApplication.user.getMoney()) - (Integer.parseInt(with_draw_account_price_ed.getText().toString())) * 100) + "");
                            ToastUtil.setToast(WithDrawAtivity.this, "发起提现成功,请留意到账账户信息");
                            finish();
                        }
                    });

                } else {
                    ToastUtil.setToast(WithDrawAtivity.this, "余额不足，无法提现");
                }
            }
        });


    }
}
