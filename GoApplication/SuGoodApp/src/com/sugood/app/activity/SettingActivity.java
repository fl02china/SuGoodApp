package com.sugood.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.sugood.app.R;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Package :com.sugood.app.activity
 * Description :
 * Author :Rc3
 * Created at :2017/04/19 16:20.
 */

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.setting_reset_pwd)
    Button mResetPwd;
    @BindView(R.id.logout)
    Button mLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.user_setting_header_back)
    void onback() {
        finish();
    }

    @OnClick(R.id.setting_reset_pwd)
    void reset() {
        startActivity(new Intent(SettingActivity.this, ForgetPWActivity.class));
    }

    @OnClick(R.id.logout)
    void Logout() {
        SugoodApplication.user = null;
        SugoodApplication.isLogin = false;
        Toast.makeText(this, "退出成功", Toast.LENGTH_SHORT).show();
        finish();

    }
}
