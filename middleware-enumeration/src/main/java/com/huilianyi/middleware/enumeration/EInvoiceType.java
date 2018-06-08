package com.huilianyi.middleware.enumeration;

import lombok.Getter;

/**
 * EInvoiceType.java
 *
 * @author : Gooliang Young
 * @date : 2018/6/3 下午12:01
 */
@Getter
public enum EInvoiceType {
    /**
     * 增值税专用发票
     */
    ZZP("01", "增值税专用发票"),
    ZPP("04", "增值税普通发票"),
    ZPD("10", "增值税普通发票(电子)"),
    ZPC("11", "增值税普通发票(卷式)"),
    AUTO("03", "机动车销售统一发票"),
    ROAD("004", "高速公路通行费发票"),
    UNROAD("005", "非高速公路通行费发票"),
    CT("007", "代扣代缴税收通用缴款凭证"),
    SZZ("008", "海关进口增值税专用缴款书"),
    FPT("009", "农产品收购发票"),
    GZZ("010", "货物运输业增值税专用发票");

    private String code;
    private String name;

    EInvoiceType(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
