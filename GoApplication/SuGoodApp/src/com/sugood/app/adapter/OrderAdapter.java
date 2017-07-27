package com.sugood.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.sugood.app.R;
import com.sugood.app.entity.OrderBean;
import com.sugood.app.holder.OrderPayViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by fl02china on 2017/6/23.
 */

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static int TYPE_0= 0;
    public final static int TYPE_1 = 1;
    public final static int TYPE_2= 2;
    public final static int TYPE_3 = 3;
    public final static int TYPE_4= 4;
    public final static int TYPE_5 =5;
    public final static int TYPE_6= 6;
    public final static int TYPE_7= 7;
    public final static int TYPE_8 = 8;
    public final static int TYPE_9= 9;
    public final static int TYPE_10 = 10;
    public final static int TYPE_11= 11;
    public final static int TYPE_12 =12;
    public final static int TYPE_13= 13;
    public final static int TYPE_14= 14;
    OnPayClickListener onPayClickListener;
    OnCancleClickListener onCancleClickListener;
    private List<OrderBean> orderList = new ArrayList<>();
    private LayoutInflater mInflater;
    private AdapterView.OnItemClickListener onItemClickListener;


    public  OrderAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<OrderBean> list) {
        this.orderList = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // mInflater = LayoutInflater.from(viewGroup.getContext());
        System.out.println("a11111:"+viewType);
        switch (viewType){
            case TYPE_0:
                return new OrderPayViewHolder(mInflater.inflate(R.layout.item_order_paying,parent,false),0);
            case TYPE_1:
                return new OrderPayViewHolder(mInflater.inflate(R.layout.item_ordering,parent,false),1);
            case TYPE_2:
                return new OrderPayViewHolder(mInflater.inflate(R.layout.item_ordering,parent,false),2);
        }
      return  null;
    }

    @Override
    public int getItemViewType(int position){
      return   orderList.get(position).getStatus();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_0:
                OrderPayViewHolder holderpay =(OrderPayViewHolder)holder;
                holderpay.bindHolder( orderList.get(position));
                holderpay.connectclient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                holderpay.cancleorder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                holderpay.gaopay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
            case TYPE_1:
            case TYPE_2:
            case TYPE_3:
            case TYPE_4:
            case TYPE_5:
            case TYPE_6:


        }
    }


    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public int getItemCount() {
        return orderList.size();
    }



    /**
     * 支付按钮
     */
    public interface OnPayClickListener {
        void onOnClick(View view, int position);
    }
    public void setOnPayClickListener(OnPayClickListener listener) {
        this.onPayClickListener = listener;
    }

    /**
     * 取消订单按钮
     */
    public interface OnCancleClickListener {
        void onOnClick(View view, int position);
    }
    public void setOnCancleClickListener(OnCancleClickListener listener) {
        this.onCancleClickListener = listener;
    }
}
