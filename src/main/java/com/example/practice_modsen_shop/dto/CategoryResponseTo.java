package com.example.practice_modsen_shop.dto;

import lombok.*;

@Data
public class CategoryResponseTo {
    private Long id;
    private String name;

    public CategoryResponseTo() {
    }

    public CategoryResponseTo(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
