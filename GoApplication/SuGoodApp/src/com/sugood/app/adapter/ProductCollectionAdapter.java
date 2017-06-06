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
import com.sugood.app.entity.Collection;
import com.sugood.app.global.Constant;
import com.sugood.app.util.GlideUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Package :com.android.supermarket.user.adapter
 * Description :
 * Author :Rc3
 * Created at :2017/3/10 14:14.
 */

public class ProductCollectionAdapter extends RecyclerView.Adapter<ProductCollectionAdapter.ProductCollectionHolder> {

    Context context;
    List<Collection> list;

    public ProductCollectionAdapter(Context context, List<Collection> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ProductCollectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_collection_item, parent, false);
        return new ProductCollectionHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductCollectionHolder holder, int position) {
   final Collection collection=list.get(position);
        GlideUtil.displayImage(Constant.PHOTOBASEURL+collection.getPhoto(),holder.mImageView);
        holder.mTitle.setText(collection.getTitle());
        holder.mDesc.setText(collection.getIntro());
        holder.mPrice.setText("￥"+collection.getMallPrice());
        holder.mMallPrice.setText("市场价：￥"+collection.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, TakeawayMarketDetailActivity.class);
                intent.putExtra("goodsId",collection.getGoodsId()+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductCollectionHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_collection_img)
        ImageView mImageView;
        @BindView(R.id.product_collection_title)
        TextView mTitle;
        @BindView(R.id.product_collection_desc)
        TextView mDesc;
        @BindView(R.id.product_collection_price)
        TextView mPrice;
        @BindView(R.id.product_collection_market_price)
        TextView mMallPrice;

        public ProductCollectionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
