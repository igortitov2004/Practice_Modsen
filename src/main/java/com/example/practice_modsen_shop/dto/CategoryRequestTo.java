package com.example.practice_modsen_shop.dto;

import lombok.Data;

@Data
public class CategoryRequestTo {
    private Long id;
    private String name;

    public CategoryRequestTo() {
    }

    public CategoryRequestTo(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
