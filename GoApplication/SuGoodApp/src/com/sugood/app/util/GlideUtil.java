package com.sugood.app.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sugood.app.R;

/**
 * Package :com.sugood.app.util
 * Description :
 * Author :Rc3
 * Created at :2017/3/28 10:07.
 */

public class GlideUtil {

    public static void displayImage(String url, ImageView imageView) {
        displayImage(R.drawable.defasd_111, url, imageView);
    }

    public static void displayImage(int ids, String url, ImageView imageView) {
        if (imageView.getContext() == null) {
            throw new IllegalArgumentException("image content null");
        }
        Glide.with(imageView.getContext()).load(url).error(R.drawable.defasd_111).into(imageView);

    }
}
