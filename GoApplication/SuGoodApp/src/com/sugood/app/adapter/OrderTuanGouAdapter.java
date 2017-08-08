package com.sugood.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sugood.app.R;
import com.sugood.app.entity.OrderShangCheng;
import com.sugood.app.entity.OrderTuanGou;
import com.sugood.app.global.Constant;
import com.sugood.app.util.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/3.
 */

public class OrderTuanGouAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<OrderTuanGou> orderList;
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
    OrderShangchengAdapter.OnRightClickListener onRightClickListener;
    OrderShangchengAdapter.OnLeftClickListener onLeftClickListener;
    OrderShangchengAdapter.OMiddleClickListener OMiddleClickListener;
    public OrderTuanGouAdapter(Context context) {
        this.context = context;


    }
    public void setData(List<OrderTuanGou> list) {
        this.orderList = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ordertg_using, parent, false);

        return new OrderTuanGouAdapter.OrderTGViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)){
            case TYPE_1:
                OrderTuanGouAdapter.OrderTGViewHolder holderpay = (OrderTuanGouAdapter.OrderTGViewHolder) holder;
                holderpay.bindHolder(orderList.get(position));
                holderpay.txLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onLeftClickListener.onOnClick(v, position);
                    }
                });
                holderpay.txRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onRightClickListener.onOnClick(v, position);
                    }
                });
                break;
            case TYPE_8:
                OrderTuanGouAdapter.OrderTGViewHolder holderpay2 = (OrderTuanGouAdapter.OrderTGViewHolder) holder;
                holderpay2.bindHolder(orderList.get(position));
                holderpay2.txRight.setBackgroundResource(R.drawable.bg_scpingjia);
                holderpay2.txRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onRightClickListener.onOnClick(v, position);
                    }
                });
                holderpay2.txLeft.setBackgroundResource(R.drawable.bg_use_finish);
                break;
            case TYPE_4:
                OrderTuanGouAdapter.OrderTGViewHolder holderpay3 = (OrderTuanGouAdapter.OrderTGViewHolder) holder;
                holderpay3.bindHolder(orderList.get(position));
                holderpay3.txLeft.setVisibility(View.GONE);
                holderpay3.txRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onRightClickListener.onOnClick(v, position);
                    }
                });
                holderpay3.txRight.setBackgroundResource(R.drawable.bg_cancle);
                break;
            case TYPE_14:
                OrderTuanGouAdapter.OrderTGViewHolder holderpay4 = (OrderTuanGouAdapter.OrderTGViewHolder) holder;
                holderpay4.bindHolder(orderList.get(position));
                holderpay4.txRight.setBackgroundResource(R.drawable.bg_use_finish);

                holderpay4.txLeft.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    public int getItemCount() {
        if (orderList!=null){

       return orderList.size();}else

        return 0;
    }
    @Override
    public int getItemViewType(int position) {
        return orderList.get(position).getStatus();
    }

    static class OrderTGViewHolder  extends RecyclerView.ViewHolder{
        @BindView(R.id.img)
        SimpleDraweeView img;

        @BindView(R.id.tuangounum)
        TextView tuangounum;
        @BindView(R.id.ordertime)
        TextView ordertime;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.textView5)
        TextView textView5;
        @BindView(R.id.usetime)
        TextView usetime;

        @BindView(R.id.num)
        TextView num;
        @BindView(R.id.tolpay)
        TextView tolpay;
        @BindView(R.id.tx_left)
        TextView txLeft;
        @BindView(R.id.tx_right)
        TextView txRight;


        OrderTGViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        public void bindHolder(OrderTuanGou order){

            ordertime.setText(DateUtil.timeStamp2Date(order.getCreateTime()+"", "yyyy/MM/dd HH:mm"));
            usetime.setText(order.getFailDate());
            name.setText(order.getTitle());
            img.setImageURI(Constant.PHOTOBASEURL+order.getPhoto());
            tuangounum.setText(order.getCode()+"");
//            name.setText(order.getShopName());
        }
    }



    public interface OMiddleClickListener {
        void onOnClick(View view, int position);
    }

    public void setOnMiddleClickListener(OrderShangchengAdapter.OMiddleClickListener listener) {
        this.OMiddleClickListener = listener;
    }

    public interface OnRightClickListener {
        void onOnClick(View view, int position);
    }

    public void setOnRightClickListener(OrderShangchengAdapter.OnRightClickListener listener) {
        this.onRightClickListener = listener;
    }

    /**
     * 取消订单按钮
     */
    public interface OnLeftClickListener {
        void onOnClick(View view, int position);
    }

    public void setOnLeftClickListener(OrderShangchengAdapter.OnLeftClickListener listener) {
        this.onLeftClickListener = listener;
    }
}
