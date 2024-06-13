package com.modsen.practice.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {
    private Long id;

    private Long productId;

    private Long orderId;

    private short count;
}
