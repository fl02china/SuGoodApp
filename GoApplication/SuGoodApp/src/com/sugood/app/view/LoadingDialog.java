package com.sugood.app.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sugood.app.R;


/**
 * 加载中Dialog
 *
 * @author
 */
public class LoadingDialog extends AlertDialog {

	private TextView tips_loading_msg;

	private String message = "";

	public LoadingDialog(Context context) {
		super(context);
		this.setCanceledOnTouchOutside(false);
	}

	public LoadingDialog(Context context, String message) {
		super(context);
		this.message = message;
		this.setCanceledOnTouchOutside(false);
	}

	public LoadingDialog(Context context, int theme, String message) {
		super(context, theme);
		this.message = message;
		this.setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_tips_loading);
		tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
		tips_loading_msg.setText(this.message);
	}

	public void setText(String message) {
		this.message = message;
		tips_loading_msg.setText(this.message);
		if (message.equals("") || null == message) {
			tips_loading_msg.setVisibility(View.GONE);
		} else {
			tips_loading_msg.setVisibility(View.VISIBLE);
		}
	}

	public void setText(int resId) {
		setText(getContext().getResources().getString(resId));

	}

}
