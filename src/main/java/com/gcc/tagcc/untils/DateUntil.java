package com.gcc.tagcc.untils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gcc on 2018/3/13.
 */
public class DateUntil {

    /**
     * 获取当前时间，String类型
     */
    public static String getCurrentDate(String format ){
        return  new SimpleDateFormat(format).format(System.currentTimeMillis());
    }

    /**
     * 以友好的方式显示时间
     */
    public static String friendlyTime(Date time) {
        //获取time距离当前的秒数
        int ct = (int)((System.currentTimeMillis() - time.getTime())/1000);
        if(ct == 0) {
            return "刚刚";
        }

        if(ct > 0 && ct < 60) {
            return ct + "秒前";
        }

        if(ct >= 60 && ct < 3600) {
            return Math.max(ct / 60,1) + "分钟前";
        }

        if(ct >= 3600 && ct < 86400) {
            return ct / 3600 + "小时前";
        }

        if(ct >= 86400 && ct < 2592000){ //86400 * 30
            int day = ct / 86400 ;
            return day + "天前";
        }

        if(ct >= 2592000 && ct < 31104000) { //86400 * 30
            return ct / 2592000 + "月前";
        }

        return ct / 31104000 + "年前";
    }

    /**
     * 将字符串转换为Date类型
     */
    public static Date stringToDate(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
