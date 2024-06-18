package com.modsen.practice.auth;

import com.modsen.practice.enumeration.Gender;
import com.modsen.practice.enumeration.UserRole;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Pattern(regexp = "^[a-zA-Z\\s\\-]+$",message = "Incorrect firstname")
    private String firstname;
    @Pattern(regexp = "^[a-zA-Z\\s\\-]+$",message = "Incorrect lastname")
    private String lastname;
    @Pattern(regexp = "^[a-zA-Z\\s\\-]+$",message = "Incorrect middleName")
    private String middleName;
    @NotNull
    private Gender gender;
    @NotNull
    private String phoneNumber;
    @NotNull
    private Date birthDate;
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",message = "Incorrect email")
    private String email;
    @NotNull
    private String login;
    @NotNull
    private String passwordHash;
}
