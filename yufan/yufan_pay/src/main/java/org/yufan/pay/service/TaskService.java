package org.yufan.pay.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yufan.pay.bean.Task;
import org.yufan.pay.bean.TaskHis;
import org.yufan.pay.repository.PayTaskHisRepository;
import org.yufan.pay.repository.PayTaskRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by zxd on 2019/4/6
 **/
@Service
public class TaskService {

    @Autowired
    private PayTaskRepository payTaskRepository;

    @Autowired
    private PayTaskHisRepository payTaskHisRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    //取出前n条任务,取出指定时间之前处理的任务
    public List<Task> findTaskList(Date updateTime, int n){
        //设置分页参数，取出前n 条记录
        Pageable pageable = PageRequest.of(0, n);
        Page<Task> tasks = payTaskRepository.findByUpdateTimeBefore(pageable,updateTime);
        return tasks.getContent();
    }

    //发送消息
    public void publish(Task task,String ex,String routingKey){
        Optional<Task> optional = payTaskRepository.findById(task.getId());
        if(optional.isPresent()){
            //发送消息到消息队列
            rabbitTemplate.convertAndSend(ex,routingKey,task);
            //更新任务时间
            Task one = optional.get();
            one.setUpdateTime(new Date());
            payTaskRepository.save(one);
        }
    }

    //获取任务
    @Transactional
    public int getTask(String id,int version){
        //通过乐观锁的方式来更新数据表，如果结果大于0说明取到任务
        int count = payTaskRepository.updateTaskVersion(id, version);
        return count;
    }

    //完成任务
    @Transactional
    public void finishTask(String taskId){
        Optional<Task> optionalXcTask = payTaskRepository.findById(taskId);
        if(optionalXcTask.isPresent()){
            //当前任务
            Task xcTask = optionalXcTask.get();
            //历史任务
            TaskHis xcTaskHis = new TaskHis();
            BeanUtils.copyProperties(xcTask,xcTaskHis);
            payTaskHisRepository.save(xcTaskHis);
            //删除当前已成功执行任务
            payTaskRepository.delete(xcTask);
        }
    }


}
