package com.example.practice_modsen_shop.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class OrderResponseTo {

    private Long id;

    private Double price;

    private String status;

    private Long userId;

    private LocalDateTime creationDate;

}
