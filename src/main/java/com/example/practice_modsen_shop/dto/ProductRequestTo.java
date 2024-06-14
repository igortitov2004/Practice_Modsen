package com.example.practice_modsen_shop.dto;

import lombok.Data;

@Data
public class ProductRequestTo {
    private Long id;
    private Long category;
    private String ingredients;
    private Double price;
    private String description;
    private Integer weight;
    private Integer caloric_value;

    public ProductRequestTo(Long id, Long category, String ingredients, Double price, String description, Integer weight, Integer caloric_value) {
        this.id = id;
        this.category = category;
        this.ingredients = ingredients;
        this.price = price;
        this.description = description;
        this.weight = weight;
        this.caloric_value = caloric_value;
    }

    public ProductRequestTo() {
    }
}
