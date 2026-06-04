package com.soccertraining.soccer_training_api.service.jwt;

import com.soccertraining.soccer_training_api.dto.auth.LoginRequestDTO;
import com.soccertraining.soccer_training_api.dto.auth.LoginResponseDTO;
import com.soccertraining.soccer_training_api.dto.auth.RefreshTokenResponseDTO;
import com.soccertraining.soccer_training_api.entity.User;
import com.soccertraining.soccer_training_api.exception.InvalidCredentialsException;
import com.soccertraining.soccer_training_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;//esto para encriptar la contraseña
    private final UserRepository userRepository; //consultas jps a la base de datos
    private final JwtService jwtService;//logica de jwt

    //Login (logearse)
    public LoginResponseDTO login(LoginRequestDTO requestDTO){

        LoginResponseDTO responseDTO=new LoginResponseDTO(); //tipo de espuesta

        User userFound=userRepository.findByEmail(requestDTO.getEmail()).orElseThrow(()->new InvalidCredentialsException("Credenciales no validas"));//verifica si es existe o no


        //para verificar si la contraseña es la misma con la que se registro
        if (!passwordEncoder.matches(requestDTO.getPassword(),userFound.getPassword())){
            throw new InvalidCredentialsException("Contraseña incorrecta");
        }

        String jwt=jwtService.generateToken(userFound);//aca pasamos el objeto y el mismo se encraga de extarer lo que neciesta parala autenticacion


        return LoginResponseDTO.builder()
                .token(jwt)
                .type("Bearer")
                .userId(userFound.getId())
                .name(userFound.getName())
                .role(userFound.getRole().name())
                .build();
    }

    //Refesh token vamso a refrescar el token
    public RefreshTokenResponseDTO refreshToken(String token){

        String jwt=jwtService.refreshToken(token);

        return RefreshTokenResponseDTO.builder()
                .token(jwt)
                .type("Bearer")
                .build();
    }
}
