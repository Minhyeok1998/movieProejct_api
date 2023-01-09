package com.movie.movieproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.movieproject.data.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);
}