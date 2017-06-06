package com.sugood.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sugood.app.R;

import java.util.ArrayList;

/**
 * 同城黄页listview的item中有gridview的适配器
 */
public class NearbyGVAdapter extends BaseAdapter {
	private  ArrayList<String> disType;
	private ArrayList<String> icon;
	private LayoutInflater inflater;


	public NearbyGVAdapter(Context context, ArrayList<String> icon, ArrayList<String> disType){
		this.disType=disType;
		this.icon = icon;
		inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return disType==null?0:disType.size();
	}

	@Override
	public Object getItem(int position) {
		return disType.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageViewHolder holder = null;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.layout_samecity_yellowpage_partail_layout, null);
			holder = new ImageViewHolder();
			holder.iv = (ImageView) convertView.findViewById(R.id.iv_discout_icon);
			holder.tv = (TextView) convertView.findViewById(R.id.tv_disc);
			convertView.setTag(holder);
			
		}else{
			holder = (ImageViewHolder) convertView.getTag();
		}

		setIcon(holder.iv, icon.get(position));
		holder.tv.setText(disType.get(position));
		
		return convertView;
	}
	
	static class ImageViewHolder {
		ImageView iv;
		TextView tv;
	}

	private void setIcon(ImageView iv, String icon){
		if(icon!=null && !"".equals(icon)){
			if(icon.equals("ling")){
				iv.setBackgroundResource(R.drawable.ling);
			}else if(icon.equals("zhe")){
				iv.setBackgroundResource(R.drawable.zhe);
			}else if(icon.equals("jian")){
				iv.setBackgroundResource(R.drawable.daijinquan);
			}else if(icon.equals("piao")){
				iv.setBackgroundResource(R.drawable.piao);
			}
			else if(icon.equals("shou")){
				iv.setBackgroundResource(R.drawable.shou);
			}
		}
	}
}
