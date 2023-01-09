package com.movie.movieproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.movie.movieproject.data.dto.JoinDto;
import com.movie.movieproject.data.entity.UserEntity;
import com.movie.movieproject.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userR;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Boolean findByUsername(String username) {
        UserEntity user = userR.findByUsername(username);
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean findByEmail(String email) {
        UserEntity user = userR.findByEmail(email);
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean joinUser(JoinDto joinUser) {
        UserEntity user = UserEntity.builder()
                .username(joinUser.getUsername())
                .password(bCryptPasswordEncoder.encode(joinUser.getPassword()))
                .name(joinUser.getName())
                .role(("ROLE_USER"))
                .favoritegenre(joinUser.getFavoritegenre())
                .email(joinUser.getEmail())
                .gender(joinUser.getGender())
                .birth(joinUser.getBirth())
                .build();
        user = userR.save(user);

        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

}
