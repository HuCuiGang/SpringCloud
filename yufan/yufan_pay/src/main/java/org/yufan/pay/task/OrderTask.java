package org.yufan.pay.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yufan.pay.bean.Task;
import org.yufan.pay.config.RabbitMQConfig;
import org.yufan.pay.service.TaskService;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by zxd on 2019/4/6
 **/
@Component
@Slf4j
public class OrderTask {

    @Autowired
    private TaskService taskService;

    //3秒执行一次
    @Scheduled(cron="0/3 * * * * *")
    //定时发送加选课任务
    public void sendOrderTask(){
        //得到1分钟之前的时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(GregorianCalendar.MINUTE,-1);
        Date time = calendar.getTime();
        List<Task> taskList = taskService.findTaskList(time, 100);
        log.info("需要要发送的任务;{}",taskList);

        taskList.forEach(task -> {
            //乐观锁执行任务
            if(taskService.getTask(task.getId(),task.getVersion())>0){
                String ex = task.getMqExchange();//要发送的交换机
                String routingKey = task.getMqRoutingkey();//发送消息要带routingKey
                taskService.publish(task,ex,routingKey);
                log.info("发送修改订单任务，任务id为:{}");
            }
        });


    }


    @RabbitListener(queues = RabbitMQConfig.ORDER_FINISH)
    public void receiveFinishOrderTask(Task task){
        if(task!=null && StringUtils.isNotEmpty(task.getId())){
            log.info("接收完成订单修改任务消息,任务id为:{}",task.getId());
            taskService.finishTask(task.getId());
        }
    }





}
