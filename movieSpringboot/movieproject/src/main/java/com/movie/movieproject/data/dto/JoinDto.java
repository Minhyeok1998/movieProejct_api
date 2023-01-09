package com.movie.movieproject.data.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class JoinDto {

    private String username;
    private String password;
    private String favoritegenre;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private String email;
    private Byte gender;

    public List<String> genreList() {
        List<String> genreList = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(this.favoritegenre, ",");
        while (st.hasMoreTokens()) {
            String genre = st.nextToken();
            genreList.add(genre);
        }
        return genreList;
    }
}
