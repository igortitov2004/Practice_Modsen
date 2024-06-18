package com.modsen.practice.auth;

import com.modsen.practice.enumeration.Gender;
import com.modsen.practice.enumeration.UserRole;
import jakarta.persistence.Column;
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

    private String firstname;
    private String lastname;
    private String middleName;
    private Gender gender;
    private String phoneNumber;
    private Date birthDate;
    private String email;
    private String login;
    private String passwordHash;
}
