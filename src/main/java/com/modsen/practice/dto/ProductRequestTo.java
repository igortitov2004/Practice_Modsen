package com.modsen.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestTo {
    private Long id;
    private Long category;
    private String ingredients;
    private Double price;
    private String description;
    private Integer weight;
    private Integer caloric_value;
}
