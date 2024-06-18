package com.example.practice_modsen_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseTo {
    private Long id;
    private Long category;
    private String ingredients;
    private Double price;
    private String description;
    private Integer weight;
    private Integer caloric_value;
}
