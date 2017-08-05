package com.sugood.app.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sugood.app.R;
import com.sugood.app.global.Constant;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/4.
 */

public class TuanGouCodeDetailActivity extends BaseActivity {

    @BindView(R.id.code)
    TextView code;
    @BindView(R.id.img_code)
    ImageView imgCode;
    @BindView(R.id.img)
    SimpleDraweeView img;
    @BindView(R.id.shopname)
    TextView titlename;
    @BindView(R.id.usetime)
    TextView usetime;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.line1)
    LinearLayout line1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codedetail);
        ButterKnife.bind(this);
        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        final String tuanCode = bundle.getString("tuanCode");
        code.setText("团购劵号：" + tuanCode);
        titlename.setText(bundle.getString("title"));
        usetime.setText(bundle.getString("faildate"));
        img.setImageURI(Constant.PHOTOBASEURL+bundle.getString("photo"));
        line1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.putExtra("tuanId", bundle.getString("tuanId"));
                intent.putExtra("shopId", bundle.getString("shopId"));
                intent.setClass(getApplicationContext(), TuanGouActivity.class);
                startActivity(intent);

            }
        });
        Bitmap qrBitmap = generateBitmap(tuanCode, 400, 400);
        imgCode.setImageBitmap(qrBitmap);
    }

    void startIntent(Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtra("tuanId", bundle.getString("tuanId"));
        intent.putExtra("shopId", bundle.getString("shopId"));
        intent.setClass(TuanGouCodeDetailActivity.this, TuanGouActivity.class);
        startActivity(intent);
    }

    private Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
