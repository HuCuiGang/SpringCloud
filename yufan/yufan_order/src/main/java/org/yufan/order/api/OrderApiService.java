package org.yufan.order.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.yufan.common.utils.JsonUtils;
import org.yufan.order.bean.Order;
import org.yufan.order.bean.OrderStatus;
import org.yufan.order.client.ItemClient;
import org.yufan.order.eums.OrderStatusEnum;
import org.yufan.order.repository.OrderRepository;
import org.yufan.order.repository.OrderShippingRepository;
import org.yufan.order.repository.OrderStatusRepository;



@RequestMapping("/api/order")
@Slf4j
@RestController
public class OrderApiService implements OrderApi{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;


    @Autowired
    private  OrderShippingRepository orderShippingRepository;


    @GetMapping("/{orderId}")
    public Order queryOrderByOrderId(@PathVariable("orderId") String orderId) {
        log.debug("查询订单id为:{}",orderId);
        Order order = orderRepository.findOrderByOrderId(orderId);
        if(order==null){
            log.error("没有找到对应的订单信息");
            return null;
        }
        log.debug("查询订单信息为:{}",JsonUtils.objectToJson(order));
        return order;
    }

    @GetMapping("/status/{orderId}")
    @Override
    public Boolean queryOrderIsPay(@PathVariable("orderId") String orderId) {
        log.debug("查询订单id为:{}",orderId);
        OrderStatus orderStatus=orderStatusRepository.findOrderStatusByOrderId(orderId);
        if(orderStatus==null){
            log.error("没有找到对应的订单状态信息");
            throw  new RuntimeException("没有找到对应的订单状态信息");
        }
        log.debug("查询订单信息为:{}",JsonUtils.objectToJson(orderStatus));

        if(!orderStatus.getStatus().equals(OrderStatusEnum.NO_PAYMENT.getCode())){
            log.debug("订单已支付");
            return true;
        }
        log.debug("订单未支付");
        return false;
    }

    @PostMapping("/state/{orderId}")
    @Transactional
    public Boolean changeOrderStatePay(@PathVariable("orderId") String orderId) {
        log.info("修改订单状态的订单为:{}",orderId);
        if(orderStatusRepository.changeOrderState(OrderStatusEnum.PAYMENT.getCode(),
                orderId,OrderStatusEnum.NO_PAYMENT.getCode())>0){
            log.info("修改订单状态为支付成功!");
            return true;
        }
        log.error("修改订单状态失败");
        return false;
    }




}
