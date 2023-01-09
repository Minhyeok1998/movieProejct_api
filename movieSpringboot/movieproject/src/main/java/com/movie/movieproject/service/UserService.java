package com.movie.movieproject.service;

import com.movie.movieproject.data.dto.JoinDto;
import com.movie.movieproject.data.entity.UserEntity;

public interface UserService {

    // username 으로 UserEntity 가있는지 확인.
    Boolean findByUsername(String username);

    Boolean findByEmail(String email);

    Boolean joinUser(JoinDto joinUser);
}
