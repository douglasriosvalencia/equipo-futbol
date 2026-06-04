package com.soccertraining.soccer_training_api.controller;


import com.soccertraining.soccer_training_api.dto.auth.LoginRequestDTO;
import com.soccertraining.soccer_training_api.dto.auth.LoginResponseDTO;
import com.soccertraining.soccer_training_api.dto.auth.RefreshTokenResponseDTO;
import com.soccertraining.soccer_training_api.exception.HttpGlobalResponse;
import com.soccertraining.soccer_training_api.service.jwt.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")

public class AuthController {
    private final AuthService authService;

    // LOGIN----------------------------------------------------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<HttpGlobalResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginRequestDTO requestDTO){
        LoginResponseDTO loginResponse = authService.login(requestDTO);

        HttpGlobalResponse<LoginResponseDTO> response = new HttpGlobalResponse<>();

        response.setData(loginResponse);
        response.setMessage("Inicio de sesión exitoso");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // REFRESH TOKEN----------------------------------------------------------
    @PostMapping("/refresh")
    public ResponseEntity<HttpGlobalResponse<RefreshTokenResponseDTO>> refreshToken(@RequestHeader("Authorization") String authorizationHeader){

        String token = authorizationHeader.replace("Bearer ", "");

        RefreshTokenResponseDTO responseDTO = authService.refreshToken(token);

        HttpGlobalResponse<RefreshTokenResponseDTO> response = new HttpGlobalResponse<>();

        response.setData(responseDTO);
        response.setMessage("Token refrescado correctamente");

        return ResponseEntity.ok(response);
    }
}
