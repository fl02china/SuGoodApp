package com.sugood.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sugood.app.R;


/**
 * Package :com.android.supermarket.takeaway.adapter
 * Description :
 * Author :Rc3
 * Created at :2017/3/2 17:47.
 */

public class TakeawaySchedulerAdapter extends RecyclerView.Adapter<TakeawaySchedulerAdapter.SchedulerHolder> {
    Context context;

    public TakeawaySchedulerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SchedulerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.takeaway_scheduler_item, parent, false);

        return new SchedulerHolder(view);
    }

    @Override
    public void onBindViewHolder(SchedulerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class SchedulerHolder extends RecyclerView.ViewHolder {

        public SchedulerHolder(View itemView) {
            super(itemView);
        }
    }
}
