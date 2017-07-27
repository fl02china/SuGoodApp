package com.sugood.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.sugood.app.R;

/**
 * Created by Administrator on 2017/7/22 0022.
 */

public class OrderManagerActivity  extends BaseActivity {
    private TextView back;
    private TextView txWaimai;
    private TextView txTuangou;
    private TextView txShangcheng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiaty_ordermanager);

        initView();
    }

    private void initData(){

    }
    private void initView(){
        back = (TextView) findViewById(R.id.back);
        txWaimai = (TextView) findViewById(R.id.tx_waimai);
        txTuangou = (TextView) findViewById(R.id.tx_tuangou);
        txShangcheng = (TextView) findViewById(R.id.tx_shangcheng);
        back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });


        txWaimai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //    intent.setClass(MineActivity.this, UserOrderActivity.class);
                intent.setClass(OrderManagerActivity.this, OrderWaiMaiActivity.class);
                startActivity(intent);
            }
        });
        txTuangou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //    intent.setClass(MineActivity.this, UserOrderActivity.class);
                intent.setClass(OrderManagerActivity.this, OrderTuanGouActivity.class);
                startActivity(intent);

            }
        });
        txShangcheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //    intent.setClass(MineActivity.this, UserOrderActivity.class);
                intent.setClass(OrderManagerActivity.this, OrderShanChengActivity.class);
                startActivity(intent);
            }
        });
}
}
