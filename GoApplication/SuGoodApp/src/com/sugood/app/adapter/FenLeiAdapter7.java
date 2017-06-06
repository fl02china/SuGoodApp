package com.sugood.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sugood.app.R;

/**
 * 未使用 可修改，可删除
 */

public class FenLeiAdapter7 extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;
	private String [] content ={"全部","服装鞋帽","家居日用","食品饮料","箱包","母婴用品","化妆品","数码家电","钟表首饰","户外用品","图书音像","本地购物","其他购物"};
	public FenLeiAdapter7(Context context) {
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return content.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return content[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View layout = inflater.inflate(R.layout.adapter_item_nearby_layout, parent, false);
		return layout;
	}

}
