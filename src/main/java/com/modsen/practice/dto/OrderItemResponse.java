package com.modsen.practice.dto;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {

    private Long id;

    private Long productId;

    private Long orderId;

    private short count;
}
