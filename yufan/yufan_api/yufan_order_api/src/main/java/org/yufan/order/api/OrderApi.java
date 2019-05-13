package org.yufan.order.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yufan.order.bean.Order;


@RequestMapping("/api/order")
public interface OrderApi {

    @GetMapping("/{orderId}")
    public Order queryOrderByOrderId(@PathVariable("orderId") String orderId);

    @GetMapping("/status/{orderId}")
    public Boolean queryOrderIsPay(@PathVariable("orderId") String orderId) ;

    @PostMapping("/state/{orderId}")
    public Boolean changeOrderStatePay(@PathVariable("orderId") String orderId);

}
