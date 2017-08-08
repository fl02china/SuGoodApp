package com.sugood.app.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.sugood.app.R;
import com.sugood.app.entity.OrderBean;
import com.sugood.app.holder.OrderPayViewHolder;
import com.sugood.app.holder.OrderingViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by fl02china on 2017/6/23.
 */

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static int TYPE_0 = 0;
    public final static int TYPE_1 = 1;
    public final static int TYPE_2 = 2;
    public final static int TYPE_3 = 3;
    public final static int TYPE_4 = 4;
    public final static int TYPE_5 = 5;
    public final static int TYPE_6 = 6;
    public final static int TYPE_7 = 7;
    public final static int TYPE_8 = 8;
    public final static int TYPE_9 = 9;
    public final static int TYPE_10 = 10;
    public final static int TYPE_11 = 11;
    public final static int TYPE_12 = 12;
    public final static int TYPE_13 = 13;
    public final static int TYPE_14 = 14;
    public final static int TYPE_15 = 15;
    OnRightClickListener onRightClickListener;
    OnLeftClickListener onLeftClickListener;
    OnConnectClickListener onConnectClickListener;
    private List<OrderBean> orderList = new ArrayList<>();
    private LayoutInflater mInflater;
    private AdapterView.OnItemClickListener onItemClickListener;

    private Context mContext;

    public OrderAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setData(List<OrderBean> list) {
        this.orderList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // mInflater = LayoutInflater.from(viewGroup.getContext());
        System.out.println("a11111:" + viewType);
        switch (viewType) {
            case TYPE_0:
            case TYPE_1:
                return new OrderPayViewHolder(mInflater.inflate(R.layout.item_order_paying, parent, false), 0);

            //   return new OrderPayViewHolder(mInflater.inflate(R.layout.item_order_paying,parent,false),1);
            case TYPE_2:
                return new OrderingViewHolder(mInflater.inflate(R.layout.item_ordering, parent, false), 2);
            case TYPE_3:
                return new OrderPayViewHolder(mInflater.inflate(R.layout.item_order_paying, parent, false), 3);
            case TYPE_4:
                return new OrderPayViewHolder(mInflater.inflate(R.layout.item_order_paying, parent, false), 2);
            case TYPE_5:
                return new OrderPayViewHolder(mInflater.inflate(R.layout.item_order_paying, parent, false), 2);
            case TYPE_6:
                return new OrderPayViewHolder(mInflater.inflate(R.layout.item_order_paying, parent, false), 2);
            case TYPE_7:
                return new OrderPayViewHolder(mInflater.inflate(R.layout.item_order_paying, parent, false), 2);
            case TYPE_8:
                return new OrderPayViewHolder(mInflater.inflate(R.layout.item_order_paying, parent, false), 2);
            case TYPE_9:
                return new OrderingViewHolder(mInflater.inflate(R.layout.item_ordering, parent, false), 2);
            case TYPE_10:
                return new OrderPayViewHolder(mInflater.inflate(R.layout.item_order_paying, parent, false), 2);
            case TYPE_11:
                return new OrderPayViewHolder(mInflater.inflate(R.layout.item_order_paying, parent, false), 2);

            case TYPE_12:
                return new OrderPayViewHolder(mInflater.inflate(R.layout.item_order_paying, parent, false), 2);

            case TYPE_13:
                return new OrderingViewHolder(mInflater.inflate(R.layout.item_ordering, parent, false), 2);
            case TYPE_14:
                return new OrderPayViewHolder(mInflater.inflate(R.layout.item_order_paying, parent, false), 2);
            case TYPE_15:
                return new OrderPayViewHolder(mInflater.inflate(R.layout.item_order_paying, parent, false), 2);
        }
        return null;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {

            case TYPE_0:
                OrderPayViewHolder holderpay = (OrderPayViewHolder) holder;
                holderpay.bindHolder(orderList.get(position));
                holderpay.connectclient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onConnectClickListener.onOnClick(v, position);
                    }
                });
                holderpay.btn_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onLeftClickListener.onOnClick(v, position);
                    }
                });
                holderpay.btn_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onRightClickListener.onOnClick(v, position);
                    }
                });
                holderpay.btn_right.setBackgroundResource(R.drawable.bg_gopay);
                break;
            case TYPE_1:
                OrderPayViewHolder holderpay2 = (OrderPayViewHolder) holder;
                holderpay2.bindHolder(orderList.get(position));
                holderpay2.connectclient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onConnectClickListener.onOnClick(v, position);
                    }
                });
                holderpay2.btn_left.setBackgroundResource(R.drawable.bg_tuikuan);
                holderpay2.btn_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLeftClickListener.onOnClick(v, position);
                    }
                });
//                holderpay2.btn_right.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
              //  holderpay2.btn_right.setText("已支付");
                break;
            case TYPE_9:
            case TYPE_2:
            case TYPE_13:


                OrderingViewHolder holderpay3 = (OrderingViewHolder) holder;
                holderpay3.bindHolder(orderList.get(position));
                holderpay3.bindHolder(orderList.get(position));
                holderpay3.connectclient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onConnectClickListener.onOnClick(v, position);
                    }
                });
                holderpay3.btn_left.setBackgroundResource(R.drawable.bg_connectqs);
                holderpay3.btn_right.setBackgroundResource(R.drawable.bg_ing);
                holderpay3.btn_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLeftClickListener.onOnClick(v, position);
                    }
                });
                Resources resources = mContext.getResources();
                Drawable blue = resources.getDrawable(R.drawable.st_blue);
                blue.setBounds(0, 0, blue.getMinimumWidth(), blue.getMinimumHeight());
                if (getItemViewType(position) == 9) {
                    holderpay3.progress.setProgress(20);
                    holderpay3.progressT1.setCompoundDrawables(null, blue, null, null);
                } else if (getItemViewType(position) == 2) {
                    holderpay3.progress.setProgress(40);
                    holderpay3.progressT1.setCompoundDrawables(null, blue, null, null);
                    holderpay3.progressT2.setCompoundDrawables(null, blue, null, null);

                } else if (getItemViewType(position) == 13) {
                    holderpay3.progress.setProgress(60);
                    holderpay3.progressT1.setCompoundDrawables(null, blue, null, null);
                    holderpay3.progressT2.setCompoundDrawables(null, blue, null, null);
                    holderpay3.progressT3.setCompoundDrawables(null, blue, null, null);
                    holderpay3.progressT3.setCompoundDrawables(null, blue, null, null);

                }
                break;
            case TYPE_3:
            case TYPE_10:
            case TYPE_11:
            case TYPE_7:
            case TYPE_12:
            case TYPE_6:
                OrderPayViewHolder holderpay7 = (OrderPayViewHolder) holder;
                holderpay7.bindHolder(orderList.get(position));
                holderpay7.connectclient.setText("等待处理");
                holderpay7.connectclient.setBackground(null);
                holderpay7.btn_right.setBackgroundResource(R.drawable.bg_dobysj);
                holderpay7.btn_left.setVisibility(View.GONE);
                break;
            case TYPE_8:
                OrderPayViewHolder holderpay4 = (OrderPayViewHolder) holder;
                holderpay4.bindHolder(orderList.get(position));
                holderpay4.connectclient.setText("订单已完成");
                holderpay4.connectclient.setBackground(null);
                holderpay4.btn_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onLeftClickListener.onOnClick(v, position);
                    }
                });
                holderpay4.btn_left.setBackgroundResource(R.drawable.bg_other_order);
                holderpay4.btn_right.setBackgroundResource(R.drawable.bg_pinjia);
                holderpay4.btn_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onRightClickListener.onOnClick(v, position);
                    }
                });
                break;
            case TYPE_4:
                OrderPayViewHolder holderpay5= (OrderPayViewHolder) holder;
                holderpay5.bindHolder(orderList.get(position));
                holderpay5.btn_right.setBackgroundResource(R.drawable.bg_tuikuan_finish);
                holderpay5.connectclient.setText("退款完成");
                holderpay5.connectclient.setBackground(null);
                holderpay5.btn_left.setVisibility(View.GONE);
                break;
            case TYPE_5:
                OrderPayViewHolder holderpay6 = (OrderPayViewHolder) holder;
                holderpay6.bindHolder(orderList.get(position));
                holderpay6.connectclient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onConnectClickListener.onOnClick(v, position);
                    }
                });
                holderpay6.btn_right.setBackgroundResource(R.drawable.bg_tuikuanfail);
                holderpay6.btn_left.setVisibility(View.GONE);
                break;
            case TYPE_15:
                OrderPayViewHolder holderpay8 = (OrderPayViewHolder) holder;
                holderpay8.bindHolder(orderList.get(position));
                holderpay8.connectclient.setBackground(null);
                holderpay8.connectclient.setText("订单已取消");
                holderpay8.btn_right.setBackgroundResource(R.drawable.bg_cancle);
                holderpay8.btn_left.setVisibility(View.GONE);
                break;
//            default:
//            OrderPayViewHolder holderpay9 = (OrderPayViewHolder) holder;
//            holderpay9.bindHolder(orderList.get(position));
//                holderpay9.connectclient.setBackground(null);
//                holderpay9.connectclient.setText("订单状态："+getItemViewType(position));
//                break;

        }
    }


    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return orderList.get(position).getStatus();
    }
    @Override
    public int getItemCount() {
        if (orderList!=null){
            return orderList.size();
        }else{
            return 0;
        }

    }



    public interface OnConnectClickListener {
        void onOnClick(View view, int position);
    }

    public void setOnConnectClickListener(OnConnectClickListener listener) {
        this.onConnectClickListener = listener;
    }

    public interface OnRightClickListener {
        void onOnClick(View view, int position);
    }

    public void setOnRightClickListener(OnRightClickListener listener) {
        this.onRightClickListener = listener;
    }

    /**
     * 取消订单按钮
     */
    public interface OnLeftClickListener {
        void onOnClick(View view, int position);
    }

    public void setOnLeftClickListener(OnLeftClickListener listener) {
        this.onLeftClickListener = listener;
    }
}
