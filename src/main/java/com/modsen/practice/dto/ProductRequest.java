package com.modsen.practice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @Min(1)
    @NotNull(groups = Marker.OnUpdate.class)
    private Long id;

    @Min(1)
    @NotNull(groups = Marker.OnCreate.class)
    private Long categoryId;

    @NotBlank(groups = Marker.OnCreate.class)
    @Size(min = 2, max = 200)
    private String name;

    private String ingredients;

    @Min(0)
    @NotNull(groups = Marker.OnCreate.class)
    private BigDecimal price;

    private String description;

    @Min(0)
    @NotNull(groups = Marker.OnCreate.class)
    private Short weight;

    @Min(0)
    @NotNull(groups = Marker.OnCreate.class)
    private Short caloric_value;
}
