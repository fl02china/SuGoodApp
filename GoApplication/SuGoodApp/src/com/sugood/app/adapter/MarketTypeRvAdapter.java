package com.sugood.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.activity.TakeawayMarketDetailActivity;
import com.sugood.app.entity.ShopList;
import com.sugood.app.global.Constant;
import com.sugood.app.util.GlideUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Package :com.android.supermarket.supermaket
 * Description :
 * Author :Rc3
 * Created at :2017/2/27 00:41.
 */

public class MarketTypeRvAdapter extends RecyclerView.Adapter<MarketTypeRvAdapter.MarketTypeHolder> {
    List<ShopList> mList;
    Context mContext;

    public MarketTypeRvAdapter(Context context, List<ShopList> list) {
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public MarketTypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.market_type_rv_item, parent, false);


        return new MarketTypeHolder(view);
    }

    @Override
    public void onBindViewHolder(MarketTypeHolder holder, int position) {
        final ShopList shopList = mList.get(position);
        GlideUtil.displayImage(Constant.PHOTOBASEURL + shopList.getPhoto(), holder.mImage);
        holder.mTitle.setText(shopList.getTitle());
        holder.mPrice.setText(shopList.getPrice());
        holder.mSoldNum.setText(shopList.getSoldNum());
        holder.mContent.setText("");

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* mContext.startActivity(new Intent(mContext, SuperMarketDetailActivity.class));*/

                Intent intent = new Intent(mContext, TakeawayMarketDetailActivity.class);
                intent.putExtra("goodsId", shopList.getGoodsId());

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MarketTypeHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.market_type_rv_item_image)
        ImageView mImage;
        @BindView(R.id.market_type_rv_item_product_title)
        TextView mTitle;
        @BindView(R.id.market_type_rv_item_product_content)
        TextView mContent;
        @BindView(R.id.market_type_rv_item_product_price)
        TextView mPrice;
        @BindView(R.id.market_type_rv_item_product_sold_num)
        TextView mSoldNum;

        public MarketTypeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

        public View getView() {
            return itemView;
        }
    }
}
