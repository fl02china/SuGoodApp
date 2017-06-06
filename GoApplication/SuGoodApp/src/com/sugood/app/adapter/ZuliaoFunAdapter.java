package com.sugood.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sugood.app.R;
import com.sugood.app.entity.Food;
import com.sugood.app.entity.ZuLiaoFun;
import com.sugood.app.global.Constant;
import com.sugood.app.view.MyGridView;

import java.util.ArrayList;

/**
 * 足疗 按摩 适配器
 */
public class ZuliaoFunAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Food> dataList;


    public ZuliaoFunAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);

    }

    public void setData(ArrayList<Food> dataList) {
        this.dataList = dataList;
    }


    public ArrayList<Food> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<Food> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_item_zuliao_layout, null, false);
            holder.rl_shop = (RelativeLayout) convertView.findViewById(R.id.rl_shop);
            holder.ivHeadPic = (SimpleDraweeView) convertView.findViewById(R.id.iv_nearby_headpic);
            holder.tvShopName = (TextView) convertView.findViewById(R.id.tv_nearby_shopname);
            holder.tvDistance = (TextView) convertView.findViewById(R.id.tv_nearby_distance);

            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_nearby_time);
            holder.ratingbar = (RatingBar) convertView.findViewById(R.id.ratingbar);
            holder.tvSoldNum = (TextView) convertView.findViewById(R.id.tv_sold_num);
            holder.tvStartFrom = (TextView) convertView.findViewById(R.id.tv_deliver_start_from);
            holder.tvDelivery = (TextView) convertView.findViewById(R.id.tv_deliver);
            holder.nearby_mygv = (MyGridView) convertView.findViewById(R.id.nearby_mygv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Food food = dataList.get(position);
        holder.ivHeadPic.setImageURI(Constant.PHOTOBASEURL + food.getLogo());
        holder.rl_shop.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        holder.tvShopName.setText(food.getShopName());
        holder.tvDistance.setText(food.getMin() + "m");
        holder.tvTime.setText(food.getDistribution() + "分钟");
        holder.ratingbar.setRating(food.getScore());
        holder.tvDelivery.setText("配送¥" + food.getLogistics());
        holder.tvSoldNum.setText("月售" + food.getSoldNum() + "单");
        holder.tvStartFrom.setText("起送¥" + food.getSinceMoney());

        int size = 0;
        ArrayList<String> type = new ArrayList<String>();
        ArrayList<String> icon = new ArrayList<String>();
        if (food.getIsNew() == 1) {
            size++;
            type.add("新用户立减" + food.getNewMoney() + "元,首次使用银行卡支付再减3元");
            icon.add("ling");
        }
        if (food.getIsFan() == 1) {
            size++;
            type.add(food.getFanMoney());
            icon.add("zhe");
        }
        if (food.getIsPei() == 1) {
            size++;
            type.add("满" + food.getMinAmount() + "减" + food.getAmount());
            icon.add("jian");
        }
        // 动态设置各种优惠
        if (size > 0) {
            NearbyGVAdapter adapter = new NearbyGVAdapter(context, icon, type);
            holder.nearby_mygv.setAdapter(adapter);
        }

        return convertView;
    }

    static class ViewHolder {
        SimpleDraweeView ivHeadPic;
        TextView tvShopName;
        TextView tvDistance;
        TextView tvTime;
        TextView tvSoldNum;
        RatingBar ratingbar;
        TextView tvStartFrom;
        TextView tvDelivery;
        MyGridView nearby_mygv;
        RelativeLayout rl_shop;
    }

}
