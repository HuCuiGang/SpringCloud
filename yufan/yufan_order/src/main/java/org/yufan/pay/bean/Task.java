package org.yufan.order.bean;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Task implements Serializable {

    private String id;

    private Date createTime;
    private Date updateTime;
    private Date deleteTime;
    //任务类型
    private String taskType;
    //任务交换机
    private String mqExchange;
    //任务路由key
    private String mqRoutingkey;
    //任务请求体
    private String requestBody;
    //乐观锁版本
    private Integer version;
    //状态
    private String status;
    //任务错误信息
    private String errormsg;
}
