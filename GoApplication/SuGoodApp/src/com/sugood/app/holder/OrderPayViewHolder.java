package com.sugood.app.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sugood.app.R;
import com.sugood.app.entity.OrderBean;
import com.sugood.app.global.Constant;
import com.sugood.app.util.DateUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class OrderPayViewHolder  extends BaseOrderViewHolder{


    private TextView orderNum;
    private TextView time;
    private SimpleDraweeView img;
    private TextView name;
    private RatingBar ratingbar;

    private TextView count;
    private RecyclerView recyclerView_list;
    private TextView moneyTol;
    private TextView moneyPeisong;
//    public TextView otherOrder;
//   public TextView pingJia;
     public TextView btn_left;
  public TextView btn_right;
    public TextView connectclient;



    public TextView list_food_name1;
    public TextView list_food_num1;
    public TextView list_food_price1;

    public TextView list_food_name2;
    public TextView list_food_num2;
    public TextView list_food_price2;
    public LinearLayout liner_list_2;
    public OrderPayViewHolder(View itemView,int type){
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

        count = (TextView) itemView.findViewById(R.id.count);
       // recyclerView_list = (RecyclerView) view.findViewById(R.id.recycle_list);//待完成
        moneyTol = (TextView) itemView.findViewById(R.id.money_tol);
        moneyPeisong = (TextView) itemView.findViewById(R.id.money_num);



        connectclient = (TextView) itemView.findViewById(R.id.connectclient);
        btn_left = (TextView) itemView.findViewById(R.id.tx_left);
        btn_right = (TextView) itemView.findViewById(R.id.tx_right);
    }
    @Override
    public void bindHolder(OrderBean order) {
        time.setText(DateUtil.timeStamp2Date(order.getCreateTime()+"", "yyyy/MM/dd HH:mm"));
        orderNum.setText(order.getOrderId());
        name.setText(order.getShopName());
        img.setImageURI(Constant.PHOTOBASEURL+order.getLogo());
        count.setText("商品数量"+"("+order.getNum()+")");
        ratingbar.setRating(order.getScore());
        //  ratingbar.setNumStars(Integer.parseInt(order.getScore()+""));
        if (order.getLogistics()>0){
        moneyPeisong.setText( "含配送费"+(double)order.getLogistics()/100+"元");}else {
            moneyPeisong.setVisibility(View.GONE);
        }

        list_food_name1.setText(order.getWaimai().get(0).getProductName());
        list_food_num1.setText("×"+order.getWaimai().get(0).getNum()+"");
        list_food_price1.setText((double)(order.getWaimai().get(0).getPrice())/100+"");
        if (order.getWaimai().size()>1){
            liner_list_2.setVisibility(View.VISIBLE);
            list_food_name2.setText(order.getWaimai().get(1).getProductName());
            list_food_num2.setText("×"+order.getWaimai().get(1).getNum()+"");
            list_food_price2.setText((double)(order.getWaimai().get(1).getPrice())/100+"");
        }
        moneyTol.setText((double)order.getNeedPay()/100+"");
    }
}
