package com.sugood.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.litesuits.orm.LiteOrm;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.Ctiy;
import com.sugood.app.entity.User;
import com.sugood.app.entity.UserIDAndPW;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.util.MD5Util;
import com.sugood.app.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends BaseActivity {
    public static final String path = "http://192.168.1.66:8080/TuanShoppingServer/mypack/userAction_login";

    private EditText et_username;
    private EditText et_userpwd;
    private LiteOrm liteOrm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = (EditText) findViewById(R.id.et_username);
        et_userpwd = (EditText) findViewById(R.id.et_userpwd);
//        this.changeBack();
    }

    // 判断登录是否成功
    public void onLogin(View view) {
        if (view.getId() == R.id.btn_login) {
            Log.d("geek", "登录");
            // 得到输入编辑框的数据
            final String name = et_username.getText().toString();
            final String pwd = et_userpwd.getText().toString();
            SharedPreferences preferences = getSharedPreferences("info", MODE_PRIVATE);
            SharedPreferences.Editor sped = preferences.edit();
            sped.putString("name", name);
            sped.putString("pwd", pwd);
            sped.commit();
            Log.d("geek", "用户名：" + name + "密码：" + pwd);
            RequestParams data = new RequestParams();
            data.put("name", name);
            data.put("password", MD5Util.getMD5(pwd));

            HttpUtil.post(Constant.SUGOOGLOGIN, data, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Log.e("ss", "onSuccess: " + response.toString());

                    try {
                        if (response.getBoolean("success")) {
                            SugoodApplication.isLogin = true;
                            User user = JsonUtil.toObject(response.getString("content"), User.class);
                            SugoodApplication.user = user;
                            UserIDAndPW userIDAndPW = new UserIDAndPW();
                            userIDAndPW.setLogin(true);
                            userIDAndPW.setPassword(pwd);
                            userIDAndPW.setUserID(name);
                            SugoodApplication.liteOrm.update(userIDAndPW);
                            String msg = response.getString("message");
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
                            initNetData();
                            Intent intent = new Intent();
                            intent.putExtra("nickname", SugoodApplication.user.getNickname());
                            intent.putExtra("face", SugoodApplication.user.getFace());
                            setResult(RESULT_OK, intent);

                            closeKeyboard(et_userpwd);
                            finish();


                        } else {
                            ToastUtil.setToast(LoginActivity.this, "密码错误,请重新输入");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });


//            Log.d("geek", "data=" + data);
//            String result = "failure";
//            result = UserUtils.loginResult(path, data);
//            Log.d("geek", "返回结果：" + result);
//            if (name == null || (name == null && pwd == null)) {
//                // 弹出对话框（内容：“请输入Email或手机号”）
//                Toast.makeText(this, "请输入", Toast.LENGTH_LONG).show();
//            } else if (pwd == null) {
//                // 弹出对话框(内容："请输入密码")
//                Toast.makeText(this, "请输入", Toast.LENGTH_LONG).show();
//            } else {
//                if (result.equals("success")) {// 登录成功,跳转至
//                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(this, com.sugood.app.activity.MineActivity.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(this, "登录失败", Toast.LENGTH_LONG).show();
//                }
//            }
        }
    }

    public void onReturnmine(View btn) {
        if (btn.getId() == R.id.ib_return) {
            Intent intent = new Intent(this, com.sugood.app.activity.MineActivity.class);
            startActivity(intent);
        }
    }

    public void register(View btn) {

        if (btn.getId() == R.id.btn_register) {
            Intent intent = new Intent();
            intent.setClass(this, RegisterActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent();
            intent.setClass(this, ForgetPWActivity.class);
            startActivity(intent);
        }


    }


    //上传经纬度
    private void initNetData() {

        RequestParams params = new RequestParams();
        params.put("lat", "20.912420741522");
        params.put("lng", "110.08775659179");
        params.put("cityName", "湛江市");
        Log.e("ss", "initNetData: " + "jinru");
        HttpUtil.post(Constant.UPLOADLATLONG, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("", "onSuccess: " + response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("", "onSuccess: " + response.toString());
                List<Ctiy> list = JsonUtil.toList(response.toString(), Ctiy.class);
                Log.e("", "onSuccess: " + list.get(0).toString());
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("", "onFailure: " + responseString);
            }
        });
    }

    @Override
    public void onBackPressed() {

        finish();
        // super.onBackPressed();

    }

}
