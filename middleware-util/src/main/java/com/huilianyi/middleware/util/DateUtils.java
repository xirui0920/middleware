package com.huilianyi.middleware.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.huilianyi.middleware.common.CommonValue;

import java.util.Date;

/**
 * DateUtils.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/28 下午2:35
 */
public class DateUtils {
    /**
     * 将时区时间转换为标准北京时间
     * 格式为：2018-05-07T12:14:04Z 或者 2018-05-07T12:14:04
     *
     * @param timeZone 时区时间
     * @return 标准北京时间
     */
    public static String conversionTimeZoneToString(String timeZone) {
        return DateUtil.format(conversionTimeZoneDateTime(timeZone), DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 将时区时间转换为标准北京时间
     * 格式为：2018-05-07T12:14:04Z 或者 2018-05-07T12:14:04
     *
     * @param timeZone 时区时间
     * @return 标准北京时间
     */
    public static Date conversionTimeZoneToDateTime(String timeZone) {
        return conversionTimeZoneDateTime(timeZone);
    }


    private static Date conversionTimeZoneDateTime(String timeZone) {
        if (StrUtil.isEmpty(timeZone)) {
            return null;
        }
        if (!timeZone.contains(CommonValue.TIME_ZONE_DATE_TIME_FLAG_T)) {
            return DateUtil.parseDateTime(timeZone);
        } else if (!timeZone.contains(CommonValue.TIME_ZONE_DATE_TIME_FLAG_Z)) {
            return parseTimeZoneDateTime(timeZone, CommonValue.TIME_ZONE_DATE_PATTERN_WITH_T);
        }
        return parseTimeZoneDateTime(timeZone, timeZone.length() > CommonValue.TWENTY ? CommonValue.TIME_ZONE_DATE_PATTERN_WITH_MS : CommonValue.TIME_ZONE_DATE_PATTERN_WITH_T_Z);
    }

    /**
     * 转换日期
     *
     * @param dateTime    dateTime
     * @param datePattern datePattern
     * @return date
     */
    private static Date parseTimeZoneDateTime(String dateTime, String datePattern) {
        return DateUtil.offsetHour(DateUtil.parse(dateTime, datePattern), CommonValue.EIGHT);
    }
}
