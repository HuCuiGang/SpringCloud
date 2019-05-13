package org.yufan.order.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "yf_order")
@Getter
@Setter
public class Order {
    /**
     * 订单id
     */
    @Id
    @Column(name = "order_id")
    private String orderId;

    /**
     * 总金额，单位为分
     */
    @Column(name = "total_pay")
    private Long totalPay;

    /**
     * 实付金额。单位:分。如:20007，表示:200元7分
     */
    @Column(name = "actual_pay")
    private Long actualPay;

    /**
     * 支付类型，1、在线支付，2、货到付款
     */
    @Column(name = "payment_type")
    private Integer paymentType;

    /**
     * 邮费。单位:分。如:20007，表示:200元7分
     */
    @Column(name = "post_fee")
    private Long postFee;

    /**
     * 订单创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 物流名称
     */
    @Column(name = "shipping_name")
    private String shippingName;

    /**
     * 物流单号
     */
    @Column(name = "shipping_code")
    private String shippingCode;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 买家留言
     */
    @Column(name = "buyer_message")
    private String buyerMessage;


    /**
     * 买家是否已经评价,0未评价，1已评价
     */
    @Column(name = "buyer_rate")
    private Integer buyerRate;

    /**
     * 发票类型(0无发票1普通发票，2电子发票，3增值税发票)
     */
    @Column(name = "invoice_type")
    private Integer invoiceType;

    /**
     * 订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
     */
    @Column(name = "source_type")
    private Integer sourceType;

}