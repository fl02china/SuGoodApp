package com.sugood.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.entity.Status;

import java.util.List;

/**
 * Created by wilk on 17/4/25 16:37
 * ganweib@gmail.com
 * describe:
 */

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {


    private List<Status> statusList;

    public void setData(List<Status> statusList) {
        this.statusList = statusList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("ss", "onBindViewHolder: "+position );
        holder.tv_time.setText(statusList.get(position).getTime());
        if (statusList.get(position).getStatus() == 0) {
            holder.iv_status.setImageResource(R.drawable.xiadan);
            holder.tv_content.setText("订单编号：" + statusList.get(position).getTypeOrderId());
            holder.tv_title.setText("用户下单");
        } else if (statusList.get(position).getStatus() == 9) {
            holder.iv_status.setImageResource(R.drawable.shangjia);
            holder.tv_content.setText("商家正在准备商品,请耐心等待...");
            holder.tv_title.setText("商家已接单");
        } else if (statusList.get(position).getStatus() == 2) {
            holder.iv_status.setImageResource(R.drawable.qucan);
            holder.tv_content.setText("骑手" + statusList.get(position).getName() + " " + statusList.get(position).getMobile() + "正在飞奔送餐中...");
            holder.tv_title.setText("配送员已取餐");
        } else if (statusList.get(position).getStatus() == 8) {
            holder.iv_status.setImageResource(R.drawable.wancheng);
            holder.tv_content.setText("商品已安全送达小主手中,请慢用...");
            holder.tv_title.setText("订单已完成");
        }


    }

    @Override
    public int getItemCount() {
        return statusList != null ? statusList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView iv_status;
        TextView tv_title;
        TextView tv_time;
        TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_status = (ImageView) itemView.findViewById(R.id.iv_status);
            tv_title = (TextView) itemView.findViewById(R.id.status_title);
            tv_time = (TextView) itemView.findViewById(R.id.status_time);
            tv_content = (TextView) itemView.findViewById(R.id.status_content);

        }
    }


}
