package com.sugood.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.entity.MeiShiLeiXing;
import com.sugood.app.entity.ShangQuan;

import java.util.List;

/**
 * Created by wilk on 17/4/17 10:10
 * ganweib@gmail.com
 * describe:
 */

public class SXClassicAdapter extends BaseAdapter {

    private List<ShangQuan> shangQuanList;
    private Context mContext;

    public void setData(List<ShangQuan> shangQuanList) {

        this.shangQuanList = shangQuanList;
    }

    public SXClassicAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return shangQuanList == null ? 0 : shangQuanList.size();
    }

    @Override
    public Object getItem(int position) {
        return shangQuanList.get(position);
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
        viewHolder.item_classic.setText(shangQuanList.get(position).getBusinessName());
        return convertView;
    }

    class ViewHolder {
        TextView item_classic;
    }


}
