package com.soccertraining.soccer_training_api.entity;

import com.soccertraining.soccer_training_api.enums.Role;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//hace lo del auto_incrment
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "sur_name", nullable = false, length = 100)
    private String surName;

    @Column(nullable = false, length = 100,unique = true)
    private String email;

    @Column(nullable = false, length = 250)
    private String password;

    @Enumerated(EnumType.STRING) //gurada el enum como texto
    @Column(nullable = false)//para que quede alienado con la base de datos
    private Role role;

}
