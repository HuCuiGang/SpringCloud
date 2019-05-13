package org.yufan.order.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.yufan.cart.api.CartApi;

@FeignClient("yufan-cart-service")
public interface CartClient extends CartApi {


}
