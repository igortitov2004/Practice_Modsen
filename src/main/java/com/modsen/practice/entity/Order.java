package com.modsen.practice.entity;

import com.modsen.practice.enumeration.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house_num")
    private String houseNumber;

    @Column(name = "apartment_num")
    private int apartmentNumber;

    @Column(name = "creation_date")
    private Date creationDate;

    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY)
    private Set<OrderItem> orderItems;

}
