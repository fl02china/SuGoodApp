package com.sugood.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.entity.UserOrder;
import com.sugood.app.global.Constant;
import com.sugood.app.util.DateUtil;
import com.sugood.app.util.GlideUtil;
import com.sugood.app.util.HttpUtil;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Package :com.android.supermarket.user.adapter
 * Description :
 * Author :Rc3
 * Created at :2017/3/8 15:08.
 */

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.UserOrderHolder> {


    List<UserOrder> mList;
    Context mContext;
    TextViewViewOnClickListener onOnClickListener;
    TextOnClickListener OnClickListener;

    public UserOrderAdapter(Context conetxt) {

        this.mContext = conetxt;

    }

    public void setData(List<UserOrder> list) {
        this.mList = list;
    }

    @Override
    public UserOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.user_order_rv_item, parent, false);


        return new UserOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserOrderHolder holder, final int position) {


        final UserOrder order = mList.get(position);
        holder.user_order_id_tv.setText("订单：" + order.getOrderId());
        holder.user_order_createtime_tv.setText("下单时间：" + DateUtil.timeStamp2Date(order.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        holder.user_order_img.setImageURI(Constant.PHOTOBASEURL + order.getPhoto());
        holder.user_order_price.setText("￥" + order.getPrice());
        holder.user_order_title.setText(order.getShopName());
        holder.user_order_desc.setText(order.getProductName());
        if ("0".equals(order.getStatus())) {
            holder.mPayBtn.setText("等待付款");
        } else if ("1".equals(order.getStatus())) {
            holder.mPayBtn.setText("已支付");
        } else if ("2".equals(order.getStatus())) {
            holder.mPayBtn.setText("已发货");
        } else if ("4".equals(order.getStatus())) {
            holder.mPayBtn.setText("退款中");
        } else if ("5".equals(order.getStatus())) {
            holder.mPayBtn.setText("退款成功");
        } else if ("8".equals(order.getStatus())) {
            holder.mPayBtn.setText("已完成");
        }
        if ("tuan".equals(order.getType()) && "1".equals(order.getStatus())) {
            holder.mQRCode.setVisibility(View.VISIBLE);
            GlideUtil.displayImage(Constant.PHOTOBASEURL + order.getQRCodeAdd(), holder.mQRCode);
            holder.mPassword.setVisibility(View.VISIBLE);
            holder.mPassword.setText(order.getCode());
        } else {
            holder.mQRCode.setVisibility(View.GONE);
            holder.mPassword.setVisibility(View.GONE);
        }


        holder.mPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order.getStatus().equals("0")) {
                    onOnClickListener.onOnClick(v, position);
                }
            }
        });

        holder.mRecomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickListener.onOnClick(v,position);
            }
        });

    }



    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class UserOrderHolder extends RecyclerView.ViewHolder {
        ImageView mQRCode;
        Button mPayBtn;
        Button mRecomment;
        TextView mPassword;
        TextView user_order_id_tv;
        TextView user_order_createtime_tv;
        SimpleDraweeView user_order_img;
        TextView user_order_title;
        TextView user_order_desc;
        TextView user_order_price;

        public UserOrderHolder(View itemView) {
            super(itemView);
            mQRCode = (ImageView) itemView.findViewById(R.id.user_order_qrcode);
            mPassword = (TextView) itemView.findViewById(R.id.user_order_pwd);
            mPayBtn = (Button) itemView.findViewById(R.id.user_order_pay_btn);
            mRecomment = (Button) itemView.findViewById(R.id.user_order_recomment);

            user_order_id_tv = (TextView) itemView.findViewById(R.id.user_order_id_tv);
            user_order_createtime_tv = (TextView) itemView.findViewById(R.id.user_order_createtime_tv);
            user_order_img = (SimpleDraweeView) itemView.findViewById(R.id.user_order_img);
            user_order_title = (TextView) itemView.findViewById(R.id.user_order_title);
            user_order_desc = (TextView) itemView.findViewById(R.id.user_order_desc);
            user_order_price = (TextView) itemView.findViewById(R.id.user_order_price);


        }


    }

    public void setViewOnClickListener(TextViewViewOnClickListener listener) {
        this.onOnClickListener = listener;
    }

    public interface TextViewViewOnClickListener {
        void onOnClick(View view, int position);
    }

    public void setTKOnClickListener(TextOnClickListener listener) {
        this.OnClickListener = listener;
    }

    public interface TextOnClickListener {
        void onOnClick(View view, int position);
    }

}
