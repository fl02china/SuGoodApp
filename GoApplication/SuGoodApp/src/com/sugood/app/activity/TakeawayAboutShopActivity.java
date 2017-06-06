package com.sugood.app.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.sugood.app.R;
import com.sugood.app.util.BlurBitmapUtil;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Package :com.android.supermarket.takeaway
 * Description :
 * Author :Rc3
 * Created at :2017/3/3 21:38.
 */

public class TakeawayAboutShopActivity extends BaseActivity {

    RelativeLayout mBackgroundLayout;
    Context mContext;
    CircleImageView mCircleImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takeaway_about_shop_activity);
        mContext = this;
        initView();
        initData();
    }

    private void initData() {
        //blur bg
        Bitmap bitmap = BlurBitmapUtil.getBlurBitmap(mContext, BitmapFactory.decodeResource(getResources(), R.drawable.shop_icon), 20f);
        mBackgroundLayout.setBackground(new BitmapDrawable(bitmap));
        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mBackgroundLayout = (RelativeLayout) findViewById(R.id.takeaway_about_shop_rl);
        mCircleImageView = (CircleImageView) findViewById(R.id.about_shop_close);
    }
}
