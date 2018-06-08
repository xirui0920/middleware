package com.huilianyi.middleware.util;

import cn.hutool.core.util.StrUtil;

import java.util.Date;

/**
 * DateUtil.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/28 下午2:35
 */
public class DateUtil {
    /**
     * 将时区时间转换为标准北京时间
     * 格式为：2018-05-07T12:14:04Z 或者 2018-05-07T12:14:04
     *
     * @param timeZone 时区时间
     * @return 标准北京时间
     */
    public static Date conversionTimeZoneToDate(String timeZone) {
        String timeZoneFlag = "T";
        if (StrUtil.isEmpty(timeZone)) {
            return null;
        }
        if (!timeZone.contains(timeZoneFlag)) {
            return cn.hutool.core.date.DateUtil.parseDate(timeZone);
        }
        timeZone = timeZone.replaceAll(timeZoneFlag, " ").replaceAll("Z", "");
        String hour = timeZone.substring(timeZone.indexOf(" ") + 1, timeZone.indexOf(":"));
        return cn.hutool.core.date.DateUtil.parseDateTime(timeZone.replaceAll(" " + hour + ":", " " + dealHour(hour) + ":"));
    }

    /**
     * 转换小时
     *
     * @param hour hour
     * @return hour+8
     */
    private static String dealHour(String hour) {
        return (Integer.parseInt(hour) + 8) < 10 ? "0" + (Integer.parseInt(hour) + 8)
                : String.valueOf(Integer.parseInt(hour) + 8);
    }
}
