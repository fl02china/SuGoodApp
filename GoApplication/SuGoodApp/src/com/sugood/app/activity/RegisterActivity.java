package com.sugood.app.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.sugood.app.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by wilk on 17/3/15 14:32
 * ganweib@gmail.com
 * describe:
 */

public class RegisterActivity extends BaseActivity {


    private EditText edtPhone;
    private TextView tvGetCode;
    private EditText edtCode;
    private EditText edtPassword;
    private EditText edtPassword2;
    private Button btnRegister;
    private TimeCount timeCount;
    private ImageView iv_back;
    private CheckBox checkbox;
    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        timeCount = new TimeCount(60000, 1000);
//        getMsgCode();
        initNetData();
    }

    private void getMsgCode(String phone) {

        RequestParams params = new RequestParams();
        params.put("mobile", phone);
        Log.e(TAG, "address: "+Constant.GETCODE);
        HttpUtil.post(Constant.GETCODE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "address2: "+response.toString());
                try {
                    if (!response.getString("session").equals("000")){
                        ToastUtil.setToast(RegisterActivity.this, "短信验证失败");
                    }else{
                        ToastUtil.setToast(RegisterActivity.this, "验证码发送成功，请留意短信通知");
                        timeCount = new TimeCount(60000, 1000);
                        timeCount.start();
                        tvGetCode.setClickable(false);
                        tvGetCode.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.shape_bar_lc));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                try {
//                    if (!response.getBoolean("success")) {
//                        ToastUtil.setToast(RegisterActivity.this, response.toString());
//                    } else {
//
//                        ToastUtil.setToast(RegisterActivity.this, "验证码发送成功，请留意短信通知");
//                        timeCount = new TimeCount(60000, 1000);
//                        timeCount.start();
//                        tvGetCode.setClickable(false);
//                        tvGetCode.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.shape_bar_lc));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.e(TAG, "22111111onFailure: "+errorResponse.toString());
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFinish() {
                Log.e(TAG, "finish ");
                super.onFinish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, "111111onFailure: "+responseString);
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });


    }

    private void initNetData() {


    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        tvGetCode = (TextView) findViewById(R.id.tv_getCode);
        edtCode = (EditText) findViewById(R.id.edt_code);
        edtPassword = (EditText) findViewById(R.id.edt_pw1);
        edtPassword2 = (EditText) findViewById(R.id.edt_pw2);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        iv_back.setOnClickListener(new View.OnClickListener() {
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
                    ToastUtil.setToast(RegisterActivity.this, "请输入正确的手机号码");
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!Utils.isNetworkAvailable(RegisterActivity.this)) {
//                    ToastUtil.setToast(RegisterActivity.this, "网络请求失败，请检查您的网络");
//                    return;
//                }
                if (registerCheck()) return;
                register();
//                if (Utils.isNetworkAvailable(RegisterActivity.this)) {
//                    register();
//                } else{
//                    ToastUtil.setToast(RegisterActivity.this, "没有网络");
//                    return;
//                }

            }
        });


    }

    private boolean registerCheck() {
        if (!checkbox.isChecked()) {
            ToastUtil.setToast(RegisterActivity.this, "请确认选择用户协议");
            return true;
        }
        if (TextUtils.isEmpty(edtCode.getText().toString())) {
            ToastUtil.setToast(RegisterActivity.this, "验证码不能为空");
            return true;
        }
        if (TextUtils.isEmpty(edtPassword.getText().toString())) {
            ToastUtil.setToast(RegisterActivity.this, "请输入密码");
            return true;
        }
        if (TextUtils.isEmpty(edtPassword2.getText().toString())) {
            ToastUtil.setToast(RegisterActivity.this, "请重复输入密码");
            return true;
        }
        if (edtPassword.getText().toString().length() < 6) {
            ToastUtil.setToast(RegisterActivity.this, "请输入不少于6位的密码");
            return true;
        }
        if (!edtPassword.getText().toString().equals(edtPassword2.getText().toString())) {
            ToastUtil.setToast(RegisterActivity.this, "两次输入的密码不相同，请重新输入");
            return true;
        }
        return false;
    }

    //注册
    private void register() {

        RequestParams params = new RequestParams();
        String password =MD5Util.getMD5(edtPassword.getText().toString());
        String mobile =edtPhone.getText().toString();
        String code =edtCode.getText().toString();
//        String password =MD5Util.getMD5("123456");
//        String mobile ="13620906082";
//        String code ="123456";
        params.put("password", password);
        params.put("mobile", mobile);
        params.put("code", code);
        HttpUtil.post(Constant.SUGOODREGISTER, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("sadas", "onSuccess: " + response.toString());
                try {
                    if (!response.getBoolean("success")) {
                        ToastUtil.setToast(RegisterActivity.this, response.getString("message").toString());
                    } else {
                        ToastUtil.setToast(RegisterActivity.this, "注册成功");
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
              //  finish();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("sadas", "onFailure: " + "statusCode:"+statusCode+"  :"+responseString);
                ToastUtil.setToast(RegisterActivity.this, "注册失败，请重新注册");
                //  finish();
            }

            @Override
            public void onCancel() {
                Log.e("sadas", "onCancel " );
                super.onCancel();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("sadas", "onFailure2: " + "statusCode:"+statusCode+"  :"+errorResponse);
                ToastUtil.setToast(RegisterActivity.this, "注册失败，请检查网络");
                super.onFailure(statusCode, headers, throwable, errorResponse);
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
            tvGetCode.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.shape_on));

        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            tvGetCode.setClickable(false);//防止重复点击
            tvGetCode.setText("剩余" + millisUntilFinished / 1000 + "s");
        }
    }


}
