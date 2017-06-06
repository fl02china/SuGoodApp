package com.sugood.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
 * Package :com.android.supermarket.user
 * Description :
 * Author :Rc3
 * Created at :2017/3/8 17:08.
 */

public class UserNameActivity extends BaseActivity {
    Context mContext;
    EditText edtNickName;
    TextView tvOk;
    Button btnClear;
    ImageView user_name_header_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_name_activity);
        mContext = this;
        edtNickName = (EditText) findViewById(R.id.edt_nickName);
        tvOk = (TextView) findViewById(R.id.user_name_ok_btn);
        btnClear = (Button) findViewById(R.id.btn_clear);
        user_name_header_back = (ImageView) findViewById(R.id.user_name_header_back);

        user_name_header_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtNickName.setText("");
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changName();
            }
        });
        edtNickName.setHint(SugoodApplication.user.getNickname());


    }

    private void changName() {

        if (!TextUtils.isEmpty(edtNickName.getText().toString())) {

            RequestParams params = new RequestParams();
            params.put("userId", SugoodApplication.user.getUserId());
            params.put("nickname", edtNickName.getText().toString());
            HttpUtil.post(Constant.CHANGENICKNAME, params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Log.e("sasda", "onSuccess: " + response.toString());
                    Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
                    SugoodApplication.user.setNickname(edtNickName.getText().toString());
                    finish();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        } else {
            ToastUtil.setToast(this, "名字不能为空");
        }

    }
}
