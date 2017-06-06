package com.sugood.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.entity.ShopList;
import com.sugood.app.global.Constant;
import com.sugood.app.util.GlideUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Package :com.sugood.app.adapter
 * Description :
 * Author :Rc3
 * Created at :2017/3/27 17:50.
 */

public class TakeawayMarketAdapter extends RecyclerView.Adapter<TakeawayMarketAdapter.TakeawayMarketHolder> {


    public ItemOnClickListener mListener;
    List<ShopList> mList;


    public TakeawayMarketAdapter(List<ShopList> list) {
        this.mList = list;
    }

    @Override
    public TakeawayMarketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.takeaway_market_item, null);
        return new TakeawayMarketHolder(view);
    }

    @Override
    public void onBindViewHolder(TakeawayMarketHolder holder, final int position) {
        ShopList list = mList.get(position);
        GlideUtil.displayImage(Constant.PHOTOBASEURL + list.getPhoto(), holder.mImageView);
        holder.mTitle.setText(list.getTitle());
        holder.mPrice.setText(list.getPrice());
        holder.mBuy.setText(list.getSoldNum());
        holder.itemView.setTag(holder);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setListener(ItemOnClickListener mListener) {
        this.mListener = mListener;
    }

    public interface ItemOnClickListener {
        void onClick(View v, int position);

    }

    public class TakeawayMarketHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.takeaway_market_item_img)
        ImageView mImageView;
        @BindView(R.id.takeaway_market_item_title)
        TextView mTitle;
        @BindView(R.id.takeaway_market_item_price)
        TextView mPrice;
        @BindView(R.id.takeaway_market_item_buysum)
        TextView mBuy;


        public TakeawayMarketHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        View getItemView() {
            return itemView;
        }
    }
}
