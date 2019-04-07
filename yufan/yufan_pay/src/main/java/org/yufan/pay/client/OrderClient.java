package org.yufan.pay.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.yufan.order.api.OrderApi;

/**
 * Created by zxd on 2019/1/14
 **/
@FeignClient("yufan-order-service")
public interface OrderClient extends OrderApi {

}
