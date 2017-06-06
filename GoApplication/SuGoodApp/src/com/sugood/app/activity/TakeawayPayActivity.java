package com.sugood.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sugood.app.R;

/**
 * Package :com.android.supermarket.takeaway
 * Description :
 * Author :Rc3
 * Created at :2017/3/2 23:54.
 */

public class TakeawayPayActivity extends BaseActivity {


    Button mAlipayBtn;
    Button mWxPayBtn;
    Button mUnionPayBtn;

    int mSelected=-1;

    int ALIPAY=1;
    int WXPAY=2;
    int UNIONPAY=3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takeaway_pay_activity);
        initView();
        initData();
    }

    private void initData() {
        mAlipayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelected!=ALIPAY){
                    mAlipayBtn.setBackgroundResource(R.drawable.bule_yes_icon);
                    mUnionPayBtn.setBackgroundResource(R.drawable.circle_icon);
                    mWxPayBtn.setBackgroundResource(R.drawable.circle_icon);
                    mSelected=ALIPAY;
                }else{
                    mAlipayBtn.setBackgroundResource(R.drawable.circle_icon);
                    mSelected=-1;

                }
            }
        });

        mWxPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelected!=WXPAY){
                    mWxPayBtn.setBackgroundResource(R.drawable.bule_yes_icon);
                    mUnionPayBtn.setBackgroundResource(R.drawable.circle_icon);
                    mAlipayBtn.setBackgroundResource(R.drawable.circle_icon);
                    mSelected=WXPAY;
                }else{
                    mWxPayBtn.setBackgroundResource(R.drawable.circle_icon);
                    mSelected=-1;

                }
            }
        });

        mUnionPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelected!=UNIONPAY){
                    mUnionPayBtn.setBackgroundResource(R.drawable.bule_yes_icon);
                    mWxPayBtn.setBackgroundResource(R.drawable.circle_icon);
                    mAlipayBtn.setBackgroundResource(R.drawable.circle_icon);
                    mSelected=UNIONPAY;
                }else{
                    mUnionPayBtn.setBackgroundResource(R.drawable.circle_icon);
                    mSelected=-1;

                }
            }
        });
    }

    private void initView() {
        mAlipayBtn = (Button) findViewById(R.id.takeaway_pay_alipay_btn);
        mWxPayBtn = (Button) findViewById(R.id.takeaway_pay_wxpay_btn);
        mUnionPayBtn = (Button) findViewById(R.id.takeaway_pay_unionpay_btn);

    }
}
