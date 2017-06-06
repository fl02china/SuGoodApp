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
 * Created at :2017/3/2 18:51.
 */

public class TakerawayCustomerAdapter extends RecyclerView.Adapter<TakerawayCustomerAdapter.CustomerHolder> {

    Context context;

    public TakerawayCustomerAdapter(Context contetx) {
        this.context = contetx;

    }

    @Override
    public CustomerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.takeaway_customer_item_tv, parent, false);

        return new CustomerHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 11;
    }

    public class CustomerHolder extends RecyclerView.ViewHolder {
        public CustomerHolder(View itemView) {
            super(itemView);
        }
    }
}
