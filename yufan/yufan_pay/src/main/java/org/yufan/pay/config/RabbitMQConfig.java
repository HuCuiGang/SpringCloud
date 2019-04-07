package org.yufan.pay.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zxd on 2019/4/6
 **/
@Configuration
public class RabbitMQConfig {

    //任务类型
    public static final String TASK_TYPE="order_status";

    //订单状态任务交换机
    public static final String EX_ORDER = "ex_order";

    //修改订单状态消息队列
    public static final String ORDER_UPDATE = "order_update";

    //修改订单状态路由key
    public static final String ORDER_UPDATE_KEY = "updateorderstatus";

    //完成修改订单状态消息队列
    public static final String ORDER_FINISH = "order_finish";

    //完成修改订单状态路由key
    public static final String ORDER_FINISH_KEY = "finishorderstatus";


    /**
     * 交换机配置
     * @return the exchange
     */
    @Bean(EX_ORDER)
    public Exchange EX_DECLARE() {
        return ExchangeBuilder.directExchange(EX_ORDER).durable(true).build();
    }


    //声明队列 修改订单状态队列
    @Bean(ORDER_UPDATE)
    public Queue QUEUE_DECLARE_2() {
        Queue queue = new Queue(ORDER_UPDATE,true,false,true);
        return queue;
    }

    //声明队列完成订单修改队列
    @Bean(ORDER_FINISH)
    public Queue QUEUE_DECLARE() {
        Queue queue = new Queue(ORDER_FINISH,true,false,true);
        return queue;
    }



    /**
     * 绑定修改订单状态队列到交换机 .
     * @param queue    the queue
     * @param exchange the exchange
     * @return the binding
     */
    @Bean
    public Binding binding_orderUpdate_processTask(@Qualifier(ORDER_UPDATE) Queue queue, @Qualifier(EX_ORDER) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ORDER_UPDATE_KEY).noargs();
    }

    /**
     * 绑定完成订单修改队列到交换机 .
     * @param queue    the queue
     * @param exchange the exchange
     * @return the binding
     */
    @Bean
    public Binding binding_finishOrder_processTask(@Qualifier(ORDER_FINISH) Queue queue, @Qualifier(EX_ORDER) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ORDER_FINISH_KEY).noargs();
    }


}
