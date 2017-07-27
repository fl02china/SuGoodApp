package com.sugood.app.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.entity.OrderBean;
import com.sugood.app.util.DateUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class OrderPayViewHolder  extends BaseOrderViewHolder{


    private TextView orderNum;
    private TextView time;
    private CircleImageView img;
    private TextView name;
    private RatingBar ratingbar;

    private TextView count;
    private RecyclerView recyclerView_list;
    private TextView moneyTol;
    private TextView moneyPeisong;
//    public TextView otherOrder;
//   public TextView pingJia;
     public TextView cancleorder;
  public TextView gaopay;
    public TextView connectclient;

    public OrderPayViewHolder(View view,int type){
        super(view,type);
        orderNum = (TextView) view.findViewById(R.id.order_num);
        time = (TextView) view.findViewById(R.id.time);
        img = (CircleImageView) view.findViewById(R.id.img);
        name = (TextView) view.findViewById(R.id.name);
        ratingbar = (RatingBar) view.findViewById(R.id.ratingbar);

        count = (TextView) view.findViewById(R.id.count);
        recyclerView_list = (RecyclerView) view.findViewById(R.id.recycle_list);//待完成
        moneyTol = (TextView) view.findViewById(R.id.money_tol);
        moneyPeisong = (TextView) view.findViewById(R.id.money_num);



        connectclient = (TextView) view.findViewById(R.id.connectclient);
        cancleorder = (TextView) view.findViewById(R.id.tx_cancle_order);
        gaopay = (TextView) view.findViewById(R.id.order_pay);
    }
    @Override
    public void bindHolder(OrderBean order) {
        time.setText(DateUtil.timeStamp2Date(order.getCreateTime()+"", "yyyy/MM/dd HH:mm"));
        orderNum.setText(order.getOrderId());
        name.setText(order.getShopName());
        System.out.println("aaaaa"+order.getNum());
        count.setText(order.getNum()+"");
        ratingbar.setRating(order.getScore());
        //  ratingbar.setNumStars(Integer.parseInt(order.getScore()+""));
        System.out.println("aaaaamoneypei"+moneyPeisong);
        moneyPeisong.setText(order.getLogistics()+"");
        moneyTol.setText(order.getNeedPay()+"");
    }
}
