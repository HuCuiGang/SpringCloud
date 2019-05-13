package org.yufan.order.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yufan.common.exception.CustomerException;
import org.yufan.order.bean.*;
import org.yufan.order.client.CartClient;
import org.yufan.order.client.ItemClient;
import org.yufan.order.client.UserClient;
import org.yufan.order.eums.OrderStatusEnum;
import org.yufan.order.repository.*;
import org.yufan.order.service.OrderService;
import org.yufan.pay.bean.Task;

import java.util.Date;
import java.util.Optional;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartClient cartClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ItemClient itemClient;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderShippingRepository orderShippingRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderTaskHisRepository orderTaskHisRepository;


    //下单  TODO  ...
    @Override
    @Transactional
    public String createOrder(OrderDTO orderDTO) throws CustomerException {
        //1.生成订单id

        //2.计算订单总价

        //3.写入订单详情

        //4.扣库存   TODO  后面需要解决分布式事物问题

        //5.保存订单基本信息

        //6.保存收货地址

        //7.保存订单状态
        return "";
    }

    //创建订单    TODO...
    private void createOrder(OrderDTO orderDTO, String orderId, Long orderAmount) {
        //订单满80包邮,不足20元邮费
    }

    //保存订单状态   TODO
    private void createOrderStatus(String orderId) {

    }

    //保存订单详情 TODO
    private void createOrderItem() {

    }

    //保存收货地址  TODO
    private void createShipping(OrderDTO orderDTO, String orderId) {

    }

    @Transactional
    public boolean changeOrderStatePay(Task task, String orderId) {
        //查询历史任务
        Optional<TaskHis> optional = orderTaskHisRepository.findById(task.getId());

        if (optional.isPresent()) {
            log.info("修改订单状态任务已执行,任务id:{}", task.getId());
            return true;
        }
        //修改订单状态
        //修改订单状态成功
        orderStatusRepository.changeOrderState(OrderStatusEnum.PAYMENT.getCode(), orderId,
                OrderStatusEnum.NO_PAYMENT.getCode());
        //插入历史任务
        TaskHis taskHis = new TaskHis();
        BeanUtils.copyProperties(task, taskHis);
        taskHis.setUpdateTime(new Date());
        orderTaskHisRepository.save(taskHis);
        return true;
    }


}
