package util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by dec on 2017/1/4.
 */

public class Utils {

    public static void setToast(Context context, String msg){
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }




}
