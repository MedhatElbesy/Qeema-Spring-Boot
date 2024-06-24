package com.qeema.order_task.orderproduct;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qeema.order_task.order.Order;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
        List<OrderProduct> findByOrder(Order order);

}
