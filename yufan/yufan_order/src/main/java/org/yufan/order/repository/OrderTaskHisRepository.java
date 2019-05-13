package org.yufan.order.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.yufan.order.bean.TaskHis;

/**
 * Created by zxd on 2019/4/6
 **/
public interface OrderTaskHisRepository extends JpaRepository<TaskHis,String> {
}
