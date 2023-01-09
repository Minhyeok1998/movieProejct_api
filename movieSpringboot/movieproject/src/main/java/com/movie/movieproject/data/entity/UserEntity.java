package com.movie.movieproject.data.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.format.annotation.DateTimeFormat;

import com.movie.movieproject.data.dto.JoinDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "user")
/*
 * +---------------+--------------+------+-----+---------+----------------
 * | id | bigint | NO | PRI | NULL | auto_increment
 * | username | varchar(255) | NO | UNI | NULL |
 * | password | varchar(255) | NO | | NULL |
 * | name | varchar(100) | NO | | NULL |
 * | birth | date | YES | | NULL |
 * | gender | tinyint | YES | | NULL |
 * | role | varchar(255) | YES | | NULL |
 * | favoriteGenre | varchar(255) | YES | | NULL |
 * | provider | varchar(100) | YES | | NULL |
 * | providerId | varchar(255) | YES | | NULL |
 * 
 */
@Data
@RequiredArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private Byte gender;
    private String role;
    private String favoritegenre;
    private String provider;
    private String providerId;
    private String email;

    public List<String> getRoleList() {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(role, ",");
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }

    public List<String> getFavoireteGenreList() {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(favoritegenre, ",");
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }

        return list;
    }

    @Builder
    public UserEntity(String username, String password, String name, LocalDate birth, Byte gender, String role,
            String favoritegenre, String provider, String providerId, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.role = role;
        this.favoritegenre = favoritegenre;
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
    }

}
