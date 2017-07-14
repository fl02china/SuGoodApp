package com.sugood.app.wxapi;


import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;


import com.sugood.app.Constants;
import com.sugood.app.R;

import com.sugood.app.util.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = ".WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {





//		Log.e("1112222info", "onPayFinish,resp.getType()=" + resp.getType());
//		Log.e("1112222info", "onPayFinish,resp.getType()=" + resp.getType());
//		Log.e("1112222info", "onPayFinish,resp.getType()=" + resp.getType());
//		Log.e("1112222info", "onPayFinish,resp.getType()=" + resp.getType());
//		Log.e("1112222info", "onPayFinish,resp.getType()=" + resp.getType());
//		Log.e("1112222info", "resp.errCode=" + resp.errCode);
//		if (resp.errCode == -2){
//			Log.e("333333333312222info", "onPayFinish,resp.getType()=" + resp.getType());
//			Toast.makeText(this, "用户取消", Toast.LENGTH_SHORT).show();
//		}
//		finish();


		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			this.finish();
			//Log.e("11111111111info", "onPayFinish,errCode=" + resp.errCode);

			if (resp.errCode == 0) {

			//	ToastUtil.setToast(this, "支付成功");
				Intent intent = new Intent("FinishActivity");
				sendBroadcast(intent);//发送对应的广播
				Intent intent2 = new Intent("ResultActivity");
				sendBroadcast(intent2);//发送对应的广播

				this.finish();
			} else if (resp.errCode == -1) {

				ToastUtil.setToast(this, "配置错误");
				this.finish();
			} else if (resp.errCode == -2) {

				ToastUtil.setToast(this, "用户取消");
				this.finish();
			}
		} else {

			ToastUtil.setToast(this, resp.errStr);

		}
	}
}