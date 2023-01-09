package com.movie.movieproject.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.movie.movieproject.data.entity.UserEntity;
import com.movie.movieproject.repository.UserRepo;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userR;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("로그인 발생!!!");
        System.err.println("username : " + username);
        UserEntity user = userR.findByUsername(username);
        if (user != null) {
            return new PrincipalDetails(user);
        } else {
            return null;
        }
    }

}
