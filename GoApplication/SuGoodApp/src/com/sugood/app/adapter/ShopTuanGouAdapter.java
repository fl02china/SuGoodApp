package com.sugood.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sugood.app.R;
import com.sugood.app.entity.TuanGou;
import com.sugood.app.global.Constant;

import java.util.List;

/**
 * Created by wilk on 17/3/13 17:06
 * ganweib@gmail.com
 * describe:
 */

public class ShopTuanGouAdapter extends BaseAdapter {


    private Context mContext;
    private List<TuanGou> mTuanGouList;

    public ShopTuanGouAdapter(Context mContext, List<TuanGou> mTuanGouList) {
        this.mContext = mContext;
        this.mTuanGouList = mTuanGouList;
    }

    public ShopTuanGouAdapter() {
    }

    @Override
    public int getCount() {
        return mTuanGouList != null ? mTuanGouList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mTuanGouList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tuangou, null, false);
            holder.sdvTuanGou = (SimpleDraweeView) convertView.findViewById(R.id.sdv_tuangou);
            holder.tvCount = (TextView) convertView.findViewById(R.id.tv_count);
            holder.tvOpen = (TextView) convertView.findViewById(R.id.tv_order);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.sdvTuanGou.setImageURI(Constant.PHOTOBASEURL + mTuanGouList.get(position).getPhoto());
        holder.tvCount.setText("已售" + mTuanGouList.get(position).getSoldNum());
        holder.tvTime.setText(mTuanGouList.get(position).getBgDate().replace("00:00:00", "") + "-" + mTuanGouList.get(position).getEndDate().replace("00:00:00", ""));
        holder.tvPrice.setText("¥" + mTuanGouList.get(position).getTuanPrice());
        if ("0".equals(mTuanGouList.get(position).getUseIntegral())) {
            holder.tvOpen.setText("免预约");
        } else {
            holder.tvOpen.setText("提前预约");
        }
        holder.tvTitle.setText(mTuanGouList.get(position).getTitle());

        return convertView;
    }

    static class ViewHolder {

        SimpleDraweeView sdvTuanGou;
        TextView tvTitle;
        TextView tvTime;
        TextView tvOpen;
        TextView tvPrice;
        TextView tvCount;

    }
}
