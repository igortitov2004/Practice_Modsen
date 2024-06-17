package com.example.practice_modsen_shop.repository;

import com.example.practice_modsen_shop.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
