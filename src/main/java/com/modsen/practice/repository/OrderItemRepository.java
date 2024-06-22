package com.modsen.practice.repository;

import com.modsen.practice.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    boolean existsOrderItemById(Long id);
}
