package com.sugood.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.UserAddress;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Package :com.sugood.app.activity
 * Description :
 * Author :Rc3
 * Created at :2017/04/14 14:52.
 */

public class AddAddrActivity extends AppCompatActivity {

    private static final String TAG = AddAddrActivity.class.getSimpleName();
    @BindView(R.id.add_addr_name)
    EditText mNameEd;
    @BindView(R.id.add_addr_phone)
    EditText mPhoneEd;
    @BindView(R.id.add_addr_ad)
    EditText mAddressEd;
    @BindView(R.id.add_addr_ad_info)
    EditText mAddressInfoEd;
    @BindView(R.id.add_address_header_back)
    ImageView mBack;
    @BindView(R.id.add_addr_cb)
    CheckBox mCb;
    @BindView(R.id.add_address_text_tv)
    TextView add_address_text_tv;
    Context mContext;
    UserAddress mUserAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_addr);
        ButterKnife.bind(this);
        mContext = this;

        if ((mUserAddress = (UserAddress) getIntent().getSerializableExtra("address")) != null) {

            mNameEd.setText(mUserAddress.getXm());
            mPhoneEd.setText(mUserAddress.getTel());
            mAddressEd.setText(mUserAddress.getAreaStr());
            mAddressInfoEd.setText(mUserAddress.getInfo());
            add_address_text_tv.setText("修改地址");

        }
    }

    @OnClick(R.id.add_address_header_back)
    void onBack() {
        finish();
    }

    @OnClick(R.id.add_addr_summit)
    void onSumbit() {


        if (!TextUtils.isEmpty(mNameEd.getText().toString()) &&
                !TextUtils.isEmpty(mPhoneEd.getText().toString()) &&
                !TextUtils.isEmpty(mAddressEd.getText().toString()) &&
                !TextUtils.isEmpty(mAddressInfoEd.getText().toString()) &&
                !TextUtils.isEmpty(SugoodApplication.user.getUserId())) {

            if (mUserAddress != null) {
                //修改
                RequestParams params = new RequestParams();
                params.put("id", mUserAddress.getId());
                params.put("defaults", mCb.isChecked() ? 1 : 0);
                params.put("userId", SugoodApplication.user.getUserId());

                params.put("xm", mNameEd.getText().toString());
                params.put("tel", mPhoneEd.getText().toString());
                params.put("areaStr", mAddressEd.getText().toString());
                params.put("info", mAddressInfoEd.getText().toString());

                String url=Constant.ADD_ADDRESS_URL;

                HttpUtil.post(url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        try {
                            JSONObject json = new JSONObject(new String(responseBody));
                            boolean success = json.getBoolean("success");
                            if (success) {
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "修改失败", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
            } else {
                //新增
                RequestParams params = new RequestParams();
                params.put("defaults", mCb.isChecked() ? 1 : 0);
                params.put("userId", SugoodApplication.user.getUserId());

                params.put("xm", mNameEd.getText().toString());
                params.put("tel", mPhoneEd.getText().toString());
                params.put("areaStr", mAddressEd.getText().toString());
                params.put("info", mAddressInfoEd.getText().toString());
               // Constant.ADD_ADDRESS_URL
                String url=Constant.ADD_ADDRESS_URL;

                HttpUtil.post(url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        try {
                            JSONObject json = new JSONObject(new String(responseBody));
                            boolean success = json.getBoolean("success");
                            if (success) {
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, "提交成功", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "提交失败", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
            }

        } else {
            Toast.makeText(mContext, "不能为空", Toast.LENGTH_SHORT).show();
        }
    }
}
