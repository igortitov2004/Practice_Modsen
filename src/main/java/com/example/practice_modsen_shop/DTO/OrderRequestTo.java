package com.example.practice_modsen_shop.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class OrderRequestTo {

    private Long id;

    private Double price;

    private String status;

    private Long userId;

    private LocalDateTime creationDate;

}
