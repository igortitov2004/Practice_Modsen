package com.modsen.practice.dto;

import com.modsen.practice.entity.Order;
import com.modsen.practice.entity.Product;
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
