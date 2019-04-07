package org.yufan.pay.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.yufan.pay.bean.TaskHis;

/**
 * Created by zxd on 2019/4/6
 **/
public interface PayTaskHisRepository extends JpaRepository<TaskHis,String> {
}
