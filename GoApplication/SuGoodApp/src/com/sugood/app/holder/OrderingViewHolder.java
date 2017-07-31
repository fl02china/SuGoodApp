package com.sugood.app.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sugood.app.R;
import com.sugood.app.entity.OrderBean;
import com.sugood.app.util.DateUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class OrderingViewHolder extends BaseOrderViewHolder {


    private TextView orderNum;
    private TextView time;
    private SimpleDraweeView img;
    private TextView name;
    private RatingBar ratingbar;
    public TextView connectclient;

    private TextView count;
    private RecyclerView recycleList;

    private LinearLayout liner1;

    private TextView moneyTol;
    private TextView moneyNum;
    public ProgressBar progress;

    public TextView moneyPeisong;
    public TextView progressT1;
    public TextView progressT2;
    public TextView progressT3;
    public TextView progressT4;
    public TextView progressT5;

    public TextView btn_left;
    public TextView btn_right;
    public TextView list_food_name1;
    public TextView list_food_num1;
    public TextView list_food_price1;

    public TextView list_food_name2;
    public TextView list_food_num2;
    public TextView list_food_price2;
    public LinearLayout liner_list_2;

    public OrderingViewHolder(View itemView,int type){
        super(itemView,type);

        list_food_name1  =(TextView) itemView.findViewById(R.id.list_food_name1);
        list_food_num1 =(TextView) itemView.findViewById(R.id.list_food_num1);
        list_food_price1 =(TextView) itemView.findViewById(R.id.list_food_price1);
        liner_list_2 =(LinearLayout) itemView.findViewById(R.id.liner_list_2);
        list_food_name2  =(TextView) itemView.findViewById(R.id.list_food_name2);
        list_food_num2 =(TextView) itemView.findViewById(R.id.list_food_num2);
        list_food_price2 =(TextView) itemView.findViewById(R.id.list_food_price2);

        orderNum = (TextView) itemView.findViewById(R.id.order_num);
        time = (TextView) itemView.findViewById(R.id.time);
        img = (SimpleDraweeView) itemView.findViewById(R.id.img);
        name = (TextView) itemView.findViewById(R.id.name);
        ratingbar = (RatingBar) itemView.findViewById(R.id.ratingbar);

        progress = (ProgressBar) itemView.findViewById(R.id.progress);

        progressT1 = (TextView) itemView.findViewById(R.id.progress_t1);
        progressT2 = (TextView) itemView.findViewById(R.id.progress_t2);
        progressT3 = (TextView) itemView.findViewById(R.id.progress_t3);
        progressT4 = (TextView) itemView.findViewById(R.id.progress_t4);
        progressT5 = (TextView) itemView.findViewById(R.id.progress_t5);
        moneyPeisong = (TextView) itemView.findViewById(R.id.money_num);

        connectclient = (TextView) itemView.findViewById(R.id.connectclient);
        btn_left = (TextView) itemView.findViewById(R.id.tx_left);
        btn_right = (TextView) itemView.findViewById(R.id.tx_right);
        moneyTol = (TextView) itemView.findViewById(R.id.money_tol);
        count = (TextView) itemView.findViewById(R.id.count);



    }
    @Override
    public void bindHolder(OrderBean order) {
        time.setText(DateUtil.timeStamp2Date(order.getCreateTime()+"", "yyyy/MM/dd HH:mm"));
        orderNum.setText(order.getOrderId());
        name.setText(order.getShopName());

        count.setText("商品数量"+"("+order.getNum()+"）");
        ratingbar.setRating(order.getScore());
        //  ratingbar.setNumStars(Integer.parseInt(order.getScore()+""));

        if (order.getLogistics()>0){
            moneyPeisong.setText( "含配送费"+(double)order.getLogistics()/100+"元");}else {
            moneyPeisong.setVisibility(View.GONE);
        }

        moneyTol.setText((double)order.getNeedPay()/100+"");

        list_food_name1.setText(order.getWaimai().get(0).getProductName());
        list_food_num1.setText("×"+order.getWaimai().get(0).getNum()+"");
        list_food_price1.setText((double)(order.getWaimai().get(0).getPrice())/100+"");
        if (order.getWaimai().size()>1){
            liner_list_2.setVisibility(View.VISIBLE);
            list_food_name2.setText(order.getWaimai().get(1).getProductName());
            list_food_num2.setText(order.getWaimai().get(1).getNum()+"");
            list_food_price2.setText((double)(order.getWaimai().get(1).getPrice())/100+"");
        }
    }
}
