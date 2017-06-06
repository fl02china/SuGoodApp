package com.sugood.app.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version 1.0
 * @Author
 * @Date 2015-3-28
 * @Desc 工具类
 */

public class Utils {

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "unknown";
        }
    }

    /**
     * 获取版本号(内部识别号)
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 判断网络连接是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean isAvailable = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return isAvailable;
        } else {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo == null) {
                return false;
            }

            // 如果仅仅是用来判断网络连接，则可以使用下面这行
            isAvailable = networkInfo.isAvailable();

//            NetworkInfo[] info = cm.getAllNetworkInfo();
//            if (info != null) {
//                for (int i = 0; i < info.length; i++) {
//                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//                        return true;
//                    }
//                }
//            }
        }
        return isAvailable;
    }

    /**
     * 判断GPS是否打开
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = ((LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE));

        List<String> accessibleProviders = lm.getProviders(true);

        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    /**
     * 判断WIFI是否打开
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn
                .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * 判断是否是移动数据(3G)网络
     */
    public static boolean is3rd(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是wifi网络
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 使用文件通道的方式复制文件
     *
     * @param s 源文件
     * @param t 复制到的新文件
     */
    public static void fileChannelCopy(String s, String t) {

        if (new File(s).exists() == false) {
            return;
        }

        FileInputStream fi = null;

        FileOutputStream fo = null;

        FileChannel in = null;

        FileChannel out = null;

        try {

            fi = new FileInputStream(s);

            fo = new FileOutputStream(t);

            in = fi.getChannel();// 得到对应的文件通道

            out = fo.getChannel();// 得到对应的文件通道

            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                fi.close();

                in.close();

                fo.close();

                out.close();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    public static void delFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists() == false) {
                return;
            }

            file.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件夹下的所有文件
     *
     * @param filePath       文件夹路径
     * @param deleteThisPath 是否删除给定路径下的根目录文件夹
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static boolean copyRawToFile(Context context, int rawResId, String destFile) {
        File file = new File(destFile);
        if (!file.exists()) {
            InputStream stream = context.getResources().openRawResource(rawResId);
            try {
                FileOutputStream outputStream = new FileOutputStream(destFile);
                byte[] buffer = new byte[400000];
                int count = 0;

                while ((count = stream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, count);
                }

                outputStream.close();
                stream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 检测是否符合金额类型
     *
     * @param str
     * @return
     */
    public static boolean checkMoney(String str) {
        return str.matches("^(([1-9]\\d{0,7})|0)(\\.\\d{0,2})?$");

    }

    /**
     * 检测是否符合两个中文以上的姓名
     *
     * @param str
     * @return
     */
    public static boolean checkCNName(String str) {
        return str.matches("^[\\u4e00-\\u9fa5]{2,}$");
    }

    /**
     * 检测是否符合一个中文以上的姓名
     *
     * @param str
     * @return
     */
    public static boolean checkOneCNName(String str) {
        return str.matches("^[\\u4e00-\\u9fa5]{1,}$");
    }

    public static String formatDouble(Double number) {
//        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
//        nf.setGroupingUsed(false);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(number);
    }

    public static int getResource(Context context, String imageName) {
        int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        return resId;
    }

    //金额验证
    public static boolean isNumber(String str) {


        Pattern pattern = Pattern.compile("\\d+(?:\\.\\d{2})?'"); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 保存方法
     */
    public static File saveImag(Bitmap btImage) {
        FileOutputStream out = null;
        File file = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) // 判断是否可以对SDcard进行操作
        {    // 获取SDCard指定目录下
            String sdCardDir = Environment.getExternalStorageDirectory() + "/Intely/avatar/";
            File dirFile = new File(sdCardDir);  //目录转化成文件夹
            if (!dirFile.exists()) {              //如果不存在，那就建立这个文件夹
                dirFile.mkdirs();
            }                          //文件夹有啦，就可以保存图片啦
            file = new File(sdCardDir, System.currentTimeMillis() + ".jpg");// 在SDcard的目录下创建图片文,以当前时间为其命名

            try {
                out = new FileOutputStream(file);
                btImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
                System.out.println("_________保存到____sd______指定目录文件夹下____________________");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  file;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

}
