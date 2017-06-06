package com.sugood.app.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.sugood.app.R;


/**
 * Package :com.android.supermarket.user
 * Description :
 * Author :Rc3
 * Created at :2017/3/10 16:07.
 */

public class UserRechargeActivity extends BaseActivity {


    Button mSubmit;
    Context mContext;
    View mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_recharge_activity);
        mContext = this;
        initView();
        initData();
    }

    private void initData() {
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = LayoutInflater.from(mContext).inflate(R.layout.user_recharge_dialog, null);
                mView.setVisibility(View.VISIBLE);
                PopupWindow popup = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popup.setOutsideTouchable(true);
                popup.setBackgroundDrawable(new BitmapDrawable(getResources(), ((Bitmap) null)));
                popup.showAtLocation(mSubmit, Gravity.BOTTOM, 0, 0);
                popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mView.setVisibility(View.GONE);
                    }
                });

            }
        });
    }

    private void initView() {
        mSubmit = (Button) findViewById(R.id.user_recharge_price_submit);
        mView = findViewById(R.id.user_recharge_view);

    }
}
