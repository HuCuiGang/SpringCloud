package org.yufan.order.eums;

import lombok.Getter;


@Getter
public enum OrderStatusEnum{

    NO_PAYMENT(1, "未付款"),
    PAYMENT(2, "已付款"),
    DELIVER(3, "已发货"),
    SUCCESS(4,"交易成功"),
    CANCEL(5,"交易关闭"),
    RATE(6,"已评价");

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
