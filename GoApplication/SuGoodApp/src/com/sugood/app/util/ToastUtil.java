package com.sugood.app.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by dec on 2016/12/20.
 */

public class ToastUtil {

    public static void setToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
