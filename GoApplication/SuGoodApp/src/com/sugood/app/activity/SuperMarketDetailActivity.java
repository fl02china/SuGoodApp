package com.sugood.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.sugood.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Package :com.android.supermarket.supermaket
 * Description :
 * Author :Rc3
 * Created at :2017/2/27 01:14.
 */

public class SuperMarketDetailActivity extends BaseActivity{

    @BindView(R.id.market_detail_header_back)
    ImageView mBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supermarket_detial_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.market_detail_header_back)
    void onBack(){
        finish();
    }
}
