package com.modsen.practice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "name")
    private String name;

    @Column(name = "ingredients")
    private String ingredients;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "desc")
    private String description;

    @Column(name = "weight")
    private Short weight;

    @Column(name = "caloric_value")
    private Short caloricValue;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    private Set<OrderItem> orderItems;
}
