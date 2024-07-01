package com.modsen.practice.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequest {

    @Min(value = 1,groups = Marker.OnUpdate.class)
    @NotNull(groups = Marker.OnUpdate.class)
    @Null(groups = Marker.OnCreate.class)
    private Long id;

    @Min(1)
    @NotNull
    private Long productId;

    @Min(value = 1)
    @NotNull(groups = Marker.OnUpdate.class)
    private Long orderId;

    @Min(1)
    @NotNull()
    private short count;
}
