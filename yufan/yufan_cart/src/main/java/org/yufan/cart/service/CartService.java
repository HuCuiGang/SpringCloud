package org.yufan.cart.service;

import org.yufan.cart.bean.Cart;

import java.util.List;

public interface CartService {

    public void addCart(Cart cart);

    public void updateCartNum(Long itemId,Long userId,Integer num);

    public void deleteCart(Long itemId,Long userId);

    public List<Cart> queryCartByUserId(Long userId);

}
