package com.sugood.app.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.common.internal.Objects;
import com.facebook.drawee.view.SimpleDraweeView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sugood.app.R;
import com.sugood.app.application.SugoodApplication;
import com.sugood.app.entity.DianPing;
import com.sugood.app.entity.ShopCarProduct;
import com.sugood.app.entity.TakeawayShop;
import com.sugood.app.entity.TakeawayShopInfo;
import com.sugood.app.entity.Tuan;
import com.sugood.app.entity.TuanGouDetail;
import com.sugood.app.global.Constant;
import com.sugood.app.util.GlideUtil;
import com.sugood.app.util.HttpUtil;
import com.sugood.app.util.JsonUtil;
import com.sugood.app.view.HorizontalListView;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by wilk on 17/3/10 14:14
 * ganweib@gmail.com
 * describe:
 */

public class TuanGouActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private SimpleDraweeView sdvHeaderTuanGouAd;
    private TextView tvTuanGouName;
    private TextView tvPrice;
    private TextView tvTuanGouAverage;
    private TextView tvTuanGouAddr;
    private TextView tvTuanGouEvalute;
    private RelativeLayout rlLookEvalute;
    private SimpleDraweeView sdvAvatar1;
    private TextView tvUserName1;
    private RatingBar ratbarTuangou1;
    private TextView tvScore1;
    private TextView tvEvaContet1;
    private SimpleDraweeView sdvAvatar2;
    private TextView tvUserName2;
    private RatingBar ratbarTuangou2;
    private TextView tvScore2;
    private TextView tvEvaContet2;
    private RelativeLayout rlTel;
    private TextView tvTel;
    private WebView tvTuanGouMsg;
    private TextView tvLookMore;
//    private RelativeLayout rlMsg1;
//    private SimpleDraweeView sdvMsg1;
//    private TextView tvMsg1;
//    private RelativeLayout rlMsg2;
//    private SimpleDraweeView sdvMsg2;
//    private TextView tvMsg2;
//    private RelativeLayout rlMsg3;
//    private SimpleDraweeView sdvMsg3;
    // TextView tvMsg3;
    private WebView tvBuyNotes; //购买须知
    private TextView tvLookMoreShop; //查看更多的店
    private LinearLayout llPl1;
    private LinearLayout llPl2;
    private Button btnBuyNow;
    private String[] screens;
    private HorizontalListView mScreenList;

    private String mShopId;
    private String mTuanId;
    private ScrollView mScrollView;
    private TuanGouDetail mTuanGoudetail;
    TakeawayShopInfo.EleBean shop = new TakeawayShopInfo.EleBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuangou);
        getIntentData();
        initView();
        initNetData();

    }

    private void getIntentData() {
        mShopId = getIntent().getStringExtra("shopId");
        mTuanId = getIntent().getStringExtra("tuanId");

    }


    /**
     * 识别控件
     */
    private void initView() {
        mScreenList = (HorizontalListView) findViewById(R.id.screen);
        mScrollView = (ScrollView) findViewById(R.id.scroller);
        mScreenList.setScrollParent(mScrollView);
        mScreenList.setOnItemClickListener(this);
        sdvHeaderTuanGouAd = (SimpleDraweeView) findViewById(R.id.sdv_head_tuangou_ad);
        tvTuanGouName = (TextView) findViewById(R.id.tv_foodTuanGouName);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvTuanGouAverage = (TextView) findViewById(R.id.tv_tuangou_average);
        tvTuanGouAddr = (TextView) findViewById(R.id.tv_tuangou_addr);
        tvTuanGouEvalute = (TextView) findViewById(R.id.tv_tuangou_evaluate);
        rlLookEvalute = (RelativeLayout) findViewById(R.id.rl_look_evaluate);
        sdvAvatar1 = (SimpleDraweeView) findViewById(R.id.sdv_avatar);
        tvUserName1 = (TextView) findViewById(R.id.tv_userName1);
        ratbarTuangou1 = (RatingBar) findViewById(R.id.ratbarTuanGou1);
        tvScore1 = (TextView) findViewById(R.id.tv_score);
        tvEvaContet1 = (TextView) findViewById(R.id.tv_evaluate_content);
        sdvAvatar2 = (SimpleDraweeView) findViewById(R.id.sdv_avatar_2);
        tvUserName2 = (TextView) findViewById(R.id.tv_userName2);
        ratbarTuangou2 = (RatingBar) findViewById(R.id.ratbarTuanGou2);
        tvScore2 = (TextView) findViewById(R.id.tv_score2);
        tvEvaContet2 = (TextView) findViewById(R.id.tv_evaluate_content2);
        rlTel = (RelativeLayout) findViewById(R.id.rl_tel);
        tvTel = (TextView) findViewById(R.id.tv_tel);
        tvTuanGouMsg = (WebView) findViewById(R.id.tv_tuangouMsg);
        tvLookMore = (TextView) findViewById(R.id.tv_look_more);
//        rlMsg1 = (RelativeLayout) findViewById(R.id.rl_msg1);
//        sdvMsg1 = (SimpleDraweeView) findViewById(R.id.sdv_msg1);
//        tvMsg1 = (TextView) findViewById(R.id.tv_msg2);
//        rlMsg2 = (RelativeLayout) findViewById(R.id.rl_msg2);
//        sdvMsg2 = (SimpleDraweeView) findViewById(R.id.sdv_msg2);
//        tvMsg2 = (TextView) findViewById(R.id.tv_msg2);
//        rlMsg3 = (RelativeLayout) findViewById(R.id.rl_msg3);
//        sdvMsg3 = (SimpleDraweeView) findViewById(R.id.sdv_msg3);
       // tvMsg3 = (TextView) findViewById(R.id.tv_msg3);
        tvBuyNotes = (WebView) findViewById(R.id.tv_buyNotes);
        tvLookMoreShop = (TextView) findViewById(R.id.tv_look_moreShop);
        llPl1 = (LinearLayout) findViewById(R.id.ll_pl1);
        llPl2 = (LinearLayout) findViewById(R.id.ll_pl2);
        btnBuyNow = (Button) findViewById(R.id.btn_buynow);
        rlLookEvalute = (RelativeLayout) findViewById(R.id.rl_look_evaluate);
        rlLookEvalute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("shopId", mShopId);
                intent.putExtra("tuanId", mTuanId);
                intent.setClass(TuanGouActivity.this, TuamPIngLunActivity.class);
                startActivity(intent);
            }
        });
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SugoodApplication.isLogin) {
                    if (mTuanGoudetail != null) {
                        add();
                        Intent intent = new Intent();
                        shop.setLogistics(0.00);
                        shop.setShopId(Integer.parseInt(mShopId));
                        shop.setShopName(mTuanGoudetail.getTuan().getTitle());
                        intent.putExtra("shop", shop);
                        intent.putExtra("type", "tuan");
                        intent.setClass(TuanGouActivity.this, TakeawaySubmitOrderActivity.class);
                        startActivity(intent);

                    }

                } else {
                    startActivity(new Intent(TuanGouActivity.this, LoginActivity.class));
                }
            }
        });
        rlTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + tvTel.getText());
                intent.setData(data);
                startActivity(intent);
            }
        });
    }


    private void add() {
        SugoodApplication.getInstance().getShopCarProductList().clear();
        List<ShopCarProduct> shopCarList = SugoodApplication.getInstance().getShopCarProductList();
        shopCarList.clear();
        ShopCarProduct shopCar = new ShopCarProduct();
        shopCar.setFoodAmount(1);
        shopCar.setFoodName(mTuanGoudetail.getTuan().getTitle());
        shopCar.setProductId(mTuanGoudetail.getTuan().getTuanId());
        shopCar.setFoodPrice(mTuanGoudetail.getTuan().getTuanPrice());
        shopCarList.add(shopCar);
        SugoodApplication.getInstance().setShopCarProductList(shopCarList);

    }

    /**
     * 网络请求数据
     */
    private void initNetData() {

        RequestParams params = new RequestParams();
        params.put("shopId", mShopId);
        params.put("tuanId", mTuanId);
        System.out.println("111mShopId:"+mShopId);
        System.out.println("111mTuanId:"+mTuanId);
        HttpUtil.post(Constant.SUGOODTUANGOU, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("ss", "onSuccess: " + response.toString());
                mTuanGoudetail = JsonUtil.toObject(response.toString(), TuanGouDetail.class);
                updateView();
            }
        });


    }

    private void updateView() {

        sdvHeaderTuanGouAd.setImageURI(Constant.PHOTOBASEURL + mTuanGoudetail.getTuan().getPhoto());
        tvTuanGouName.setText(mTuanGoudetail.getTuan().getTitle());
        tvPrice.setText("¥" + mTuanGoudetail.getTuan().getTuanPrice());
        tvTuanGouAverage.setText("人均消费：" + mTuanGoudetail.getTuan().getPrice());
        tvTuanGouAddr.setText(mTuanGoudetail.getShop().getAddr());
        tvTuanGouEvalute.setText("评价(" + mTuanGoudetail.getShop().getNum() + ")");

        if (mTuanGoudetail.getDianping().size() == 0) {
            llPl2.setVisibility(View.GONE);
            llPl1.setVisibility(View.GONE);
        } else if (mTuanGoudetail.getDianping().size() == 1) {
            llPl2.setVisibility(View.GONE);
            DianPing dp = mTuanGoudetail.getDianping().get(0);
            sdvAvatar1.setImageURI(Constant.PHOTOBASEURL + dp.getFace());
            tvUserName1.setText(dp.getNickname());
            ratbarTuangou1.setNumStars(Integer.parseInt(dp.getScore()));
            tvScore1.setText(dp.getScore());
            tvEvaContet1.setText(dp.getContents());
        } else if (mTuanGoudetail.getDianping().size() == 2) {
            DianPing dp = mTuanGoudetail.getDianping().get(0);
            sdvAvatar1.setImageURI(Constant.PHOTOBASEURL + dp.getFace());
            tvUserName1.setText(dp.getNickname());
            ratbarTuangou1.setNumStars(Integer.parseInt(dp.getScore()));
            tvScore1.setText(dp.getScore());
            tvEvaContet1.setText(dp.getContents());

            DianPing dp2 = mTuanGoudetail.getDianping().get(1);
            sdvAvatar2.setImageURI(Constant.PHOTOBASEURL + dp2.getFace());
            tvUserName2.setText(dp2.getNickname());
            ratbarTuangou2.setNumStars(Integer.parseInt(dp2.getScore()));
            tvScore2.setText(dp2.getScore());
            tvEvaContet2.setText(dp2.getContents());

        } else {
            llPl2.setVisibility(View.GONE);
            llPl1.setVisibility(View.GONE);
        }

        tvTel.setText(mTuanGoudetail.getShop().getTel());
        tvTuanGouMsg.loadDataWithBaseURL(null, mTuanGoudetail.getTuan().getDetails(), "text/html", "utf-8", null);
        tvTuanGouMsg.getSettings().setJavaScriptEnabled(true);
        if (null == mTuanGoudetail.getTuan().getThumb()) {
            mScreenList.setVisibility(View.GONE);
        } else {
            screens = mTuanGoudetail.getTuan().getThumb().toArray(new String[mTuanGoudetail.getTuan().getThumb().size()]);
            ScreenAdapter adapter = new ScreenAdapter(this, screens);
            mScreenList.setAdapter(adapter);
        }

//        if (null == mTuanGoudetail.getTuan().getThumb()) {
//            rlMsg1.setVisibility(View.GONE);
//            rlMsg2.setVisibility(View.GONE);
//            rlMsg3.setVisibility(View.GONE);
//        } else if (mTuanGoudetail.getTuan().getThumb().size() == 0) {
//            rlMsg1.setVisibility(View.GONE);
//            rlMsg2.setVisibility(View.GONE);
//            rlMsg3.setVisibility(View.GONE);
//        } else if (mTuanGoudetail.getTuan().getThumb().size() == 1) {
//            rlMsg1.setVisibility(View.VISIBLE);
//            rlMsg2.setVisibility(View.GONE);
//            rlMsg3.setVisibility(View.GONE);
//            sdvMsg1.setImageURI(Constant.PHOTOBASEURL + mTuanGoudetail.getTuan().getThumb().get(0));
////            GlideUtil.displayImage(Constant.PHOTOBASEURL +  mTuanGoudetail.getTuan().getThumb().get(0), sdvMsg1);
//        } else if (mTuanGoudetail.getTuan().getThumb().size() == 2) {
//            rlMsg1.setVisibility(View.VISIBLE);
//            rlMsg2.setVisibility(View.VISIBLE);
////            rlMsg3.setVisibility(View.GONE);
//            GlideUtil.displayImage(Constant.PHOTOBASEURL + mTuanGoudetail.getTuan().getThumb().get(0), sdvMsg1);
////            GlideUtil.displayImage(Constant.PHOTOBASEURL +  mTuanGoudetail.getTuan().getThumb().get(1), sdvMsg2);
//            sdvMsg1.setImageURI(Constant.PHOTOBASEURL + mTuanGoudetail.getTuan().getThumb().get(0));
//            sdvMsg2.setImageURI(Constant.PHOTOBASEURL + mTuanGoudetail.getTuan().getThumb().get(1));
//        } else if (mTuanGoudetail.getTuan().getThumb().size() >= 3) {
//            rlMsg1.setVisibility(View.VISIBLE);
//            rlMsg2.setVisibility(View.VISIBLE);
//            rlMsg3.setVisibility(View.VISIBLE);
////            GlideUtil.displayImage(Constant.PHOTOBASEURL +  mTuanGoudetail.getTuan().getThumb().get(0), sdvMsg1);
////            GlideUtil.displayImage(Constant.PHOTOBASEURL +  mTuanGoudetail.getTuan().getThumb().get(1), sdvMsg2);
////            GlideUtil.displayImage(Constant.PHOTOBASEURL +  mTuanGoudetail.getTuan().getThumb().get(2), sdvMsg3);
//            Log.e("IMAF", "updateView: " + mTuanGoudetail.getTuan().getThumb().get(0) + "  ---" + mTuanGoudetail.getTuan().getThumb().get(1) + "++" + mTuanGoudetail.getTuan().getThumb().get(2));
//            sdvMsg1.setImageURI(Constant.PHOTOBASEURL + mTuanGoudetail.getTuan().getThumb().get(0));
//            sdvMsg2.setImageURI(Constant.PHOTOBASEURL + mTuanGoudetail.getTuan().getThumb().get(1));
//            sdvMsg3.setImageURI(Constant.PHOTOBASEURL + mTuanGoudetail.getTuan().getThumb().get(2));
//        }
//        rlMsg1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(TuanGouActivity.this, BigImageViewActivity.class);
//                intent.putExtra("url", mTuanGoudetail.getTuan().getThumb().get(0));
//                startActivity(intent);
//            }
//        });
//        rlMsg2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(TuanGouActivity.this, BigImageViewActivity.class);
//                intent.putExtra("url", mTuanGoudetail.getTuan().getThumb().get(1));
//                startActivity(intent);
//            }
//        });
//        rlMsg3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(TuanGouActivity.this, BigImageViewActivity.class);
//                intent.putExtra("url", mTuanGoudetail.getTuan().getThumb().get(2));
//                startActivity(intent);
//            }
//        });
        tvBuyNotes.loadDataWithBaseURL(null, mTuanGoudetail.getTuan().getInstructions(), "text/html", "utf-8", null);
        tvBuyNotes.getSettings().setJavaScriptEnabled(true);

    }

    private String parsePrice(int price) {
        String thePrice = price + "";
        return thePrice.substring(0, thePrice.length() - 2) + "." + thePrice.substring(thePrice.length() - 2);
    }

    private String parsePriceInt(int price) {
        String thePrice = price + "";
        return thePrice.substring(0, thePrice.length() - 2);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(TuanGouActivity.this, BigImageViewActivity.class);
        intent.putExtra("url", mTuanGoudetail.getTuan().getThumb().get(position));
        startActivity(intent);
    }

    private class ScreenAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater mInflater;
        private String[] mUrls;
        //private Picasso mPicasso;

        public ScreenAdapter(Context context, String[] urls) {
            // TODO Auto-generated constructor stub
            mContext = context;
            mUrls = urls;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //  mPicasso = Picasso.with(mContext);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mUrls == null ? 0 : mUrls.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_screen2, null);
                holder.image = (SimpleDraweeView) convertView.findViewById(R.id.icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (null != mTuanGoudetail.getTuan().getThumb()) {
//                Glide.with(mContext).load(Constant.PHOTOBASEURL + mUrls[position])
//                        .error(R.drawable.defasd_111)
//                        .override(110, 110)
//                        //.fit()
//                        .into(holder.image);
                holder.image.setImageURI(Constant.PHOTOBASEURL + mUrls[position]);

            } else {
                holder.image.setImageResource(R.drawable.defasd_111);
            }

            return convertView;
        }

        private class ViewHolder {
            SimpleDraweeView image;
        }
    }

}
