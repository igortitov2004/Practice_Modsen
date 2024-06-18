package com.modsen.practice.dto;


import com.modsen.practice.entity.OrderItem;
import com.modsen.practice.entity.User;
import com.modsen.practice.enumeration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private Long id;

    private Long userId;

    private BigDecimal price;

    private OrderStatus status;

    private String city;

    private String street;

    private String houseNumber;

    private int apartmentNumber;

    private Date creationDate;

    private Set<OrderItem> orderItems;

}
