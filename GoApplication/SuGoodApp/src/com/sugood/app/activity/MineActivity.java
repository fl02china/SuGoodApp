package com.sugood.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.global.Constant;
import com.sugood.app.util.GlideUtil;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 我的 页面
 *
 * @author wtb
 */
public class MineActivity extends AppCompatActivity {

    private static final String TAG = MineActivity.class.getSimpleName();
    private Context mContext;

    private TextView tv_username;
    private RelativeLayout rel_personal_info;
    //	private RelativeLayout rl_user_pic;
    private CircleImageView mPic;
    private RelativeLayout rel_my_balance;
    private RelativeLayout rel_my_coupon;
    private RelativeLayout rel_member_level;


    private RelativeLayout iv_commet_manage;
    private RelativeLayout rel_member_center;
    private RelativeLayout rel_service_center;

    private SugoodApplication sugoodApplication;
    private long exitTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        mContext = MineActivity.this;
        sugoodApplication = SugoodApplication.getInstance();
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initDataFromNet();
        setClickListener();
    }

    private void initView() {
        tv_username = (TextView) findViewById(R.id.tv_username);
        rel_personal_info = (RelativeLayout) findViewById(R.id.rel_personal_info);
//		rl_user_pic = (RelativeLayout) findViewById(R.id.rl_user_pic);
        mPic = (CircleImageView) findViewById(R.id.iv_user_head_pic);
        rel_my_balance = (RelativeLayout) findViewById(R.id.rel_my_balance);
        rel_my_coupon = (RelativeLayout) findViewById(R.id.rel_my_coupon);
        rel_member_level = (RelativeLayout) findViewById(R.id.rel_member_level);

        iv_commet_manage = (RelativeLayout) findViewById(R.id.rel_commet_manage);
        rel_member_center = (RelativeLayout) findViewById(R.id.rel_member_center);
        rel_service_center = (RelativeLayout) findViewById(R.id.rel_service_center);

    }

    private void initDataFromNet() {
        Log.i(TAG, "initDataFromNet: ");
        if (SugoodApplication.user != null) {

            tv_username.setText(SugoodApplication.user.getNickname());
            GlideUtil.displayImage(Constant.PHOTOBASEURL + SugoodApplication.user.getFace(), mPic);

        } else {
            tv_username.setText("请登录");
            mPic.setImageResource(R.drawable.avatar);
        }
    }

    private void setClickListener() {

        rel_personal_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sugoodApplication.isLogin) {
                    Intent intent = new Intent();
                    intent.setClass(MineActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(MineActivity.this, UserActivity.class);
                    startActivityForResult(intent, 111);
                }

            }
        });

//		rel_personal_info.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(mContext, "换头像", Toast.LENGTH_SHORT).show();
//				Intent intent = new Intent();
//				intent.setClass(MineActivity.this,UserActivity.class);
//				startActivity(intent);
//			}
//		});

        rel_my_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SugoodApplication.isLogin) {
                    //Toast.makeText(mContext, "订单", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                //    intent.setClass(MineActivity.this, UserOrderActivity.class);
                    intent.setClass(MineActivity.this, OrderManagerActivity.class);
                    startActivity(intent);
                } else {
//                    Intent intent = new Intent();
//                    intent.setClass(MineActivity.this, LoginActivity.class);
//                    startActivity(intent);
                    ToastUtil.setToast(MineActivity.this, "请先登录");
                }
            }
        });

        rel_my_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(mContext, "外卖状态", Toast.LENGTH_SHORT).show();
                if (SugoodApplication.isLogin) {
                    Intent intent = new Intent();
                    intent.setClass(MineActivity.this, UserTakeawayActivity.class);
                    startActivity(intent);
                } else {
//
                    ToastUtil.setToast(MineActivity.this, "请先登录");
                }

            }
        });

        rel_member_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SugoodApplication.isLogin) {
//                    Toast.makeText(mContext, "收藏", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(MineActivity.this, UserCollectionActivity.class);
                    startActivity(intent);
                } else {
//
                    ToastUtil.setToast(MineActivity.this, "请先登录");
                }
            }
        });

        iv_commet_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SugoodApplication.isLogin) {
                    Intent intent = new Intent();
                    intent.setClass(MineActivity.this, NotificationActivity.class);
                    startActivity(intent);
                } else {
//
                    ToastUtil.setToast(MineActivity.this, "请先登录");
                }
            }
        });

        rel_member_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SugoodApplication.isLogin) {
//                    Toast.makeText(mContext, "余额", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(MineActivity.this, UserRemainderActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtil.setToast(MineActivity.this, "请先登录");
                }
            }
        });

        rel_service_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SugoodApplication.isLogin) {
                    Intent intent = new Intent();
                    intent.setClass(MineActivity.this, SettingActivity.class);
                    startActivityForResult(intent, 111);
                } else {
//
                    ToastUtil.setToast(MineActivity.this, "请先登录");
                }

            }
        });

    }

    public void onLogin(View btn) {
        //跳转到登录页面
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: ");
        if (resultCode == RESULT_OK) {
            if (data != null) {
                tv_username.setText(data.getStringExtra("nickname"));
                GlideUtil.displayImage(Constant.PHOTOBASEURL + data.getStringExtra("pic"), mPic);
            }
        }
        if (requestCode == 111) {
            initDataFromNet();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }

    }

}
