package com.sugood.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.entity.MeiShiLeiXing;

import java.util.List;

/**
 * Created by wilk on 17/4/17 10:10
 * ganweib@gmail.com
 * describe:
 */

public class ClaassicAdapter extends BaseAdapter {

    private List<MeiShiLeiXing> meiShiLeiXingList;
    private Context mContext;

    public void setData(List<MeiShiLeiXing> meiShiLeiXingList) {

        this.meiShiLeiXingList = meiShiLeiXingList;
    }

    public ClaassicAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return meiShiLeiXingList == null ? 0 : meiShiLeiXingList.size();
    }

    @Override
    public Object getItem(int position) {
        return meiShiLeiXingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_classic, null, false);
            viewHolder.item_classic = (TextView) convertView.findViewById(R.id.item_classicfd);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.item_classic.setText(meiShiLeiXingList.get(position).getCateName());
        return convertView;
    }

    class ViewHolder {
        TextView item_classic;
    }


}
