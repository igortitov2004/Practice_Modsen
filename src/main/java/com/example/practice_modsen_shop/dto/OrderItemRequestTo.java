package com.example.practice_modsen_shop.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderItemRequestTo {
    private Long id;

    private Long productId;

    private Long orderId;

    private int count;
}
