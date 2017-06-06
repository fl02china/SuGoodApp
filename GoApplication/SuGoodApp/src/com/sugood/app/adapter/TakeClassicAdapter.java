package com.sugood.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.activity.FangDaiCalActivity;
import com.sugood.app.activity.FunActivity;
import com.sugood.app.activity.GoodFoodActivity;
import com.sugood.app.activity.H5WebActivity;
import com.sugood.app.activity.SameCityActivity;
import com.sugood.app.activity.SuperMarketMainActivity;
import com.sugood.app.activity.TakeawayMainActivity;
import com.sugood.app.activity.TakeawayMarketActivity;
import com.sugood.app.entity.ShopMain;
import com.sugood.app.global.Constant;
import com.sugood.app.util.GlideUtil;

import java.util.List;


/**
 * 足疗 按摩 适配器
 */
public class TakeClassicAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<ShopMain> dataList;

    public TakeClassicAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public TakeClassicAdapter(Context context, List<ShopMain> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    public void setDataList(List<ShopMain> dataList){
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

        HomeGVAdapter.ViewHolder holder = null;
        if(convertView==null){
            holder = new HomeGVAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_item_layout_home_gv, parent, false);
            holder.iv = (ImageView) convertView.findViewById(R.id.img_home_gv);
            holder.tv = (TextView) convertView.findViewById(R.id.tv_home_gv);
            convertView.setTag(holder);
        }else{
            holder = (HomeGVAdapter.ViewHolder) convertView.getTag();
        }
        holder.tv.setText(dataList.get(position).getCateName());
        GlideUtil.displayImage(Constant.PHOTOBASEURL+dataList.get(position).getPhoto(),holder.iv);


//        // 点击事件
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });




        return convertView;
    }

    static class ViewHolder{
        ImageView iv;
        TextView tv;
    }

}


