package com.soccertraining.soccer_training_api.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank(message = "nombre obligatorio")
    private String name;

    @NotBlank(message = "apellido obligatorio")
    private String surName;

   @NotBlank(message = "email obligatorio")
   @Email(message = "email invalido")
    private String email;

    @NotBlank(message = "contraseña obligatoria")
    @Size(min = 6,message = "la contraseña debe tener minimo 6 caracteres")
    private String password;

    //@NotNull(message = "rol obligatorio")
    //private Role role; esto no por que queda libre que alguen pueda registarse como queria y esta mal
}
