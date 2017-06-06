package com.sugood.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.entity.Msg;
import com.sugood.app.entity.UserNotification;
import com.sugood.app.util.DateUtil;

import java.util.List;

/**
 * Package :com.android.supermarket.user.adapter
 * Description :
 * Author :Rc3
 * Created at :2017/3/10 15:27.
 */

public class UserNotificationAdapter extends RecyclerView.Adapter<UserNotificationAdapter.UserNotificationHolder> {


    List<Msg> list;
    Context context;

    public UserNotificationAdapter(Context context, List<Msg> list) {

        this.context = context;
        this.list = list;


    }

    @Override
    public UserNotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_notification_item, parent, false);
        return new UserNotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(UserNotificationHolder holder, int position) {
        holder.tv_title.setText(list.get(position).getTitle());
        holder.tv_content.setText(list.get(position).getIntro());
        holder.tv_time.setText(DateUtil.timeStamp2Date(list.get(position).getCreateTime(),"yyyy-MM-dd HH:mm:ss"));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UserNotificationHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        TextView tv_content;
        TextView tv_time;

        public UserNotificationHolder(View itemView) {
            super(itemView);

            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_content = (TextView) itemView.findViewById(R.id.msgContent);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);

        }
    }
}
