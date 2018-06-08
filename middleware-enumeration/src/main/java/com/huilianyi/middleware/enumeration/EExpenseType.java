package com.huilianyi.middleware.enumeration;

import lombok.Getter;

/**
 * EExpenseType.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/4 下午3:39
 */
@Getter
public enum EExpenseType {
    /**
     * 日常报销单
     */
    DAILY_EXPENSE(1001),
    /**
     * 差旅报销单
     */
    TRAVEL_EXPENSE(1002),
    /**
     * 费用报销单
     */
    COST_EXPENSE(1003);

    private Integer typeCode;

    EExpenseType(Integer typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * 判断是哪类报销单
     *
     * @param typeCode typeCode
     * @return EExpenseType
     */
    public static EExpenseType whichExpense(Integer typeCode) {
        for (EExpenseType expenseType : EExpenseType.values()) {
            if (expenseType.getTypeCode().equals(typeCode)) {
                return expenseType;
            }
        }
        return null;
    }
}
