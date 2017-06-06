package com.sugood.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.activity.TakeawayShopDetailActivity;
import com.sugood.app.entity.TakeawayShop;
import com.sugood.app.global.Constant;
import com.sugood.app.util.GlideUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Package :com.android.supermarket.takeaway.fragment
 * Description :
 * Author :Rc3
 * Created at :2017/2/28 10:45.
 */

public class TakeawayMainAdapter extends RecyclerView.Adapter<TakeawayMainAdapter.TakeawayShopHolder> {

    Context mContext;
    List<TakeawayShop> mList;

    public TakeawayMainAdapter(Context context, List<TakeawayShop> list) {
        this.mContext = context;
        this.mList = list;


    }

    @Override
    public TakeawayShopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.takeaway_main_rv_item, parent, false);


        return new TakeawayShopHolder(view);
    }

    @Override
    public void onBindViewHolder(final TakeawayShopHolder holder, int position) {
        final TakeawayShop takeawayShop = mList.get(position);
        GlideUtil.displayImage(Constant.PHOTOBASEURL + takeawayShop.getLogo(), holder.mImage);
        holder.mName.setText(takeawayShop.getShopName());
        holder.mRatingBar.setRating(takeawayShop.getScore());

        holder.mDistance.setText(takeawayShop.getMin() + "米");

        holder.mDeliveryTime.setText(takeawayShop.getDistribution() + "分钟");
        holder.mSoldNum.setText("月售" + takeawayShop.getMonthNum() + "单");

        holder.mDeliveryStartPrice.setText("起送￥" + takeawayShop.getSinceMoney());

        holder.mDeliveryPrice.setText("配送￥" + takeawayShop.getLogistics());


        if (takeawayShop.getDiscount() == 0) {

            holder.mCouponLayout.setVisibility(View.GONE);
        } else {
            holder.mCoupon.setText("折扣" + takeawayShop.getDiscount());

        }
        if (takeawayShop.getMinAmount() == 0 || takeawayShop.getAmount() == 0) {
            holder.mDiscountLayout.setVisibility(View.GONE);
        } else {
            holder.mDiscount.setText("满" + takeawayShop.getMinAmount() + "减" + takeawayShop.getAmount());

        }

        if (takeawayShop.getIsNew() == 1) {
            holder.mFirstOrder.setText("新用户满" + takeawayShop.getFullMoney() + "减" + takeawayShop.getNewMoney());
        } else {
            holder.mNewLayout.setVisibility(View.GONE);
        }

        if (takeawayShop.getIsFan() == 1) {
            holder.mReduce.setText("最高支持返现" + takeawayShop.getFanMoney());
        } else {

            holder.mReduceLayout.setVisibility(View.GONE);
        }


        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, TakeawayShopDetailActivity.class);
                intent.putExtra("shopId", String.valueOf(takeawayShop.getShopId()));
                intent.putExtra("shop", takeawayShop);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TakeawayShopHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.takeaway_shop_img)
        ImageView mImage;
        @BindView(R.id.takeaway_shop_name)
        TextView mName;
        @BindView(R.id.takeaway_shop_rating)
        RatingBar mRatingBar;
        @BindView(R.id.takeaway_shop_sold_num)
        TextView mSoldNum;
        @BindView(R.id.takeaway_shop_start_delivery_price)
        TextView mDeliveryStartPrice;
        @BindView(R.id.takeaway_shop_delivery_price)
        TextView mDeliveryPrice;
        @BindView(R.id.takeaway_shop_reduce_tv)
        TextView mReduce;
        @BindView(R.id.takeaway_shop_first_order_tv)
        TextView mFirstOrder;
        @BindView(R.id.takeaway_shop_discount_tv)
        TextView mDiscount;
        @BindView(R.id.takeaway_shop_distance)
        TextView mDistance;
        @BindView(R.id.takeaway_shop_delivary_time)
        TextView mDeliveryTime;
        @BindView(R.id.takeaway_shop_coupon)
        TextView mCoupon;
        @BindView(R.id.takeaway_coupon_ll1)
        LinearLayout mReduceLayout;
        @BindView(R.id.takeaway_coupon_ll2)
        LinearLayout mNewLayout;
        @BindView(R.id.takeaway_coupon_ll3)
        LinearLayout mDiscountLayout;
        @BindView(R.id.takeaway_coupon_ll4)
        LinearLayout mCouponLayout;

        public TakeawayShopHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        View getItemView() {
            return itemView;
        }
    }
}
