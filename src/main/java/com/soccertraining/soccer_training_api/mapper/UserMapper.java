package com.soccertraining.soccer_training_api.mapper;

import com.soccertraining.soccer_training_api.dto.userDto.UserRequestDTO;
import com.soccertraining.soccer_training_api.dto.userDto.UserResponseDTO;
import com.soccertraining.soccer_training_api.entity.User;

public final class UserMapper {
    private UserMapper(){

    }

    public static User toEntity(UserRequestDTO requestDTO){
        User user=new User(); //selecionamos la tabla
        user.setName(requestDTO.getName());
        user.setSurName(requestDTO.getSurName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());

        return user;
    }

    public static UserResponseDTO toResponse(User user){
        UserResponseDTO responseDTO=new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setName(user.getName());
        responseDTO.setSurName(user.getSurName());
        responseDTO.setEmail(user.getEmail());
        if (user.getRole() != null) {
            responseDTO.setRole(user.getRole().name());
        }

        return responseDTO;
    }
}
