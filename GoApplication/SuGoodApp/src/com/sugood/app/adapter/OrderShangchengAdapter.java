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
import com.sugood.app.util.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class OrderShangchengAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<OrderShangCheng> orderList;
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
    OMiddleClickListener OMiddleClickListener;
    public OrderShangchengAdapter(Context context, List<OrderShangCheng> list) {
        this.context = context;
        this.orderList = list;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ordersc_all, parent, false);

        return new OrderShangchengAdapter.OrderSCViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)){
            case TYPE_0:
                OrderSCViewHolder holderpay = (OrderSCViewHolder) holder;
                holderpay.bindHolder(orderList.get(position));
                holderpay.txLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onLeftClickListener.onOnClick(v, position);
                    }
                });
        }

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return orderList.get(position).getStatus();
    }

    static class OrderSCViewHolder  extends RecyclerView.ViewHolder{
        @BindView(R.id.text1)
        TextView text1;
        @BindView(R.id.order_num) TextView orderNum;
        @BindView(R.id.time) TextView time;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.phone) TextView phone;
        @BindView(R.id.text2) TextView text2;
        @BindView(R.id.addr) TextView addr;
        @BindView(R.id.img)
        SimpleDraweeView img;
        @BindView(R.id.shopname) TextView shopname;
        @BindView(R.id.goodsname) TextView goodsname;
        @BindView(R.id.num) TextView num;
        @BindView(R.id.divider1) View divider1;
        @BindView(R.id.liner1)
        LinearLayout liner1;
        @BindView(R.id.textView4) TextView textView4;
        @BindView(R.id.money_tol) TextView moneyTol;
        @BindView(R.id.money_num) TextView moneyNum;
        @BindView(R.id.liner2) LinearLayout liner2;
        @BindView(R.id.tx_left) TextView txLeft;
        @BindView(R.id.tx_middle) TextView txMiddle;
        @BindView(R.id.tx_right) TextView txRight;

        OrderSCViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        public void bindHolder(OrderShangCheng order){
            time.setText(DateUtil.timeStamp2Date(order.getCreateTime()+"", "yyyy/MM/dd HH:mm"));
            orderNum.setText(order.getOrderId());
            name.setText(order.getShopName());
        }
    }



    public interface OMiddleClickListener {
        void onOnClick(View view, int position);
    }

    public void setOnMiddleClickListener(OMiddleClickListener listener) {
        this.OMiddleClickListener = listener;
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


