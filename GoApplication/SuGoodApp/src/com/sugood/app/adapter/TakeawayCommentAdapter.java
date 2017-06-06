package com.sugood.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sugood.app.R;
import com.sugood.app.activity.BigImageViewActivity;
import com.sugood.app.entity.TakeawayShopRemark;
import com.sugood.app.global.Constant;
import com.sugood.app.util.GlideUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Package :com.android.supermarket.takeaway.adapter
 * Description :
 * Author :Rc3
 * Created at :2017/3/2 01:22.
 */

public class TakeawayCommentAdapter extends RecyclerView.Adapter<TakeawayCommentAdapter.ShopComment> {
    List<TakeawayShopRemark.DianPingBean> mList;
    Context mContext;
    boolean ishow;


    public void setViewUpdate(boolean isShow) {
        this.ishow = isShow;
        notifyDataSetChanged();

    }

    public TakeawayCommentAdapter(List<TakeawayShopRemark.DianPingBean> l) {
        this.mList = l;
    }

    @Override
    public ShopComment onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.takeaway_shop_comment_item, parent, false);
        return new ShopComment(view);
    }

    @Override
    public void onBindViewHolder(ShopComment holder, int position) {
        final TakeawayShopRemark.DianPingBean dianPing = mList.get(position);
        GlideUtil.displayImage(Constant.PHOTOBASEURL + dianPing.getFace(), holder.mUserPhoto);
        holder.mNickName.setText(dianPing.getNickname());
        holder.mCreateTime.setText(dianPing.getCreateTime());
        holder.mComment.setText(dianPing.getContents());
        holder.mRatingBar.setRating(Integer.parseInt(dianPing.getScore()));
        if (dianPing.getPic() != null) {
            for (int i = 0; i < dianPing.getPic().size(); i++) {
                ImageView image = new ImageView(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 150);
                image.setLayoutParams(params);
                GlideUtil.displayImage(Constant.PHOTOBASEURL + dianPing.getPic().get(i), image);
                holder.mLayout.addView(image);
                final int finalI = i;
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, BigImageViewActivity.class);
                        intent.putExtra("url", dianPing.getPic().get(finalI));
                        mContext.startActivity(intent);
                    }
                });
            }
        }
        holder.mReceiveTime.setText(dianPing.getSpeed());
        if (TextUtils.isEmpty(dianPing.getReply())){
            holder.mShopRemark.setVisibility(View.GONE);
        }else {
            holder.mShopRemark.setText(dianPing.getReply());
        }
        if (ishow){
//            holder.ll1.setVisibility(View.VISIBLE);
//            holder.ll2.setVisibility(View.VISIBLE);
            holder.mShopRemark.setVisibility(View.VISIBLE);
        }else {
//            holder.ll2.setVisibility(View.GONE);
//            holder.ll1.setVisibility(View.GONE);
            holder.mShopRemark.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ShopComment extends RecyclerView.ViewHolder {

        @BindView(R.id.takeaway_shop_comment_createtime)
        TextView mCreateTime;
        @BindView(R.id.takeaway_shop_user_comment)
        TextView mComment;
        @BindView(R.id.takeaway_shop_comment_ll)
        LinearLayout mLayout;
        @BindView(R.id.takeaway_shop_comment_user_ratingbar)
        RatingBar mRatingBar;
        @BindView(R.id.takeaway_shop_comment_user_name)
        TextView mNickName;
        @BindView(R.id.takeaway_shop_comment_user_receiver_time)
        TextView mReceiveTime;
        @BindView(R.id.takeaway_shop_comment_user_img)
        CircleImageView mUserPhoto;
        @BindView(R.id.takeaway_shop_remark_tv)
        TextView mShopRemark;
        @BindView(R.id.takeaway_shop_user_comment_praise_ll1)
        LinearLayout ll1;
        @BindView(R.id.takeaway_shop_user_comment_praise_ll2)
        LinearLayout ll2;

        public ShopComment(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
