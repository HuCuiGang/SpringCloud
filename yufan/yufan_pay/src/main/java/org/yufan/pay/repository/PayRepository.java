package org.yufan.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yufan.pay.bean.PayLog;

/**
 * Created by zxd on 2019/4/6
 **/
public interface PayRepository extends JpaRepository<PayLog,String> {
}
