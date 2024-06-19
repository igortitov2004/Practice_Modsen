package com.modsen.practice.dto;


import com.modsen.practice.enumeration.Gender;
import com.modsen.practice.enumeration.UserRole;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String firstname;

    private String lastname;

    private String middleName;

    private String gender;

    private String login;

    private String role;

    private String phoneNumber;

    private Date birthDate;

    private String email;
}
