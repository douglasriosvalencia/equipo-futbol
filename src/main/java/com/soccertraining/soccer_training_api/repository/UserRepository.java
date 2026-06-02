package com.soccertraining.soccer_training_api.repository;

import com.soccertraining.soccer_training_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);//este es para buscar cuando se haga el login

    boolean existsByEmail(String email);
}
