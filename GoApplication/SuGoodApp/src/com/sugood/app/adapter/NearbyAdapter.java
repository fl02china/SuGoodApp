package com.sugood.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.entity.NearbyInfo;
import com.sugood.app.view.MyGridView;

import java.util.ArrayList;


public class NearbyAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<NearbyInfo> dataList;

    
    public NearbyAdapter(Context context, ArrayList<NearbyInfo> dataList) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.dataList = dataList;
	}


	public ArrayList<NearbyInfo> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<NearbyInfo> dataList) {
		this.dataList = dataList;
	}

	@Override
	public int getCount() {
		return dataList==null?0:dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.adapter_item_nearby_layout, null, false);
			holder.ivHeadPic = (ImageView) convertView.findViewById(R.id.iv_nearby_headpic);
			holder.tvShopName = (TextView) convertView.findViewById(R.id.tv_nearby_shopname);
			holder.tvDistance = (TextView) convertView.findViewById(R.id.tv_nearby_distance);

			holder.tvTime = (TextView) convertView.findViewById(R.id.tv_nearby_time);
			holder.ratingbar = (RatingBar) convertView.findViewById(R.id.ratingbar);
			holder.tvSoldNum = (TextView) convertView.findViewById(R.id.tv_sold_num);
			holder.tvStartFrom = (TextView) convertView.findViewById(R.id.tv_deliver_start_from);
			holder.tvDelivery = (TextView) convertView.findViewById(R.id.tv_deliver);
			holder.nearby_mygv = (MyGridView) convertView.findViewById(R.id.nearby_mygv);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}

//		holder.ivHeadPic
		holder.tvShopName.setText(dataList.get(position).getShopName());
		holder.tvDistance.setText(dataList.get(position).getDistance());
		holder.tvTime.setText(dataList.get(position).getTimeToGet());
		if(dataList.get(position).getComment()!=null && !"".equals(dataList.get(position).getComment())){
			holder.ratingbar.setOnRatingBarChangeListener(new RatingBarChangeListener());
		}
		holder.tvSoldNum.setText(dataList.get(position).getSoldNum());
		holder.tvStartFrom.setText(dataList.get(position).getDeliveryStartFrom());
		holder.tvDelivery.setText(dataList.get(position).getDelivery());


		int size = 0;
		ArrayList<String> type = new ArrayList<String>();
		ArrayList<String> icon = new ArrayList<String>();
		if(dataList.get(position).getDiscoutDai()!=null){
			size++;
			type.add(dataList.get(position).getDiscoutDai());
			icon.add("ling");
		}
		if(dataList.get(position).getDiscoutZhe()!=null){
			size++;
			type.add(dataList.get(position).getDiscoutZhe());
			icon.add("zhe");
		}
		if(dataList.get(position).getDiscoutJian()!=null){
			size++;
			type.add(dataList.get(position).getDiscoutJian());
			icon.add("jian");
		}
		if(dataList.get(position).getDiscoutInvoice()!=null){
			size++;
			type.add(dataList.get(position).getDiscoutInvoice());
			icon.add("piao");
		}

		if(dataList.get(position).getDiscoutShou()!=null){
			size++;
			type.add(dataList.get(position).getDiscoutShou());
			icon.add("shou");
		}

		// 动态设置各种优惠
		if(size>0){
			NearbyGVAdapter adapter = new NearbyGVAdapter(context, icon,type);
			holder.nearby_mygv.setAdapter(adapter);
		}

		return convertView;
	}

	static class ViewHolder{
		ImageView ivHeadPic;
		TextView tvShopName;
		TextView tvDistance;
		TextView tvTime;
		TextView tvSoldNum;
		RatingBar ratingbar;
		TextView tvStartFrom;
		TextView tvDelivery;
		MyGridView nearby_mygv;
	}

	class RatingBarChangeListener implements RatingBar.OnRatingBarChangeListener{

		@Override
		public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
			System.out.println("当前分数="+rating);
		}
	}


}
