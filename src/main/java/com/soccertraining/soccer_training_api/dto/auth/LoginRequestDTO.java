package com.soccertraining.soccer_training_api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    //validaciones en
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no es válido")//exige que tenga @ y .com
    private String email;

    @NotBlank(message = "contraseña obligatoria")
    private String password;
}
