package com.sugood.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.sugood.app.R;
import com.sugood.app.entity.OrderState;

import java.util.List;

/**
 * Package :com.android.supermarket.user.adapter
 * Description :
 * Author :Rc3
 * Created at :2017/3/10 09:58.
 */

public class OrderStateAdapter extends RecyclerView.Adapter<OrderStateAdapter.OrderStateHolder> {


    Context context;
    List<OrderState> list;

    public OrderStateAdapter(Context context, List<OrderState> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public OrderStateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_state_item, parent, false);
        return new OrderStateHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderStateHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OrderStateHolder extends RecyclerView.ViewHolder {
        public OrderStateHolder(View itemView) {
            super(itemView);
        }
    }
}
