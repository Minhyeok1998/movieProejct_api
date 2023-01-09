package com.movie.movieproject.data.dto;

import org.springframework.web.service.annotation.PostExchange;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class MovieDto {

    private String posterUrl; // 포스터 이미지 파일 이름 저장해놓을거 //naver
    private Integer rank; // kofic
    private Integer rankInten; // 순위 변동 //kofic
    private String name; // kofic
    private String subtitle; // naver
    private String director; // 감독 //naver
    private String actors; // 영화배우|영화배우2|3 '|' 이걸로 구분됨. //naver
    private Long totalView; // 총관람 //kofic
    private Double userRating; // 별점 //naver
    private String detailsUrl; // naver
    private String country;
    private String genre;

    @Builder
    public MovieDto(int rank, int rankInten, String name, long totalView, String posterUrl, String actors,
            double userRating, String detailsUrl, String director, String subtitle, String country, String genre) {
        this.rank = rank;
        this.rankInten = rankInten;
        this.name = name;
        this.totalView = totalView;
        this.posterUrl = posterUrl;
        this.actors = actors;
        this.userRating = userRating;
        this.detailsUrl = detailsUrl;
        this.director = director;
        this.subtitle = subtitle;
        this.country = country;
        this.genre = genre;
    }

}
