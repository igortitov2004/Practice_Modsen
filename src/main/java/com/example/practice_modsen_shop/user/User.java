package com.example.practice_modsen_shop.user;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private Long id;

    @Column(name = "u_firstname")
    private String firstName;

    @Column(name = "u_lastname")
    private String lastName;

    @Column(name = "u_gender")
    private Gender gender;

    @Column(name = "u_login")
    private String login;

    @Column(name = "u_password_hash")
    private String passwordHash;

    @Column(name = "u_phone_number")
    private String phoneNumber;

    @Column(name = "u_birth_date")
    private Date birthDate;

    @Column(name = "u_city")
    private String city;

    @Column(name = "u_street")
    private String street;

    @Column(name = "u_house_num")
    private int houseNumber;

    @Column(name = "u_apartment_num")
    private int apartmentNumber;

    @Column(name = "u_email")
    private String email;

}