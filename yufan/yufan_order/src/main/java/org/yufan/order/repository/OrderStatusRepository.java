package org.yufan.order.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.yufan.order.bean.OrderStatus;


public interface OrderStatusRepository extends CrudRepository<OrderStatus,Long> {

    @Query(nativeQuery = true,value = "UPDATE yufan_order_status SET status=?1 WHERE order_id=?2 and status=?3")
    @Modifying
    public Integer changeOrderState(Integer nowStatus,String orderId,Integer oldStatus);

    public OrderStatus findOrderStatusByOrderId(String orderId);

}
