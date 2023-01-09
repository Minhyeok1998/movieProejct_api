package com.movie.movieproject.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.movie.movieproject.data.dto.MovieDto;

@Service
public class KoficApiServiceImpl {

    @Value("${external.kofic.movieDetails-url}")
    private String accessKey;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private String today = LocalDate.now().minusWeeks(1).format(formatter);

    @Value("${external.kofic.movieDetails-url}")
    String movieDetailsUrl;
    @Autowired
    private NaverServiceImpl naverS;

    public Map<String, Object> weeklyRankMovie() {

        System.out.println("오늘 날짜 : " + today);
        // test

        String urlstr = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?key="
                + accessKey + "&targetDt=" + today + "&itemPerPage=8";

        try {
            URL url = new URL(urlstr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // int responseCode = connection.getResponseCode();
            // System.out.println("responseCode : " + responseCode);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String iputLine = "";
            while ((iputLine = bufferedReader.readLine()) != null) {
                stringBuffer.append(iputLine);
            }
            String result = stringBuffer.toString();
            JSONObject jsonObject = new JSONObject(result); // kofic 주간/주말 박스오피스 api 결과
            JSONArray weeklyRankMovie_jsonArray = jsonObject.getJSONObject("boxOfficeResult")
                    .getJSONArray("weeklyBoxOfficeList");
            // System.out.println("jsonArray : " + jsonArray);
            /*
             * 1.kofic을 통해서 주중 1~8등까지의 영화 데이터를 추출(주간/주말 박스오피스)
             * 2.kofic(영화 목록)
             * 추출된 데이터의 포스터 사진을 얻기위해 naver 영화 검색을 사용한다.
             */
            weeklyRankMovieMoreDetails(weeklyRankMovie_jsonArray);
            List<MovieDto> movieList = naverS.saveMovieDto(weeklyRankMovie_jsonArray);

            return Collections.singletonMap("MovieRankObj", movieList);

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return Collections.singletonMap("MovieRankObj", null);
        }
    }

    void weeklyRankMovieMoreDetails(JSONArray jsonArray) {
        HttpURLConnection connection = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject movie = jsonArray.getJSONObject(i);
                String movieNm = URLEncoder.encode(String.valueOf(movie.get("movieNm")), "utf-8");
                String query = movieDetailsUrl + "?key=" + accessKey + "&movieNm=" + movieNm + "&itemPerPage=1";
                URL url = new URL(query);
                connection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String iputLine = "";
                while ((iputLine = bufferedReader.readLine()) != null) {
                    stringBuffer.append(iputLine);
                }
                String apiResult = stringBuffer.toString();
                JSONObject moiveDetials_jsonObject = new JSONObject(apiResult);
                JSONObject movieDetails = moiveDetials_jsonObject.getJSONObject("movieListResult")
                        .getJSONArray("movieList").getJSONObject(0);
                // System.out.println("----------------------------------------");
                // System.out.println(movieDetails.get("repNationNm"));
                // System.out.println("----------------------------------------");

                jsonArray.getJSONObject(i).put("country", movieDetails.get("repNationNm")); // 대표국가
                jsonArray.getJSONObject(i).put("genre", movieDetails.get("genreAlt")); // 장르
            } catch (Exception e) {
                System.err.println(
                        "koficApi-> findMovieDetails Exception : " + e.getLocalizedMessage() + e.getStackTrace());
                return;
            }
        }

    }
}
