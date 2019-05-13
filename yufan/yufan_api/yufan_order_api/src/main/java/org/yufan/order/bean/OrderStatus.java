package org.yufan.order.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "yf_order_status")
@Getter
@Setter
public class OrderStatus {
    /**
     * 订单id
     */
    @Id
    @Column(name = "order_id")
    private String orderId;

    /**
     * 状态：1、未付款 2、已付款,未发货 3、已发货,未确认 4、交易成功 5、交易关闭 6、已评价
     */
    private Integer status;

    /**
     * 订单创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 付款时间
     */
    @Column(name = "payment_time")
    private Date paymentTime;

    /**
     * 发货时间
     */
    @Column(name = "consign_time")
    private Date consignTime;

    /**
     * 交易完成时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 交易关闭时间
     */
    @Column(name = "close_time")
    private Date closeTime;

    /**
     * 评价时间
     */
    @Column(name = "comment_time")
    private Date commentTime;


}