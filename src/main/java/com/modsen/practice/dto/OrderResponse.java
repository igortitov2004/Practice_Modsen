package com.modsen.practice.dto;



import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private Long id;

    private Long userId;

    private BigDecimal price;

    private String status;

    private String city;

    private String street;

    private String houseNumber;

    private int apartmentNumber;

    private Date creationDate;

    private Set<OrderItemResponse> orderItems;

}
