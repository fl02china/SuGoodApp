package com.sugood.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sugood.app.R;

/**
 * Package :com.android.supermarket.takeaway
 * Description :
 * Author :Rc3
 * Created at :2017/3/2 14:28.
 */

public class TakeawayFoodDetailActivity extends BaseActivity {
    Button mAddShopCarBtn;
    LinearLayout mAddShopCarLL;
    ImageView mBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takeaway_food_detail_activity);
        initView();
        initData();
    }

    private void initData() {

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.takeaway_food_detail_header_back);
      //  mAddShopCarBtn = (Button) findViewById(R.id.takeaway_food_add_shopcar_btn);
     //   mAddShopCarLL = (LinearLayout) findViewById(R.id.takeaway_food_add_shopcar_ll);
    }
}
