package com.example.practice_modsen_shop.order;

import com.example.practice_modsen_shop.orderitem.OrderItem;
import com.example.practice_modsen_shop.user.User;
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
    @Column(name = "ord_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "u_id")
    private User user;

    @Column(name = "ord_price")
    private BigDecimal price;

    @Column(name = "ord_status")
    private String status;

    @Column(name = "ord_creation_date")
    private Date creationDate;

    @OneToMany(mappedBy = "id")
    private Set<OrderItem> orderItems;

}
