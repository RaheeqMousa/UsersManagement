package com.example.UsersManagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name= "\"user\"")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;


}