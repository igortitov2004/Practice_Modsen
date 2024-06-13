package com.example.practice_modsen_shop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "it_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "p_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "ord_id")
    private Order order;

    @Column(name = "it_count")
    private short count;


}
