package com.sugood.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.TakeawayCommentAdapter;
import com.sugood.app.entity.TakeawayShopRemark;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Package :com.android.supermarket.takeaway.fragment
 * Description :
 * Author :Rc3
 * Created at :2017/3/1 17:06.
 */

public class TakeawayCommentFragment extends Fragment {
    private static final String TAG = TakeawayCommentFragment.class.getSimpleName();
    Context mContext;
    TagFlowLayout mTagFlowLayout;
    TagAdapter<String> mAdapter;
    @BindView(R.id.takeaway_comment_tag_cb)
    CheckBox takeaway_comment_tag_cb;

    @BindView(R.id.takeaway_shop_comment_rv)
    XRecyclerView mXRecyclerView;

    List<TakeawayShopRemark.DianPingBean> mList = new ArrayList<>();
    TakeawayCommentAdapter mTakeawayCommentAdapter;

    int page = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.takeaway_comment_fragment, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
        initView(view, getArguments().getString("shopId"));
        return view;
    }


    private void requestData(int page, String shopId, final boolean isLoadMore) {

//        String url = "http://192.168.0.106:8080/Speed/Speed/WaiMai/baoShop/eleDianPing";
        RequestParams params = new RequestParams();
        params.put("page", String.valueOf(page));
        params.put("pageSize", "20");
        params.put("shopId", shopId);
        HttpUtil.post(Constant.TAKEAWAY_REMARK_LIST_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String result = new String(responseBody);
                    TakeawayShopRemark remark = JsonUtil.toObject(result, TakeawayShopRemark.class);
                    Log.i(TAG, "onSuccess: " + result);
                    if (!isLoadMore) {
                        mList.clear();
                    }
                    mList.addAll(remark.getDianPing());
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {

                            mTakeawayCommentAdapter.notifyDataSetChanged();
                            mXRecyclerView.loadMoreComplete();
                            mXRecyclerView.refreshComplete();
                        }
                    });
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, final Throwable error) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


    private void initView(View view, final String shopId) {

        takeaway_comment_tag_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTakeawayCommentAdapter.setViewUpdate(false);
                } else {
                    mTakeawayCommentAdapter.setViewUpdate(true);
                }
            }
        });

        mXRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mXRecyclerView.setHasFixedSize(true);
        mXRecyclerView.setAdapter(mTakeawayCommentAdapter = new TakeawayCommentAdapter(mList));
        mTakeawayCommentAdapter.setViewUpdate(true);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                requestData(1, shopId, false);
            }

            @Override
            public void onLoadMore() {
                page++;
                requestData(page, shopId, true);
            }
        });
        mXRecyclerView.refresh();


        String[] attr = {"全部", "有图12", "好评51", "中评9", "差评7", "味道赞80", "包装精美66", "准时送达32"};

        mTagFlowLayout = (TagFlowLayout) view.findViewById(R.id.takeaway_shop_comment_tagflowlayout);

        mTagFlowLayout.setAdapter(mAdapter = new TagAdapter<String>(attr) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView v = (TextView) LayoutInflater.from(mContext).inflate(R.layout.takeaway_shop_comment_tag, parent, false);
                v.setText(s);
                v.setClickable(false);


                if (position < 6) {
                    if (position == 0) {
                        v.setBackgroundResource(R.drawable.comment_all_tag);
                        v.setTextColor(getResources().getColor(R.color.text_yellow));

                    } else {
                        v.setBackgroundResource(R.drawable.commemt_tag_select_shape);
                        v.setTextColor(getResources().getColor(R.color.text_yellow));
                    }

                } else {

                    v.setBackgroundResource(R.drawable.comment_tag_uncheck);
                    v.setTextColor(getResources().getColor(R.color.text_grey));
                }
                return v;
            }
        });
        mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                return true;
            }
        });


    }
}
