package com.sugood.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.entity.TakeawayShopType;

import java.util.List;

/**
 * Package :com.android.supermarket.takeaway.fragment
 * Description :
 * Author :Rc3
 * Created at :2017/2/28 15:23.
 */

public class TakeawayShopOrderAdapter extends RecyclerView.Adapter<TakeawayShopOrderAdapter.OrderHolder> {


    public int mSelectPos = -1;
    Context mContext;
    List<TakeawayShopType> mList;
    OnitemClickListener mOnitemClickListener;

    public TakeawayShopOrderAdapter(Context context, List<TakeawayShopType> list) {
        this.mContext = context;
        this.mList = list;
        mList.get(0).setSelected(true);
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isSelected()) {
                mSelectPos = i;
            }
        }
    }

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.takeaway_shop_order_item, parent, false);


        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderHolder holder, final int position) {
        holder.mType.setText(mList.get(position).getTypeName());

        if (mList.get(position).isSelected()) {
            holder.mView.setBackgroundResource(R.drawable.takeaway_shop_bg);
        } else {
            holder.mView.setBackgroundResource(R.drawable.takeaway_shop_uncheck_bg);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectPos != position) {
                    mList.get(mSelectPos).setSelected(false);
                    notifyItemChanged(mSelectPos);
                    mSelectPos = position;
                    mList.get(mSelectPos).setSelected(true);
                    notifyItemChanged(position);
                }

                mOnitemClickListener.onClick(position);

            }
        });



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

   public void setOnitemClickLitener(OnitemClickListener l) {
        this.mOnitemClickListener = l;
    }

    public interface OnitemClickListener {
        void onClick(int position);
    }

    class OrderHolder extends RecyclerView.ViewHolder {
        TextView mType;
        LinearLayout mView;

        public OrderHolder(View itemView) {
            super(itemView);
            mType = (TextView) itemView.findViewById(R.id.takeaway_shop_type_tv);
            mView = (LinearLayout) itemView.findViewById(R.id.takeaway_shop_type_ll);
        }

        View getItemView() {
            return itemView;
        }
    }
}
