package com.sugood.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.activity.SameCityDetailActivity;
import com.sugood.app.entity.SameCityYellowPage;
import com.sugood.app.global.Constant;
import com.sugood.app.util.GlideUtil;
import com.sugood.app.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 同城黄页适配器
 * Created by dec on 2016/12/19.
 */

public class SameCityAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;

    private List<SameCityYellowPage> dataList;

    public SameCityAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setDataList(List<SameCityYellowPage> dataList) {
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
            convertView = inflater.inflate(R.layout.layout_samecity_adapter_item, null);
            holder.ivHead = (ImageView) convertView.findViewById(R.id.iv_shop_headpic);
            holder.tvShopName = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.ivCall = (ImageView) convertView.findViewById(R.id.iv_phone_icon);
            holder.tvTelPhone = (TextView) convertView.findViewById(R.id.tv_shop_telphone);
            holder.tvAddr = (TextView) convertView.findViewById(R.id.tv_shop_addr);
            holder.tvDistance = (TextView) convertView.findViewById(R.id.tv_shop_distance);
            holder.gv = (MyGridView) convertView.findViewById(R.id.gv_distcout);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        // 设值
//        holder.ivHead
        holder.tvShopName.setText(dataList.get(position).getName());
        holder.tvTelPhone.setText(dataList.get(position).getTelephone());
        holder.tvAddr.setText(dataList.get(position).getAddress());
        holder.tvDistance.setText(dataList.get(position).getMi());
        GlideUtil.displayImage(Constant.PHOTOBASEURL + dataList.get(position).getPhoto(), holder.ivHead);

        holder.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = dataList.get(position).getTelephone();
                if (phone != null && !"".equals(phone) && !"暂无".equals(phone) && !"没有".equals(phone)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + phone);
                    intent.setData(data);
                    context.startActivity(intent);
                }
            }
        });

//
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//				context.startActivity(new Intent(context, SameCityDetailActivity.class));
//            }
//        });

        return convertView;
    }

    private LinearLayout createImageLayout(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }


    static class ViewHolder {
        ImageView ivHead;
        TextView tvShopName;
        ImageView ivCall;
        TextView tvTelPhone;
        TextView tvDistance;
        TextView tvAddr;
        MyGridView gv;
    }
}
