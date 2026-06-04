package com.soccertraining.soccer_training_api.service.crud;

import com.soccertraining.soccer_training_api.dto.userDto.UserRequestDTO;
import com.soccertraining.soccer_training_api.dto.userDto.UserResponseDTO;
import com.soccertraining.soccer_training_api.entity.User;
import com.soccertraining.soccer_training_api.enums.Role;
import com.soccertraining.soccer_training_api.exception.DuplicateResourceException;
import com.soccertraining.soccer_training_api.exception.ResourceNotFoundException;
import com.soccertraining.soccer_training_api.mapper.UserMapper;
import com.soccertraining.soccer_training_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //metodo de crear
    public UserResponseDTO create(UserRequestDTO requestDTO){

        //validacion de si ya existe el email
        if (userRepository.existsByEmail(requestDTO.getEmail())){
            throw  new DuplicateResourceException("Email ya existe");
        }
        User entity= UserMapper.toEntity(requestDTO);
        entity.setRole(Role.PLAYER);//inyecion del player desde la creacion
        entity.setPassword(passwordEncoder.encode(requestDTO.getPassword())); //encriptacion de la contraseña
        User saved=userRepository.save(entity);
        return UserMapper.toResponse(saved);
    }

    //metodo para listar
    public List<UserResponseDTO> listUsers(){
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    //metodo para buscar por id
    public UserResponseDTO getById(Long id){
        User userId=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Usuario no encontrado"));
        return UserMapper.toResponse(userId);
    }

    //Metodo para eliminar
    public String deleteUser(Long id){
        User userDelete=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Usuario no encontrado"));
        userRepository.delete(userDelete);
        return "Usuario eliminado correctamente";
    }
}
