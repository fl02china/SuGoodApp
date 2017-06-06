package com.sugood.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.util.ToastUtil;

/**
 * 同城黄页详情界面
 */

public class SameCityDetailActivity extends Activity {
	private static final String TAG = SameCityDetailActivity.class.getSimpleName();

	private Context mContext;
	private RelativeLayout relBack;
	private TextView tvRelease;
	private FrameLayout shareFrame;

	private ImageView ivShare;
	private ImageView ivStar;
	private ImageView ivWarn;

	private TextView tv_phone;
	private ImageView iv_phone;
	private String phoneNum;

	private ImageView iv_navigation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_same_city_detail);
		mContext = SameCityDetailActivity.this;

		initView();
		initDataFromNet();
		setClickListener();
	}

	private void initView() {
		relBack = (RelativeLayout) findViewById(R.id.rel_samecity_detail_back);
		tvRelease = (TextView) findViewById(R.id.tv_samecity_detail_release);
		shareFrame = (FrameLayout) findViewById(R.id.framelayout_samecity_detail);
		shareFrame.bringToFront();
		ivShare = (ImageView) findViewById(R.id.iv_detail_share);
		ivStar = (ImageView) findViewById(R.id.iv_detail_star);
		ivWarn = (ImageView) findViewById(R.id.iv_detail_warning);

		tv_phone = (TextView) findViewById(R.id.tv_phone);
		iv_phone = (ImageView) findViewById(R.id.iv_phone);
		iv_navigation = (ImageView) findViewById(R.id.iv_navigation);
	}

	private void initDataFromNet() {

	}

	private void setClickListener() {
		relBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		tvRelease.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ToastUtil.setToast(mContext, "发布信息页面");
			}
		});

		ivShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		ivStar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		ivWarn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		tv_phone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String phoneNum = tv_phone.getText().toString();
				if(phoneNum!=null && !"".equals(phoneNum)){
					Intent intent = new Intent();
					intent.setData(Uri.parse("tel:" + phoneNum));
					intent.setAction(Intent.ACTION_CALL);
					startActivity(intent);
				}
			}
		});

		iv_phone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String phoneNum = tv_phone.getText().toString();
				if(phoneNum!=null && !"".equals(phoneNum)){
					Intent intent = new Intent();
					intent.setData(Uri.parse("tel:" + phoneNum));
					intent.setAction(Intent.ACTION_CALL);
					startActivity(intent);
				}
			}
		});

		iv_navigation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, MapActivity.class));
			}
		});
	}

}
