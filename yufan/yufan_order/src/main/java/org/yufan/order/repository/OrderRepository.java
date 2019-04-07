package org.yufan.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yufan.order.bean.Order;


public interface OrderRepository extends JpaRepository<Order,String> {

    public Order findOrderByOrderId(String id);
}
