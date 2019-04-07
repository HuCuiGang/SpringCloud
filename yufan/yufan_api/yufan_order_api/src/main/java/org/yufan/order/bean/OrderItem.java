package org.yufan.order.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "yf_order_item")
@Getter
@Setter
public class OrderItem {
    /**
     * 订单详情id 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 商品id
     */
    @Column(name = "item_id")
    private Long itemId;

    /**
     * 购买数量
     */
    private Integer num;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 价格,单位：分
     */
    private Long price;

    /**
     * 商品图片
     */
    private String image;


}