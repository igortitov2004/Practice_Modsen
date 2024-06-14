package com.example.practice_modsen_shop.product;

import com.example.practice_modsen_shop.category.Category;
import com.example.practice_modsen_shop.orderitem.OrderItem;
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
    @Column(name = "p_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "c_id")
    private Category category;

    @Column(name = "p_ingredients")
    private String ingredients;

    @Column(name = "p_price")
    private BigDecimal price;

    @Column(name = "p_desc")
    private String description;

    @Column(name = "p_weight")
    private Short weight;

    @Column(name = "p_caloric_value")
    private Short caloricValue;

    @OneToMany(mappedBy = "id")
    private Set<OrderItem> orderItems;
}
