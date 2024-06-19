package com.modsen.practice.dto;


import com.modsen.practice.enumeration.Gender;
import com.modsen.practice.enumeration.UserRole;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @Min(value = 1,groups = Marker.OnUpdate.class)
    @NotNull(groups = Marker.OnUpdate.class)
    @Null(groups = Marker.OnCreate.class)
    private Long id;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    private String middleName;

    @NotBlank
    private String gender;

    @NotBlank
    private String login;

    @NotBlank
    private String passwordHash;

    @NotBlank
    private String role;

    @NotBlank
    private String phoneNumber;

    @NotNull
    @Past
    private Date birthDate;

    @NotBlank
    private String email;
}
