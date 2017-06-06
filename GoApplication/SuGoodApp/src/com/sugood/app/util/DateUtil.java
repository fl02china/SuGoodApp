package com.sugood.app.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

    private static final String TAG = "DateUtil";

    private static final String FORMAT1 = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMAT2 = "yyyyMMddHHmmss";
    private static SimpleDateFormat sdfNormal;
    private static SimpleDateFormat sdfStr;
    //一天的毫秒数 86400000 = 24*60*60*1000;
    private static final int millisPerDay = 86400000;
    //一小时的毫秒数 3600000 = 60*60*1000;
    private static final int millisPerHour = 3600000;
    //一分钟毫秒数 60*1000
    private static final int millsPerMin = 60000;

    static {
        sdfNormal = new SimpleDateFormat(FORMAT1);
        sdfStr = new SimpleDateFormat(FORMAT2);
    }

    public static String formatDate(String date) {
        String format = null;
        try {
            if (date == null || "".equals(date)) {
                return "";
            }
            format = sdfNormal.format(sdfNormal.parse(date));
        } catch (ParseException e) {
            Log.e(TAG, "formatDate", e);
            return date;
        }
        return format;
    }

    public static String formatDate(String date, String old_format, String format) {
        String ret_format = null;
        try {
            if (date == null || "".equals(date)) {
                return "";
            }
            SimpleDateFormat sdf0 = new SimpleDateFormat(old_format, Locale.getDefault());
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            ret_format = sdf.format(sdf0.parse(date));
        } catch (ParseException e) {
            return "";
        }
        return ret_format;
    }

    /**
     * get current applyTime,applyTime pattern  : yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentDateTime() {
        String format = null;
        format = sdfNormal.format(new Date());
        return format;
    }

    public static String formatDateStr(String date) {
        String format = null;
        try {
            Date parseDate = sdfStr.parse(date);
            format = sdfNormal.format(parseDate);
        } catch (ParseException e) {
            Log.e(TAG, "formatDateStr", e);
            return date;
        }
        return format;
    }

    /**
     * 获取当前时间的字符串
     */
    public static String getCurrentDateStr() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int w = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        String mDate = c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1)
                + "月" + c.get(Calendar.DATE) + "日  " + weekDays[w];
        return mDate;
    }

    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    public static String getCurrentDataHour() {
        return getCurrentTime("yyyy-MM-dd HH:mm");
    }

    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentYear() {
        return getCurrentTime("yyyy");
    }

    public static String getCurrentMonth() {
        return getCurrentTime("MM");
    }

    public static String getCurrentDate() {
        return getCurrentTime("dd");
    }

    public static String getCurrentHour() {
        return getCurrentTime("HH");
    }

    public static String getCurrentMinute() {
        return getCurrentTime("mm");
    }

    public static String formatTime(String format, int time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String outTime = sdf.format(date);
        return outTime;
    }

    public static String formatTime(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String outTime = sdf.format(date);
        return outTime;
    }

    public static String add(int field, int value, String format) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.add(field, value);

        return formatTime(format, gregorianCalendar.getTime());
    }

    public static String addYear(int year, String format) {
        return add(GregorianCalendar.YEAR, year, format);
    }

    public static String addMonth(int month, String format) {
        return add(GregorianCalendar.MONTH, month, format);
    }

    public static String addDate(int date, String format) {
        return add(GregorianCalendar.DATE, date, format);
    }

    public static String addYear(int year) {
        return addYear(year, "yyyy");
    }

    public static String addMonth(int month) {
        return addMonth(month, "MM");
    }

    public static String addDate(int date) {
        return addDate(date, "dd");
    }

    public static long subDate(String firstDate, String secondDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date d1 = sdf.parse(firstDate);
            Date d2 = sdf.parse(secondDate);
            long diff = d1.getTime() - d2.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            return days;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String subHowDate(String firstDate, String secondDate, String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            Date date1 = sdf.parse(firstDate);
            Date date2 = sdf.parse(secondDate);
            long diff = date1.getTime() - date2.getTime();
            if (diff < millsPerMin) {
                return diff / 1000 + "秒";
            } else if (diff < millisPerHour) {
                return diff / millsPerMin + "分钟";
            } else if (diff < millisPerDay) {
                return diff / millisPerHour + "小时";
            } else {
                return diff / millisPerDay + "天";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getMonthDay(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        String month = simpleDateFormat.format(date);
        SimpleDateFormat format1 = new SimpleDateFormat("DD");
        String day = format1.format(date);
        String monthDay = month + "yue" + day + "日";
        return monthDay;
    }

    public static String getDay(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String day = simpleDateFormat.format(date) + "日";
        return day;
    }

    public static String getMonthDay_date(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time = new Long(date);
        Date dt = new Date(time);
        String date1 = simpleDateFormat.format(dt);
        return date1;
    }

    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }


}
