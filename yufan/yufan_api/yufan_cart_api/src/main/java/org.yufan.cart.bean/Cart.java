package org.yufan.cart.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ApiModel(value = "购物车实体")
public class Cart implements Serializable {
    @ApiModelProperty(value = "商品id",required = true,dataType = "Long")
    private Long itemId;
    @ApiModelProperty(value = "用户id",required = true,dataType = "Long")
    private Long userId;
    @ApiModelProperty(value = "商品标题",required = true)
    private String itemTitle;
    @ApiModelProperty(value = "商品图片",required = true)
    private String itemImage;
    @ApiModelProperty(value = "商品价格(分)",required = true,dataType = "Long")
    private Long itemPrice;
    @ApiModelProperty(value = "商品数量",required = true,dataType = "Integer")
    private Integer num;
    @ApiModelProperty(hidden = true)
    private Date created;
    @ApiModelProperty(hidden = true)
    private Date updated;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        if (itemId != null ? !itemId.equals(cart.itemId) : cart.itemId != null) return false;
        return userId != null ? userId.equals(cart.userId) : cart.userId == null;
    }

    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
