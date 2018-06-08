package com.huilianyi.middleware.util;

import cn.hutool.core.util.NumberUtil;
import com.huilianyi.middleware.common.CommonValue;

/**
 * TaxAmountUtil.java
 * 税额工具类
 *
 * @author : Gooliang Young
 * @date : 2018/5/28 下午2:39
 */
public class TaxAmountUtil {
    /**
     * 计算税额，保留两位小数
     *
     * @param taxAmount 总金额
     * @param taxRate   税率
     * @return 税额
     */
    private static double computeRax(double taxAmount, double taxRate) {
        double returnValue;
        returnValue = NumberUtil.div(NumberUtil.mul(taxAmount, taxRate), NumberUtil.add(CommonValue.ONE.doubleValue(), taxRate));
        returnValue = NumberUtil.round(returnValue, CommonValue.TWO).doubleValue();
        return returnValue;
    }

}
