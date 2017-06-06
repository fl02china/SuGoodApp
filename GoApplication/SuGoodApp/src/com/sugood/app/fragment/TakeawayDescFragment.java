package com.sugood.app.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.entity.TakeawayShopDesc;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;

import static com.sugood.app.R.id.takeaway_shop_desc_reduce;

/**
 * Package :com.android.supermarket.takeaway.fragment
 * Description :
 * Author :Rc3
 * Created at :2017/3/2 10:19.
 */

public class TakeawayDescFragment extends Fragment {


    @BindView(takeaway_shop_desc_reduce)
    TextView mReduce;
    @BindView(R.id.takeaway_shop_desc_roupon)
    TextView mRoupon;
    @BindView(R.id.takeaway_shop_desc_return)
    TextView mReturn;
    @BindView(R.id.takeaway_shop_desc_pay_online)
    TextView mPayLine;
    @BindView(R.id.takeaway_shop_desc_worktime)
    TextView mWorkTime;
    @BindView(R.id.takeaway_shop_desc_tel)
    TextView mTel;
    @BindView(R.id.takeaway_shop_desc_addr)
    TextView mAddress;
    @BindView(R.id.takeaway_shop_desc_advice)
    TextView mAdvice;
    @BindView(R.id.rl_tel)
    RelativeLayout rl_tel;

    Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.takeaway_shop_desc_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        requestData(getArguments().getString("shopId"));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void requestData(String shopId) {
        RequestParams params = new RequestParams();
        params.put("shopId", shopId);
        HttpUtil.post(Constant.TAKEAWAY_DESC_LIST_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String result = new String(responseBody);
                    Log.e("---desc+++", "onSuccess: " + result);
                    try {
                        final TakeawayShopDesc shopDesc = new TakeawayShopDesc();
                        JSONObject json = new JSONObject(result);
                        JSONArray array = json.getJSONArray("list");
                        JSONObject desc = array.getJSONObject(0);

                        shopDesc.setIsPay(desc.getInt("isPay"));

                        shopDesc.setIsNes(desc.getInt("isNew"));
                        if (desc.getInt("isNew") == 1) {
                            shopDesc.setFullMoney(desc.getInt("fullMoney"));
                            shopDesc.setNewMoney(desc.getInt("newMoney"));
                        }
                        shopDesc.setTel(desc.getString("tel"));
                        shopDesc.setAddr(desc.getString("addr"));
                        shopDesc.setIsFan(desc.getInt("isFan"));

                        if (desc.getInt("isFan") == 1) {
                            shopDesc.setFanMoney(desc.getInt("fanMoney"));
                        }


                        shopDesc.setBusihour(desc.getString("busihour"));
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                setTextview(shopDesc);
                            }
                        });


                        try {
                            if (desc.getInt("typeId") == 0) {

                                shopDesc.setDiscount(desc.getInt("discount"));


                            } else if (desc.getInt("typeId") == 1) {
                                shopDesc.setMinAmount(desc.getInt("minAmount"));
                                shopDesc.setAmount(desc.getInt("amount"));
                            }
                        } catch (JSONException e) {
                            // e.printStackTrace();
                            shopDesc.setMinAmount(0);
                            shopDesc.setAmount(0);
                            shopDesc.setDiscount(0);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    void setTextview(TakeawayShopDesc shopDesc) {
        if (shopDesc.getIsPay() == 1) {
            mPayLine.setText("支持在线支付");
        } else {
            mPayLine.setText("尚不支持在线支付");
        }
        if (shopDesc.getIsNes() == 1) {
            mRoupon.setText("新客户满" + shopDesc.getFullMoney() + "减" + shopDesc.getNewMoney());
        } else {
            mRoupon.setText("无新客户优惠");
        }
        if (shopDesc.getIsFan() == 1) {
            mReturn.setText("最高返现" + shopDesc.getFanMoney());
        } else {
            mReturn.setText("尚未有返现活动");
        }
        if (shopDesc.getDiscount() != 0) {
            mReduce.setText("全场" + shopDesc.getDiscount() + "折");
        } else if (shopDesc.getAmount() != 0 && shopDesc.getMinAmount() != 0) {
            mReduce.setText("全场满" + shopDesc.getMinAmount() + "减" + shopDesc.getAmount());

        } else {
            mReduce.setText("无满减活动");
        }

        mWorkTime.setText("营业时间：" + shopDesc.getBusihour());
        mTel.setText("联系电话：" + shopDesc.getTel());
        mAddress.setText("联系地址：" + shopDesc.getAddr());
        mAdvice.setText("举报或建议");
        rl_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + mTel.getText());
                intent.setData(data);
                startActivity(intent);
            }
        });


    }
}
