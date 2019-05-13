package org.yufan.pay.enums;

import lombok.Getter;


@Getter
public enum PayTypeEnum {

    MOBILE(1, "手机APP支付"),
    MOBILE_WEB(2, "手机网页支付"),
    PC(3,"电脑网站支付");

    private Integer code;

    private String message;

    PayTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
