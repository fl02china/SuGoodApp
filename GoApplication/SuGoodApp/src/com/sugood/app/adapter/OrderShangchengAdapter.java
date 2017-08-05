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
import com.sugood.app.global.Constant;
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
    public OrderShangchengAdapter(Context context) {
        this.context = context;


    }
    public void setData(List<OrderShangCheng> list) {
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
                holderpay.btn_middle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        OMiddleClickListener.onOnClick(v, position);
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
                break;
            case TYPE_1:

                OrderSCViewHolder holderpay2 = (OrderSCViewHolder) holder;
                holderpay2.bindHolder(orderList.get(position));
                holderpay2.btn_middle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        OMiddleClickListener.onOnClick(v, position);
                    }
                });
                holderpay2.btn_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onLeftClickListener.onOnClick(v, position);
                    }
                });
                holderpay2.btn_right.setBackgroundResource(R.drawable.bg_wait_sj);
                holderpay2.btn_left.setBackgroundResource(R.drawable.bg_tuikuan_order);

                break;
            case TYPE_13:

                OrderSCViewHolder holderpay3 = (OrderSCViewHolder) holder;
                holderpay3.bindHolder(orderList.get(position));
                holderpay3.btn_middle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        OMiddleClickListener.onOnClick(v, position);
                    }
                });

                holderpay3.btn_right.setBackgroundResource(R.drawable.bg_fahuoed);
                holderpay3.btn_left.setVisibility(View.GONE);

                break;
            case TYPE_8:

                OrderSCViewHolder holderpay4 = (OrderSCViewHolder) holder;
                holderpay4.bindHolder(orderList.get(position));
                holderpay4.btn_middle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        OMiddleClickListener.onOnClick(v, position);
                    }
                });
                holderpay4.btn_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onRightClickListener.onOnClick(v, position);
                    }
                });
                holderpay4.btn_right.setBackgroundResource(R.drawable.bg_scpingjia);
                holderpay4.btn_left.setVisibility(View.GONE);

                break;

            case TYPE_14:
                OrderSCViewHolder holderpay5 = (OrderSCViewHolder) holder;
                holderpay5.bindHolder(orderList.get(position));
                holderpay5.btn_right.setBackgroundResource(R.drawable.bg_finish_sj);
                holderpay5.btn_left.setVisibility(View.GONE);
                holderpay5.btn_middle.setVisibility(View.GONE);

                break;

            case TYPE_15:
                OrderSCViewHolder holderpay6 = (OrderSCViewHolder) holder;
                holderpay6.bindHolder(orderList.get(position));
                holderpay6.btn_right.setBackgroundResource(R.drawable.bg_cancle);
                holderpay6.btn_left.setVisibility(View.GONE);
                holderpay6.btn_middle.setVisibility(View.GONE);

                break;
            case TYPE_3:
            case TYPE_7:
            case TYPE_10:
            case TYPE_11:
            case TYPE_12:
                OrderSCViewHolder holderpay7 = (OrderSCViewHolder) holder;
                holderpay7.bindHolder(orderList.get(position));
                holderpay7.btn_middle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        OMiddleClickListener.onOnClick(v, position);
                    }
                });
                holderpay7.btn_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onLeftClickListener.onOnClick(v, position);
                    }
                });
                holderpay7.btn_right.setBackgroundResource(R.drawable.bg_dobysj);
                holderpay7.btn_left.setBackgroundResource(R.drawable.bg_cancle_tuikuan);
                break;
            case TYPE_4:
                OrderSCViewHolder holderpay8 = (OrderSCViewHolder) holder;
                holderpay8.bindHolder(orderList.get(position));
                holderpay8.btn_right.setBackgroundResource(R.drawable.bg_tuikuan_finish);
                holderpay8.btn_left.setVisibility(View.GONE);
                holderpay8.btn_middle.setVisibility(View.GONE);

                break;

            case TYPE_5:
                OrderSCViewHolder holderpay9 = (OrderSCViewHolder) holder;
                holderpay9.bindHolder(orderList.get(position));
                holderpay9.btn_middle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        OMiddleClickListener.onOnClick(v, position);
                    }
                });

                holderpay9.btn_right.setBackgroundResource(R.drawable.bg_sj_jujue);
                holderpay9.btn_left.setVisibility(View.GONE);

                break;

        }

    }

    @Override
    public int getItemCount() {
        if (orderList!=null){
        return orderList.size();}else{
            return 0;
        }


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
        @BindView(R.id.tx_left) TextView btn_left;
        @BindView(R.id.tx_middle) TextView btn_middle;
        @BindView(R.id.tx_right) TextView btn_right;
        @BindView(R.id.item_price) TextView itemprice;
        OrderSCViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        public void bindHolder(OrderShangCheng order){
            time.setText(DateUtil.timeStamp2Date(order.getCreateTime()+"", "yyyy/MM/dd HH:mm"));
            orderNum.setText(order.getOrderId());
            name.setText(order.getShopName());
            addr.setText(order.getAddr());

            phone.setText(order.getUserTel());

            shopname.setText(order.getShopName());


            moneyTol.setText((double)order.getNeedPay()/100+"");

            if (order.getGoods().size()>0) {
                int shopnum = order.getGoods().get(0).getNum();
                double shopprice = (double) order.getGoods().get(0).getMallPrice() / 100;
                img.setImageURI(Constant.PHOTOBASEURL + order.getGoods().get(0).getPhoto());
                goodsname.setText(order.getGoods().get(0).getTitle());
                num.setText("X" + order.getGoods().get(0).getNum());

                itemprice.setText((double) (shopprice * shopnum) + "");
            }
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


