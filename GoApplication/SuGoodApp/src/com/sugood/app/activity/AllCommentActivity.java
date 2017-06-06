package com.sugood.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.entity.ShopComment;
import com.sugood.app.global.Constant;
import com.sugood.app.util.GlideUtil;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Package :com.sugood.app.activity
 * Description :
 * Author :Rc3
 * Created at :2017/04/18 09:30.
 */

public class AllCommentActivity extends AppCompatActivity {

    private static final String TAG = AllCommentActivity.class.getSimpleName();
    @BindView(R.id.all_comment_header_back)
    ImageView mBack;
    @BindView(R.id.all_comment_rv)
    XRecyclerView mXRecyclerView;
    Context mContext;
    CommentAdapter mAdapter;

    List<ShopComment> mList = new ArrayList<>();
    int page = 1;
    String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_comment);
        ButterKnife.bind(this);
        mContext = this;
        initData();

    }

    private void requestData(int page, final boolean isLoadMore) {
        RequestParams params = new RequestParams();
        // params.put("shopId",getIntent().getStringExtra("shopId"));
        params.put("shopId", "10");
        params.put("page", String.valueOf(page));
        params.put("pageSize", "20");
        HttpUtil.post(Constant.SHOP_COMMENT_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "onSuccess: " + new String(responseBody));
                if (!isLoadMore) {
                    mList.clear();
                }
                mList.addAll(JsonUtil.toList(new String(responseBody), ShopComment.class));

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + new String(responseBody));
            }
        });
    }

    private void initData() {
        mAdapter = new CommentAdapter();
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mXRecyclerView.setHasFixedSize(true);

        mXRecyclerView.setAdapter(mAdapter);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                requestData(1, false);

                mXRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                page++;
                requestData(page, true);
                mXRecyclerView.loadMoreComplete();
            }
        });
        mXRecyclerView.refresh();

    }


    public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyHolder> {
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_comment_rv_item, parent, false));
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {

            final ShopComment shopComment = mList.get(position);
            GlideUtil.displayImage(Constant.PHOTOBASEURL + shopComment.getFace(), holder.mCircleImageView);
            holder.mCircleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PhotoViewUI.class);
                    intent.putExtra("url", Constant.PHOTOBASEURL + shopComment.getFace());
                    startActivity(intent);
                }
            });
            holder.mTitle.setText(shopComment.getNickname());
            holder.mDesc.setText(shopComment.getContents());
            holder.mRatingBar.setRating(Integer.parseInt(shopComment.getScore()));
            holder.mScrollView.removeAllViews();

            for (int i = 0; i < shopComment.getPic().size(); i++) {
                ImageView img = new ImageView(mContext);
                img.setLayoutParams(new LinearLayoutCompat.LayoutParams(240, 240));
                GlideUtil.displayImage(Constant.PHOTOBASEURL + shopComment.getPic().get(i), img);
                holder.mScrollView.addView(img);
                url = Constant.PHOTOBASEURL + shopComment.getPic().get(i);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, PhotoViewUI.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.all_comment_img)
            CircleImageView mCircleImageView;
            @BindView(R.id.all_comment_title)
            TextView mTitle;
            @BindView(R.id.all_comment_desc)
            TextView mDesc;
            @BindView(R.id.all_comment_ratingbar)
            RatingBar mRatingBar;
            @BindView(R.id.all_comment_img_ll)
            LinearLayout mScrollView;

            public MyHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);

            }
        }
    }
}
