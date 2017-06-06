package com.sugood.app.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.global.Constant;
import com.sugood.app.util.AppUtil;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.MD5Util;
import com.sugood.app.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by wilk on 17/3/21 14:53
 * ganweib@gmail.com
 * describe:
 */

public class ForgetPWActivity extends BaseActivity {

    private EditText edtPhone;
    private TextView tvGetCode;
    private EditText edtCode;
    private EditText edtPassword;
    private EditText edtPassword2;
    private Button btnRegister;
    private TimeCount timeCount;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpw);

        initView();
        timeCount = new TimeCount(60000, 1000);

    }

    private void getMsgCode(String phone) {

        RequestParams params = new RequestParams();
        params.put("mobile", phone);

        HttpUtil.post(Constant.GETCODE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                ToastUtil.setToast(ForgetPWActivity.this, "验证码发送成功，请留意短信通知");
                timeCount = new TimeCount(60000, 1000);
                timeCount.start();
                tvGetCode.setClickable(false);
                tvGetCode.setBackground(ContextCompat.getDrawable(ForgetPWActivity.this, R.drawable.shape_bar_lc));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });


    }

    private void initNetData() {


    }

    private void initView() {
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        tvGetCode = (TextView) findViewById(R.id.tv_getCode);
        edtCode = (EditText) findViewById(R.id.edt_code);
        edtPassword = (EditText) findViewById(R.id.edt_pw1);
        edtPassword2 = (EditText) findViewById(R.id.edt_pw2);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.isMobileNO(edtPhone.getText().toString())) {
                    getMsgCode(edtPhone.getText().toString());
                } else {
                    ToastUtil.setToast(ForgetPWActivity.this, "请输入正确的手机号码");
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtCode.getText().toString())) {
                    ToastUtil.setToast(ForgetPWActivity.this, "验证码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                    ToastUtil.setToast(ForgetPWActivity.this, "请输入密码");
                    return;
                }
                if (TextUtils.isEmpty(edtPassword2.getText().toString())) {
                    ToastUtil.setToast(ForgetPWActivity.this, "请重复输入密码");
                    return;
                }
                if (edtPassword.getText().toString().length() < 6) {
                    ToastUtil.setToast(ForgetPWActivity.this, "请输入不少于6位的密码");
                    return;
                }
                if (!edtPassword.getText().toString().equals(edtPassword2.getText().toString())) {
                    ToastUtil.setToast(ForgetPWActivity.this, "两次输入的密码不相同，请重新输入");
                    return;
                }
                findPW();
            }
        });


    }

    //找回密码
    private void findPW() {

        RequestParams params = new RequestParams();
        params.put("password", MD5Util.getMD5(edtPassword.getText().toString()));
        params.put("mobile", edtPhone.getText().toString());
        params.put("code", edtCode.getText().toString());
        HttpUtil.post(Constant.SUGOODGFINDPW, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TAG", "onSuccess: " + response.toString());
                try {
                    if (!response.getBoolean("success")) {
                        ToastUtil.setToast(ForgetPWActivity.this, response.getString("message"));
                    } else {
                        ToastUtil.setToast(ForgetPWActivity.this, "修改成功");
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                ToastUtil.setToast(ForgetPWActivity.this, "修改失败，请重新提交");
            }
        });


    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            tvGetCode.setText("获取验证码");
            tvGetCode.setClickable(true);
            tvGetCode.setBackground(ContextCompat.getDrawable(ForgetPWActivity.this, R.drawable.shape_on));

        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            tvGetCode.setClickable(false);//防止重复点击
            tvGetCode.setText("剩余" + millisUntilFinished / 1000 + "s");
        }
    }

}
