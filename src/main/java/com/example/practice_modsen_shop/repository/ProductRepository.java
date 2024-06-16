package com.example.practice_modsen_shop.repository;

import com.example.practice_modsen_shop.entities.Category;
import com.example.practice_modsen_shop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
