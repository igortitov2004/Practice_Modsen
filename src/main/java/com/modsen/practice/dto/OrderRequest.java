package com.modsen.practice.dto;


import com.modsen.practice.entity.OrderItem;
import com.modsen.practice.enumeration.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    @Min(value=1,groups = Marker.OnUpdate.class)
    @Null(groups = Marker.OnCreate.class)
    @NotNull(groups = Marker.OnUpdate.class)
    private Long id;

    @Min(0)
    @NotNull
    private BigDecimal price;

    @NotBlank
    private String status;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String houseNumber;

    @Min(0)
    @NotNull
    private int apartmentNumber;

    @Past
    @NotNull
    private Date creationDate;

    @NotEmpty
    private Set<OrderItemRequest> orderItems;

}
