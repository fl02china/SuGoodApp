package com.sugood.app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.entity.UserAddress;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Package :com.android.supermarket.user.adapter
 * Description :
 * Author :Rc3
 * Created at :2017/3/8 18:16.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> {
    Context mConetxt;
    List<UserAddress> mList;
    ItemViewOnClickListener onItemOnClickListener;


    public AddressAdapter(Context conetxt) {
        this.mConetxt = conetxt;

    }

    public void setList(List<UserAddress> mList) {
        this.mList = mList;
    }

    @Override
    public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mConetxt).inflate(R.layout.manager_address_item, parent, false);


        return new AddressHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressHolder holder, final int position) {
        UserAddress address = mList.get(position);
        holder.address_user_name.setText(address.getXm());
        holder.address_user_phone.setText(address.getTel());
        holder.address_tv.setText(address.getAreaStr() + address.getInfo());
        if (address.getDefaults().equals("0")) {
            holder.address_cb.setChecked(false);
        } else {
            holder.address_cb.setChecked(true);
        }

        holder.address_delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(position);
            }
        });
    }

    private void showDialog(final int postion) {
  /*
  这里使用了 android.support.v7.app.AlertDialog.Builder
  可以直接在头部写 import android.support.v7.app.AlertDialog
  那么下面就可以写成 AlertDialog.Builder
  */
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mConetxt);
        builder.setTitle("Material Design Dialog");
        builder.setMessage("这是 android.support.v7.app.AlertDialog 中的样式");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteAddr(postion);
            }
        });
        builder.show();
    }

    private void deleteAddr(int pos) {
        RequestParams params = new RequestParams();
        params.put("id", mList.get(pos).getId());
        HttpUtil.post(Constant.SUGOODDELETEADDR, params, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("T_T", "onSuccess: " + response.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class AddressHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView address_user_name;
        private TextView address_user_phone;
        private TextView address_tv;
        private CheckBox address_cb;
        private TextView address_delete_tv;
        private ImageView address_delete_img;
        private TextView address_edit_tv;
        private ImageView address_edit_img;
        private RelativeLayout rl_address;


        public AddressHolder(View itemView) {
            super(itemView);
            address_user_name = (TextView) itemView.findViewById(R.id.address_user_name);
            address_user_phone = (TextView) itemView.findViewById(R.id.address_user_phone);
            address_tv = (TextView) itemView.findViewById(R.id.address_tv);
            address_cb = (CheckBox) itemView.findViewById(R.id.address_cb);
            address_delete_img = (ImageView) itemView.findViewById(R.id.address_delete_img);
            address_edit_tv = (TextView) itemView.findViewById(R.id.address_edit_tv);
            address_edit_img = (ImageView) itemView.findViewById(R.id.address_edit_img);
            rl_address = (RelativeLayout) itemView.findViewById(R.id.rl_address);
            rl_address.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (onItemOnClickListener != null) {
                onItemOnClickListener.onItemOnClick(v, getPosition());
            }
        }
    }

    public void setOnItemOnClickListener(ItemViewOnClickListener listener) {
        this.onItemOnClickListener = listener;
    }

    public interface ItemViewOnClickListener {
        void onItemOnClick(View view, int position);
    }
}
