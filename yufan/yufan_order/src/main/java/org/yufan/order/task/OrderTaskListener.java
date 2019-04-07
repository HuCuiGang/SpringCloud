package org.yufan.order.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.yufan.pay.bean.Task;
import org.yufan.order.config.RabbitMQConfig;
import org.yufan.order.service.OrderService;


@Component
@Slf4j
public class OrderTaskListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.ORDER_UPDATE)
    public void receiveOrderTask(Task task) {
        try {
            if (ObjectUtils.isEmpty(task)) {
                log.info("接收到的消息为空!");
                return;
            }
            //取出消息的内容
            String orderId = task.getRequestBody();
            log.info("需要处理的任务id为:{}，订单id;{}", task.getId(), orderId);
            if (orderService.changeOrderStatePay(task, orderId)) {
                //修改订单状态成功，向mq发消息完成修改订单任务
                rabbitTemplate.convertAndSend(RabbitMQConfig.EX_ORDER, RabbitMQConfig.ORDER_FINISH_KEY, task);
            }
        } catch (Exception e) {
            log.error("修改订单状态失败:{}", e.getMessage());
            throw new RuntimeException("修改订单状态失败!");
        }
    }
}
