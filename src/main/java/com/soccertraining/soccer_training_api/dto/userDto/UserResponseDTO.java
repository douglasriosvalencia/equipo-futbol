package com.soccertraining.soccer_training_api.dto.userDto;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;

    private String name;

    private String surName;

    private String email;

    private String role;
}
