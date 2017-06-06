package com.sugood.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.entity.ShopMain;

import java.util.List;

/**
 * 未使用 可修改，可删除
 */

public class FenLeiAdapter2 extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<ShopMain> shopMainList;

    //    private String [] content ={"全部","经济型酒店","钟点房","豪华酒店"};
    public FenLeiAdapter2(Context context, List<ShopMain> list) {
        this.inflater = LayoutInflater.from(context);
        this.shopMainList = list;
        Log.e("yyyy", "FenLeiAdapter2: "+shopMainList.toString() );
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return shopMainList == null ? 0 : shopMainList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return shopMainList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHoldler viewHoldler = null;
        if (convertView == null){
            viewHoldler = new ViewHoldler();
            convertView = inflater.inflate(R.layout.popupwindow_item, null, false);
            viewHoldler.popItem = (TextView)convertView.findViewById(R.id.popup_item);
            convertView.setTag(viewHoldler);
        }else {
            viewHoldler = (ViewHoldler) convertView.getTag();
        }
        viewHoldler.popItem.setText(shopMainList.get(position).getCateName());
        return convertView;
    }

    class ViewHoldler {

        TextView popItem;

    }

}
