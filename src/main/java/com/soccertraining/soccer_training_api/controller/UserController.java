package com.soccertraining.soccer_training_api.controller;

import com.soccertraining.soccer_training_api.dto.userDto.UserRequestDTO;
import com.soccertraining.soccer_training_api.dto.userDto.UserResponseDTO;
import com.soccertraining.soccer_training_api.exception.HttpGlobalResponse;
import com.soccertraining.soccer_training_api.service.crud.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * Crear jugador.
     */
    //metdo crear player---------------------------------------------------------
    @PostMapping
    public ResponseEntity<HttpGlobalResponse<UserResponseDTO>> createUser(@Valid @RequestBody UserRequestDTO requestDTO){

        UserResponseDTO user = userService.create(requestDTO);

        HttpGlobalResponse<UserResponseDTO> response = new HttpGlobalResponse<>();

        response.setData(user);
        response.setMessage("Usuario creado correctamente");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Listar usuarios.
     */

    @GetMapping
    public ResponseEntity<
            HttpGlobalResponse<List<UserResponseDTO>>> getAllUsers(){

        List<UserResponseDTO> users = userService.listUsers();

        HttpGlobalResponse<List<UserResponseDTO>> response = new HttpGlobalResponse<>();

        response.setData(users);
        response.setMessage("Usuarios obtenidos correctamente");

        return ResponseEntity.ok(response);
    }

    /**
     * Buscar usuario por id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HttpGlobalResponse<UserResponseDTO>> getById(@PathVariable Long id){

        UserResponseDTO user = userService.getById(id);

        HttpGlobalResponse<UserResponseDTO> response = new HttpGlobalResponse<>();

        response.setData(user);
        response.setMessage("Usuario encontrado");

        return ResponseEntity.ok(response);
    }

    /**
     * Eliminar usuario.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpGlobalResponse<String>> deleteUser(@PathVariable Long id){

        String message = userService.deleteUser(id);

        HttpGlobalResponse<String> response = new HttpGlobalResponse<>();

        response.setData(message);
        response.setMessage("Operación exitosa");

        return ResponseEntity.ok(response);
    }

}
