package com.sugood.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.activity.TakeawayMarketShopActivity;
import com.sugood.app.activity.TakeawayShopDetailActivity;
import com.sugood.app.entity.ShopCollection;
import com.sugood.app.global.Constant;
import com.sugood.app.util.GlideUtil;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Package :com.android.supermarket.user.adapter
 * Description :
 * Author :Rc3
 * Created at :2017/3/10 15:06.
 */

public class ShopCollectionAdapter extends RecyclerView.Adapter<ShopCollectionAdapter.ShopCollectionHolder> {
    Context context;
    List<ShopCollection> list;

    public ShopCollectionAdapter(Context context, List<ShopCollection> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public ShopCollectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_collection_item, parent, false);

        return new ShopCollectionHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopCollectionHolder holder, int position) {
        final ShopCollection collection = list.get(position);
        GlideUtil.displayImage(Constant.PHOTOBASEURL + collection.getPhoto(), holder.mImageView);
        holder.mTel.setText(collection.getTel());
        holder.mShopName.setText(collection.getShopName());
        holder.mAddr.setText(collection.getAddr());
        holder.mDelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delect(collection);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (collection.getType().equals("2")) {
                    intent.setClass(context, TakeawayMarketShopActivity.class);
                    intent.putExtra("shopId", collection.getShopId() + "");
                    context.startActivity(intent);
                } else if (collection.getType().equals("1")) {
                    intent.setClass(context, TakeawayShopDetailActivity.class);
                    intent.putExtra("shopId", collection.getShopId() + "");
                    context.startActivity(intent);
                } else {
                    ToastUtil.setToast(context, "暂不支持");
                }


            }
        });
    }

    private void delect(ShopCollection collection) {

      //showLoading("");
        RequestParams params = new RequestParams();
        params.put("Id", collection.getId());

        HttpUtil.post(Constant.DELETESHOP, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("TUi", "onSuccess: " + response.toString());

                try {
                    if (!response.getBoolean("success")) {
                        ToastUtil.setToast(context, response.getString("message"));
                    } else {
                        ToastUtil.setToast(context, "删除商店成功");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("TUi", "onSuccess: " + responseString);

                ToastUtil.setToast(context, "删除商店失败");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ShopCollectionHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.shop_collection_img)
        ImageView mImageView;
        @BindView(R.id.shop_collection_name)
        TextView mShopName;
        @BindView(R.id.shop_collection_location_text)
        TextView mAddr;
        @BindView(R.id.delete)
        TextView mDelect;
        @BindView(R.id.shop_collection_delivery_tel)
        TextView mTel;

        public ShopCollectionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
