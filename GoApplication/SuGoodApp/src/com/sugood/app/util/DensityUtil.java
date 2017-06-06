package com.sugood.app.util;

import android.content.Context;

/**
 * Package :com.android.supermarket.utils
 * Description :
 * Author :Rc3
 * Created at :2017/3/2 13:07.
 */

public class DensityUtil {

    public static int dip2px(Context content,float dpValue){
        final float scale=content.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
