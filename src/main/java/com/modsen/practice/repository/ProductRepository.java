package com.modsen.practice.repository;

import com.modsen.practice.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory_id(Long categoryId, Pageable pageable);

    boolean existsOrderItemById(Long id);
}
