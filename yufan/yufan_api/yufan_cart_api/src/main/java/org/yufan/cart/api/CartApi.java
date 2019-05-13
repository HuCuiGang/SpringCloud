package org.yufan.cart.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yufan.cart.bean.Cart;

import java.util.List;

@RequestMapping("/api/cart")
public interface CartApi {

    @GetMapping(value = "/")
    public List<Cart> queryCart(@RequestParam("userId") Long userId);

    @GetMapping(value = "/item")
    public Cart queryCartItem(@RequestParam("userId") Long userId, @RequestParam("itemId") Long itemId);
}
