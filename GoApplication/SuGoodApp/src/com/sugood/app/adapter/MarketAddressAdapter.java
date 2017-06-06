package com.sugood.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.activity.AddAddrActivity;
import com.sugood.app.entity.UserAddress;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Package :com.android.supermarket.supermaket
 * Description :
 * Author :Rc3
 * Created at :2017/2/27 16:08.
 */

public class MarketAddressAdapter extends RecyclerView.Adapter<MarketAddressAdapter.AddressHolder> {

    Context mContext;
    List<UserAddress> mList;
    AddressAdapter.ItemViewOnClickListener onItemOnClickListener;

    public MarketAddressAdapter(Context context) {
        this.mContext = context;

    }

    public MarketAddressAdapter(Context mContext, List<UserAddress> mList) {
        this.mContext = mContext;
        this.mList = mList;
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setEdit(false);
        }
    }

    public void setList(List<UserAddress> mList) {
        this.mList = mList;
    }

    @Override
    public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.manager_address_item, parent, false);

        return new AddressHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressHolder holder,final int position) {
        final UserAddress address = mList.get(position);
        holder.address_user_name.setText(address.getXm());
        holder.address_user_phone.setText(address.getTel());
        holder.address_tv.setText(address.getAreaStr() + address.getInfo());
        if (address.isEdit()) {

            holder.mDelete.setVisibility(View.VISIBLE);
            holder.mDeleteImage.setVisibility(View.VISIBLE);
            holder.mEdit.setVisibility(View.VISIBLE);
            holder.mEditImage.setVisibility(View.VISIBLE);

        } else {
            holder.mDelete.setVisibility(View.GONE);
            holder.mDeleteImage.setVisibility(View.GONE);
            holder.mEdit.setVisibility(View.GONE);
            holder.mEditImage.setVisibility(View.GONE);
        }

        holder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, AddAddrActivity.class);
                intent.putExtra("address", address);
                mContext.startActivity(intent);
            }
        });
        holder.mEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, AddAddrActivity.class);
                intent.putExtra("address", address);
                mContext.startActivity(intent);
            }
        });
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delete(address, position);

            }
        });
        holder.mDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(address, position);
            }
        });
    }


    private void delete(UserAddress address, final int position) {
        RequestParams params = new RequestParams();
        params.put("userId", address.getUserId());
        params.put("id", address.getId());
        HttpUtil.post(Constant.DELETE_ADDRESS_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    boolean success = json.getBoolean("success");
                    if (success) {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                                mList.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setOnItemOnClickListener(AddressAdapter.ItemViewOnClickListener listener) {
        this.onItemOnClickListener = listener;
    }


    public interface ItemViewOnClickListener {
        void onItemOnClick(View view, int position);
    }

    public class AddressHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView address_user_name;
        TextView address_user_phone;
        TextView address_tv;
        RelativeLayout rl_address;
        ImageView mEditImage;
        TextView mEdit;
        ImageView mDeleteImage;
        TextView mDelete;


        public AddressHolder(View itemView) {
            super(itemView);
            address_user_name = (TextView) itemView.findViewById(R.id.address_user_name);
            address_user_phone = (TextView) itemView.findViewById(R.id.address_user_phone);
            address_tv = (TextView) itemView.findViewById(R.id.address_tv);
            rl_address = (RelativeLayout) itemView.findViewById(R.id.rl_address);
            rl_address.setOnClickListener(this);
            mEditImage = (ImageView) itemView.findViewById(R.id.address_edit_img);
            mEdit = (TextView) itemView.findViewById(R.id.address_edit_tv);
            mDeleteImage = (ImageView) itemView.findViewById(R.id.address_delete_img);
            mDelete = (TextView) itemView.findViewById(R.id.address_delete_tv);
        }

        @Override
        public void onClick(View v) {
            if (onItemOnClickListener != null) {
                onItemOnClickListener.onItemOnClick(v, getPosition());
            }
        }
    }
}
