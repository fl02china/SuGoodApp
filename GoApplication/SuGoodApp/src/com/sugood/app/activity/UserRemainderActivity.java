package com.sugood.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.application.SugoodApplication;


/**
 * Package :com.android.supermarket.user
 * Description :
 * Author :Rc3
 * Created at :2017/3/10 18:00.
 */

public class UserRemainderActivity extends BaseActivity {

    TextView user_remainder_price;
    ImageView user_remainder_header_back;
    RelativeLayout mWithDraw;
    ImageView mBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_remainder_activity);
        user_remainder_price = (TextView) findViewById(R.id.user_remainder_price);
        user_remainder_header_back = (ImageView) findViewById(R.id.user_remainder_header_back);
        user_remainder_header_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWithDraw = (RelativeLayout) findViewById(R.id.with_draw_rl);
        mWithDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserRemainderActivity.this, WithDrawAtivity.class));
            }
        });
        mBack = (ImageView) findViewById(R.id.user_remainder_header_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        mWithDraw = (RelativeLayout) findViewById(R.id.with_draw_rl);
//        mWithDraw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(UserRemainderActivity.this, WithDrawAtivity.class));
//            }
//        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        user_remainder_price.setText(Double.parseDouble(SugoodApplication.user.getMoney()) / 100 + "元");
    }
}
