package com.modsen.practice.dto;


import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Long id;

    private String email;

    private String login;

    private String password;

    private String name;

    private String surname;

    private String gender;


    private LocalDate birthDate;
}
