package com.modsen.practice.repository;

import com.modsen.practice.entity.Order;

import com.modsen.practice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_id(Long userId, Pageable pageable);
}
