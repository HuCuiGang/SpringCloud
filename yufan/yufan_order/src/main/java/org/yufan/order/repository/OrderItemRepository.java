package org.yufan.order.repository;

import org.springframework.data.repository.CrudRepository;
import org.yufan.order.bean.OrderItem;


public interface OrderItemRepository extends CrudRepository<OrderItem,Long> {
}
