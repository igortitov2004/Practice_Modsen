package com.example.practice_modsen_shop.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class UserRequestTo {
    private Long id;

    private String email;

    private String login;

    private String password;

    private String name;

    private String surname;

    private String gender;


    private LocalDate birthDate;
}
