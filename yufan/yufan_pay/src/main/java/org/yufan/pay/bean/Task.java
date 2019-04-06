package org.yufan.pay.bean;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@ToString
@Entity
@Table(name = "yf_pay_task")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Task implements Serializable {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;

    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "delete_time")
    private Date deleteTime;
    //任务类型
    @Column(name = "task_type")
    private String taskType;
    //任务交换机
    @Column(name = "mq_exchange")
    private String mqExchange;
    //任务路由key
    @Column(name = "mq_routingkey")
    private String mqRoutingkey;
    //任务请求体
    @Column(name = "request_body")
    private String requestBody;
    //乐观锁版本
    private Integer version;
    //状态
    private String status;
    //任务错误信息
    private String errormsg;
}
