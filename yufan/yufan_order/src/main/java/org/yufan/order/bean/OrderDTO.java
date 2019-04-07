package org.yufan.order.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
public class OrderDTO {

    /**
     * 1.在线支付  2.货到付款
     */
    @NotNull(message = "支付方式不能为空")
    private Integer paymentType;


    /**
     * 下单商品id
     */
    @NotNull(message = "下单商品id不能为空")
    private List<Long> skuIds;

    /**
     * 发票类型(0无发票1普通发票，2电子发票，3增值税发票)
     */
    private Integer invoiceType;

    /**
     * 订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
     */
    private Integer sourceType;


    @NotNull(message = "收货地址id不能为空")
    public Long shippingId;

    /**
     * 买家留言
     */
    private String buyerMessage;



}
