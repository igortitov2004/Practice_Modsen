package com.modsen.practice.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {

    private Long id;

    private ProductResponse product;

    private Long orderId;

    private short count;
}
