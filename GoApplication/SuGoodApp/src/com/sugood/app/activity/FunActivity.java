package com.sugood.app.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.sugood.app.R;
import com.sugood.app.adapter.ZuliaoFunAdapter;
import com.sugood.app.entity.ZuLiaoFun;

import java.util.ArrayList;

/**
 * 足疗按摩 界面
 */

public class FunActivity extends Activity {
	private static final String TAG = FunActivity.class.getSimpleName();
	private Context mContext;

	private LinearLayout ll_zu_back;
	private EditText fun_search;

	private Spinner whole_city_spinner;
	private Spinner nearby_spinner;
	private Spinner sort_spinner;
	private Spinner select_spinner;

	private ListView lv_fun;

	private ArrayList<String> listItem1 = new ArrayList<String>();
	private ArrayList<String> listItem2 = new ArrayList<String>();
	private ArrayList<String> listItem3 = new ArrayList<String>();
	private ArrayList<String> listItem4 = new ArrayList<String>();

	private ArrayList<ZuLiaoFun> dataList = new ArrayList<ZuLiaoFun>();
	private ZuliaoFunAdapter adapter = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_fun);
		mContext = FunActivity.this;
		initView();
		initDataFromNet();

		setClick();

	}

	private void initView() {
		ll_zu_back = (LinearLayout) findViewById(R.id.ll_zu_back);
		fun_search = (EditText) findViewById(R.id.fun_search);
		whole_city_spinner = (Spinner) findViewById(R.id.whole_city_spinner);
		nearby_spinner = (Spinner) findViewById(R.id.nearby_spinner);
		sort_spinner = (Spinner) findViewById(R.id.sort_spinner);
		select_spinner = (Spinner) findViewById(R.id.select_spinner);
		lv_fun = (ListView) findViewById(R.id.lv_fun);
	}

	private void initDataFromNet() {


		// 假数据
		for(int i=0; i<10; i++){
			dataList.add(new ZuLiaoFun("",
					"北京天上人间",
					"6",
					"月售124单",
					"16m",
					"36分钟",
					"起送￥30",
					"配送￥16",
					"满200减30",
					"新用户立减40元",
					"折扣商品3折起",
					"可领30元代金券",
					"支持开发票 200元起"));
		}
//		adapter = new ZuliaoFunAdapter(mContext,dataList);
		lv_fun.setAdapter(adapter);
	}


	private void setClick(){
		ll_zu_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});








	}

}
