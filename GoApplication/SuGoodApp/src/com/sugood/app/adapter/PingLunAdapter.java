package com.sugood.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sugood.app.R;
import com.sugood.app.entity.PingLun;
import com.sugood.app.global.Constant;

import java.util.List;

/**
 * Created by wilk on 17/4/26 18:14
 * ganweib@gmail.com
 * describe:
 */

public class PingLunAdapter extends RecyclerView.Adapter<PingLunAdapter.ViewHolder> {


    private List<PingLun> statusList;

    public void setData(List<PingLun> statusList) {
        this.statusList = statusList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pinglun, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.sdv_avatar.setImageURI(Constant.PHOTOBASEURL + statusList.get(position).getFace());
        holder.tv_score.setText(statusList.get(position).getScore());
        holder.tv_evaluate_content.setText(statusList.get(position).getEvaluate());
        holder.tv_userName1.setText(statusList.get(position).getNickname());

    }

    @Override
    public int getItemCount() {
        return statusList != null ? statusList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView sdv_avatar;
        TextView tv_userName1;
        TextView tv_score;
        TextView tv_evaluate_content;

        public ViewHolder(View itemView) {
            super(itemView);
            sdv_avatar = (SimpleDraweeView) itemView.findViewById(R.id.sdv_avatar);
            tv_userName1 = (TextView) itemView.findViewById(R.id.tv_userName1);
            tv_score = (TextView) itemView.findViewById(R.id.tv_score);
            tv_evaluate_content = (TextView) itemView.findViewById(R.id.tv_evaluate_content);
        }
    }


}
