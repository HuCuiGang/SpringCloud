package org.yufan.order.service;

import org.yufan.common.exception.CustomerException;
import org.yufan.order.bean.OrderDTO;
import org.yufan.pay.bean.Task;

public interface OrderService {

    /**
     *  创建订单
     * @param orderDTO 订单数据传输对象
     * @return  订单id
     */
    public String createOrder(OrderDTO orderDTO) throws CustomerException;

    public boolean changeOrderStatePay(Task task, String orderId);

}
