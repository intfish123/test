package com.example.test.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuartzCronDateUtils {

    /**
     * 日期转cron
     * @param date
     * @param dateFormat
     * @author wangxiaolei
     * @date 2020/6/1 9:07 下午
     * @return java.lang.String
     */
    public static String formatDateByPattern(Date date,String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * 日期转cron
     * @param date
     * @author wangxiaolei
     * @date 2020/6/1 9:07 下午
     * @return java.lang.String
     */
    public static String getCron(Date date){
        String dateFormat="ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date,dateFormat);
    }
}
