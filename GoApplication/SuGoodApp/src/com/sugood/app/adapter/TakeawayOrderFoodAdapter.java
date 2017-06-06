package com.sugood.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.activity.TakeawayFoodDetailActivity;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.TakeawayShopTypeInfo;
import com.sugood.app.global.Constant;
import com.sugood.app.util.GlideUtil;
import com.sugood.app.view.MaterialDialog;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Package :com.android.supermarket.takeaway.adapter
 * Description :
 * Author :Rc3
 * Created at :2017/3/1 00:54.
 */

public class TakeawayOrderFoodAdapter extends RecyclerView.Adapter<TakeawayOrderFoodAdapter.OrderFoodHolder> {
    private static final String TAG = TakeawayOrderFoodAdapter.class.getSimpleName();

    List<TakeawayShopTypeInfo> mList;
    Context mContext;
    TagFlowLayout tagFlowLayout1;
    TagFlowLayout tagFlowLayout2;
    String[] attr1 = {"微辣", "特辣", "超级辣", "本身小辣不加辣"};
    String[] attr2 = {"多海鲜酱", "少海鲜酱"};
    String requestA = "";
    String requestB = "";
    int page = 1;


    public TakeawayOrderFoodAdapter(Context context, List<TakeawayShopTypeInfo> list) {

        this.mContext = context;
        this.mList = list;
    }

    @Override
    public OrderFoodHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.takeaway_shop_type_item, parent, false);
        return new OrderFoodHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderFoodHolder holder, int position) {

        final TakeawayShopTypeInfo info = mList.get(position);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, TakeawayFoodDetailActivity.class));
//            }
//        });

        GlideUtil.displayImage(Constant.PHOTOBASEURL + info.getPhoto(), holder.mImageView);
        holder.mFoodName.setText(info.getProductName());
        holder.mDesc.setText(info.getDesc());
        holder.mPrice.setText(info.getPrice());
        holder.mSoldNum.setText(info.getSoldNum());


        holder.mChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater mInflater = LayoutInflater.from(mContext);


                View view = mInflater.inflate(R.layout.takeaway_shop_detail_dialog, null);
                final MaterialDialog dialog = new MaterialDialog(mContext)
                        .setContentView(view)
                        .setCanceledOnTouchOutside(true);
                dialog.show();
                page = 1;
                final TextView request = (TextView) view.findViewById(R.id.takeaway_shop_dialog_request_tv);
                final TextView mAmount = (TextView) view.findViewById(R.id.takeaway_amount);
                final TextView mPrice = (TextView) view.findViewById(R.id.takeaway_shop_dialog_price);
                final TextView mFoodName = (TextView) view.findViewById(R.id.takeaway_shop_dialog_food_name);
                final TextView mAdd = (TextView) view.findViewById(R.id.takeaway_add);
                final TextView mReduce = (TextView) view.findViewById(R.id.takeaway_reduce);
                final Button mAddShopCar = (Button) view.findViewById(R.id.takeaway_shop_car_add);
                mAmount.setText("1");
                mFoodName.setText(info.getProductName());
                mPrice.setText(info.getPrice());

                mAddShopCar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnShopCarClickListener.onClick(info.getProductName(), info.getIntPrice() * page, page, info.getProductId() + "");
                        dialog.dismiss();
                    }
                });
                mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        page++;
                        mAmount.setText("" + page);
                        String allPrice = page * info.getIntPrice() + "";
//                        String result = allPrice.substring(0, allPrice.length() - 2) + "." + allPrice.substring(allPrice.length() - 2) + "元";
                        mPrice.setText(allPrice + "元");

                    }
                });

                mReduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (page > 1) {
                            page--;
                            mAmount.setText("" + page);
                            String allPrice = page * info.getIntPrice() + "";
                            mPrice.setText(allPrice + "元");
                        }

                    }
                });


                tagFlowLayout1 = (TagFlowLayout) view.findViewById(R.id.takeaway_shop_food_tabflowlayout1);
                tagFlowLayout1.setAdapter(new TagAdapter<String>(attr1) {

                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        Log.i(TAG, "getView: " + s);
                        TextView tv = (TextView) mInflater.inflate(R.layout.takeaway_shop_dialog_tab, parent, false);


                        tv.setText(s);
                        return tv;
                    }
                });

                tagFlowLayout1.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {

                        requestA = attr1[position];
                        request.setText(requestA + " " + requestB);
                        return true;
                    }
                });


                tagFlowLayout2 = (TagFlowLayout) view.findViewById(R.id.takeaway_shop_food_tabflowlayout2);
                tagFlowLayout2.setAdapter(new TagAdapter<String>(attr2) {

                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        Log.i(TAG, "getView: " + s);

                        TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.takeaway_shop_dialog_tab, tagFlowLayout2, false);
                        tv.setText(s);

                        return tv;
                    }
                });
                tagFlowLayout2.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        requestB = attr2[position];
                        request.setText(requestA + " " + requestB);
                        return true;
                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class OrderFoodHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.takeaway_shop_food_img)
        ImageView mImageView;
        @BindView(R.id.takeaway_shop_type_size_btn)
        Button mChoose;

        @BindView(R.id.takeaway_shop_food_name_tv)
        TextView mFoodName;
        @BindView(R.id.takeaway_shop_food_sold_num_tv)
        TextView mSoldNum;
        @BindView(R.id.takeaway_shop_food_price_tv)
        TextView mPrice;
        @BindView(R.id.takeaway_shop_food_description_tv)
        TextView mDesc;
        View view;

        public OrderFoodHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }

        public View getView() {
            return view;
        }
    }

    public interface OnShopCarClickListener {
        void onClick(String name, double price, int num, String productId);
    }

    OnShopCarClickListener mOnShopCarClickListener;

    public void setOnShopCarClickListener(OnShopCarClickListener onShopCarClickListener) {
        mOnShopCarClickListener = onShopCarClickListener;
    }
}
