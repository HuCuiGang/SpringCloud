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
    private Long itemId;
    private Long userId;
    private String itemTitle;
    private String itemImage;
    private Long itemPrice;
    private Integer num;
    private Date created;
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
