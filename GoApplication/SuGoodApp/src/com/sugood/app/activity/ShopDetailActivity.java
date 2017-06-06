package com.sugood.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.adapter.ShopTuanGouAdapter;
import com.sugood.app.entity.DianPing;
import com.sugood.app.entity.Shop;
import com.sugood.app.entity.TakeawayShop;
import com.sugood.app.global.Constant;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.HttpUtils;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.util.SetListViewHeight;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by wilk on 17/2/27 19:01
 * ganweib@gmail.com
 * describe:
 */

public class ShopDetailActivity extends BaseActivity {

    private SimpleDraweeView sdvHeaderAD;
    private TextView tvFoodShopName;
    private RatingBar ratbarShop;
    private LinearLayout llStar;
    private TextView tvContentAverage;
    private TextView tvGrade;
    private TextView tvShopAddr;
    private RelativeLayout rlTel;
    private TextView tvTel;
    private RelativeLayout rlWaimai;
    private TextView tvHadBuy;
    private TextView tvFavorable1;
    private TextView tvFavorable2;
    private ListView lvCombo;
    private RelativeLayout rlDianping1;
    private RelativeLayout rlDianping2;
    private RelativeLayout rl_nodp;
    private TextView tvEvaluate; //评价
    private RelativeLayout rlLookEvaluate;
    private SimpleDraweeView sdvAvatar1;
    private TextView tvUserName1;
    private LinearLayout llStarEvaluate1;
    private TextView tvScore1;
    private TextView tvEvaContet1;
    private SimpleDraweeView sdvAvatar2;
    private TextView tvUserName2;
    private LinearLayout llStarEvaluate2;
    private TextView tvScore2;
    private TextView tvEvaContet2;
    private ScrollView scrollView;
    private RelativeLayout rl_tel;
    private TextView tv_look;
    private RelativeLayout rl_wai;

    private String shopID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooddetail);
        getIntentData();
        initView();
        initNetData();
//        updateView();
    }

    /**
     * 数据请求回来后 更新界面
     */
    private void updateView(final Shop shop) {
        sdvHeaderAD.setImageURI(Constant.PHOTOBASEURL + shop.getBaoshop().getPhoto());
        tvFoodShopName.setText(shop.getBaoshop().getShopName());
        if (shop.getBaoshop().getIsWai().equals("1")) {
            rl_wai.setVisibility(View.VISIBLE);
            rl_wai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TakeawayShop takeawayShop = new TakeawayShop();
                    takeawayShop.setLogistics(3);
                    Intent intent = new Intent(ShopDetailActivity.this, TakeawayShopDetailActivity.class);
                    intent.putExtra("shopId", shopID);
                    intent.putExtra("shop", takeawayShop);
                    startActivity(intent);

                }
            });
        } else {
            rl_wai.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(shop.getBaoshop().getScore())) {
            ratbarShop.setRating(0);
        } else {
            ratbarShop.setRating(Integer.parseInt(shop.getBaoshop().getScore()));
        }
        tvContentAverage.setText(("¥" + shop.getBaoshop().getPrice() + "/人"));
        tvGrade.setText(shop.getBaoshop().getScore());
        tvShopAddr.setText(shop.getBaoshop().getAddr());
        tvTel.setText(shop.getBaoshop().getTel());
//        tvHadBuy.setText(shop.getBaoshop().);

        if (TextUtils.isEmpty(shop.getYouHui().getMinAmount()) || TextUtils.isEmpty(shop.getYouHui().getAmount())) {
            tvFavorable1.setVisibility(View.GONE);
        }
        tvFavorable2.setVisibility(View.GONE);
        tvFavorable1.setText("满" + shop.getYouHui().getMinAmount() + "减" + shop.getYouHui().getAmount());
        tvEvaluate.setText("评价(" + shop.getBaoshop().getNum() + ")");

        Log.e("SHOPDATY", "updateView: " + shop.getDianping());
        if (shop.getDianping().size() == 0 || shop.getDianping() == null) {
            rlDianping1.setVisibility(View.GONE);
            rlDianping2.setVisibility(View.GONE);
            tvEvaContet1.setVisibility(View.GONE);
            tvEvaContet2.setVisibility(View.GONE);
            rl_nodp.setVisibility(View.VISIBLE);
        } else {
            rlDianping1.setVisibility(View.VISIBLE);
            rlDianping2.setVisibility(View.VISIBLE);
            tvEvaContet1.setVisibility(View.VISIBLE);
            tvEvaContet2.setVisibility(View.VISIBLE);
            rl_nodp.setVisibility(View.GONE);
        }
        if (shop.getDianping().size() != 0 && shop.getDianping().size() == 1) {
            DianPing dianping = shop.getDianping().get(0);
            sdvAvatar1.setImageURI(Constant.PHOTOBASEURL + dianping.getFace());
            tvUserName1.setText(dianping.getNickname());
            tvEvaContet1.setText(dianping.getContents());
            tvScore1.setText(dianping.getScore());
            rlDianping1.setVisibility(View.VISIBLE);
            rlDianping2.setVisibility(View.GONE);
            tvEvaContet1.setVisibility(View.VISIBLE);
            tvEvaContet2.setVisibility(View.GONE);
        } else {

        }

        if (shop.getDianping().size() != 0 && shop.getDianping().size() == 2) {
            DianPing dianping = shop.getDianping().get(1);
            sdvAvatar2.setImageURI(Constant.PHOTOBASEURL + dianping.getFace());
            tvUserName2.setText(dianping.getNickname());
            tvEvaContet2.setText(dianping.getContents());
            tvScore2.setText(dianping.getScore());
            rlDianping1.setVisibility(View.VISIBLE);
            rlDianping2.setVisibility(View.VISIBLE);
            tvEvaContet1.setVisibility(View.VISIBLE);
            tvEvaContet2.setVisibility(View.VISIBLE);
        } else {

        }

        if (shop.getBaoTuan().size() > 0) {
            ShopTuanGouAdapter mAdapter = new ShopTuanGouAdapter(this, shop.getBaoTuan());
            lvCombo.setAdapter(mAdapter);
            SetListViewHeight setListViewHeight = new SetListViewHeight();
            setListViewHeight.setHegiht(lvCombo);
            scrollView.smoothScrollTo(0, 20);
            scrollView.setFocusable(true);


            lvCombo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent();
                    intent.setClass(ShopDetailActivity.this, TuanGouActivity.class);
                    intent.putExtra("shopId", shopID);
                    intent.putExtra("tuanId", shop.getBaoTuan().get(position).getTuanId());
                    startActivity(intent);


                }
            });
        }
    }

    /**
     * 获取上级传过来的intent 值
     */
    private void getIntentData() {
        shopID = getIntent().getStringExtra("shopId");

    }

    // 加载网络数据
    private void initNetData() {

        showLoading("");

        RequestParams params = new RequestParams();
        params.put("shopId", shopID);
        HttpUtil.post(Constant.SHOP, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("gan", "onSuccess: " + response.toString());
                Shop shop = JsonUtil.toObject(response.toString(), Shop.class);
                Log.e("shop", "onSuccess: " + shop.toString());
                updateView(shop);
                closeLoading();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("gan", "onFailure: " + "ssdad");
                Toast.makeText(ShopDetailActivity.this, "数据请求失败", Toast.LENGTH_SHORT).show();
                closeLoading();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("gan", "onFailure:," + errorResponse.toString());
                Toast.makeText(ShopDetailActivity.this, "数据请求失败", Toast.LENGTH_SHORT).show();
                closeLoading();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                closeLoading();
                Log.e("gan", "onFailure: " + errorResponse.toString());
                Toast.makeText(ShopDetailActivity.this, "数据请求失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        rl_wai = (RelativeLayout) findViewById(R.id.rl_wai);
        sdvHeaderAD = (SimpleDraweeView) findViewById(R.id.sdv_head_ad);
        tvFoodShopName = (TextView) findViewById(R.id.tv_foodShopName);
        llStar = (LinearLayout) findViewById(R.id.ll_star);
        ratbarShop = (RatingBar) findViewById(R.id.ratbarShop);
        tvContentAverage = (TextView) findViewById(R.id.tv_content_average);
        tvShopAddr = (TextView) findViewById(R.id.tv_shop_addr);
        rlTel = (RelativeLayout) findViewById(R.id.rl_tel);
        tvTel = (TextView) findViewById(R.id.tv_tel);
        rlWaimai = (RelativeLayout) findViewById(R.id.rl_waimai);
        tvHadBuy = (TextView) findViewById(R.id.tv_hadBuy);
        tvFavorable1 = (TextView) findViewById(R.id.tv_favorable1);
        tvFavorable2 = (TextView) findViewById(R.id.tv_favorable2);
        lvCombo = (ListView) findViewById(R.id.lv_group);
        rlDianping1 = (RelativeLayout) findViewById(R.id.rl_dianping1);
        rlDianping2 = (RelativeLayout) findViewById(R.id.rl_dianping2);
        rl_nodp = (RelativeLayout) findViewById(R.id.rl_nodp);
        tvEvaluate = (TextView) findViewById(R.id.tv_evaluate);
        rlLookEvaluate = (RelativeLayout) findViewById(R.id.rl_look_evaluate);
        sdvAvatar1 = (SimpleDraweeView) findViewById(R.id.sdv_avatar);
        tvUserName1 = (TextView) findViewById(R.id.tv_username);
        llStarEvaluate1 = (LinearLayout) findViewById(R.id.ll_stars);
        tvScore1 = (TextView) findViewById(R.id.tv_score);
        tvEvaContet1 = (TextView) findViewById(R.id.tv_evaluate_content);
        sdvAvatar2 = (SimpleDraweeView) findViewById(R.id.sdv_avatar_2);
        tvUserName2 = (TextView) findViewById(R.id.tv_userName2);
        llStarEvaluate2 = (LinearLayout) findViewById(R.id.ll_stars_2);
        tvScore2 = (TextView) findViewById(R.id.tv_score2);
        tvEvaContet2 = (TextView) findViewById(R.id.tv_evaluate_content2);
        tvGrade = (TextView) findViewById(R.id.tv_grade);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        tv_look = (TextView) findViewById(R.id.tv_look);
        rlTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + tvTel.getText());
                intent.setData(data);
                startActivity(intent);
            }
        });
        tv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("shopId", shopID);
                intent.setClass(ShopDetailActivity.this, PingLunActivity.class);
                startActivity(intent);
            }
        });

    }


}
