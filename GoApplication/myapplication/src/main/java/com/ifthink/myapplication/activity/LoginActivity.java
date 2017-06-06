package com.ifthink.myapplication.activity;



import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ifthink.myapplication.R;

import util.Utils;

/**
 * Created by dec on 2017/1/4.
 */

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText etAccount, etPassword;
    private Button btnLogin;




    @Override
    public void setLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initViews() {
        mContext = LoginActivity.this;

        etAccount = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);

    }

    @Override
    public void initNetDatas() {

    }

    @Override
    public void setClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if("".equals(etAccount.getText().toString().trim())){
                    Utils.setToast(mContext, "账号不能为空");
                    return;
                }else if("".equals(etPassword.getText().toString().trim())){
                    Utils.setToast(mContext,"密码不能为空");
                    return;
                }

                startActivity(new Intent(mContext, MainActivity.class));
                finish();
            }
        });
    }


}
