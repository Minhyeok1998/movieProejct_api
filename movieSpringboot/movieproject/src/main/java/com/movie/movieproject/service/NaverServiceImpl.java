package com.movie.movieproject.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.movie.movieproject.data.dto.MovieDto;
import org.json.JSONObject;

@Service
public class NaverServiceImpl {

    @Value("${external.naver.client.id}")
    private String clientId;
    @Value("${external.naver.client.secret}")
    private String secretKey;
    @Value("${external.naver.search-url}")
    private String searchUrl;

    List<MovieDto> saveMovieDto(JSONArray jsonArray) {
        List<MovieDto> movieList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println(jsonObject);
            try {
                String query = URLEncoder
                        .encode(jsonObject.get("movieNm").toString(), "utf-8");
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("display", 1);
                parameters.put("yearfrom", String.valueOf(jsonObject.get("openDt")).substring(0, 4));
                parameters.put("country", codeNation(String.valueOf(jsonObject.get("country"))));
                HttpURLConnection connection = connection(query, parameters, "GET");
                int responseCode = connection.getResponseCode();
                System.out.println("NAVER API 결과 : " + responseCode);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String iputLine = "";
                while ((iputLine = bufferedReader.readLine()) != null) {
                    stringBuffer.append(iputLine);
                }
                // System.out.println(stringBuffer.toString());
                JSONObject naverSearchResult = new JSONObject(stringBuffer.toString());
                System.out.println(naverSearchResult);
                JSONObject itemObject = naverSearchResult.getJSONArray("items").getJSONObject(0); // image, actor,
                                                                                                  // userRating 등 재밌는게
                                                                                                  // 많이있다.

                String imageUrl = itemObject.getString("image");
                // .replace("\\", "");
                String actor = itemObject.getString("actor");
                String detailsUrl = itemObject.getString("link");
                String director = itemObject.getString("director");
                String subtitle = itemObject.getString("subtitle");
                Double userRating = itemObject.getDouble("userRating");
                MovieDto movieDto = MovieDto.builder()
                        .posterUrl(imageUrl)
                        .detailsUrl(detailsUrl)
                        .actors(actor)
                        .director(director)
                        .userRating(userRating)
                        .rank(jsonObject.getInt("rank"))
                        .totalView(jsonObject.getLong("audiCnt"))
                        .name(jsonObject.getString("movieNm"))
                        .rankInten(jsonObject.getInt("rankInten"))
                        .subtitle(subtitle)
                        .country(jsonObject.getString("country"))
                        .genre(jsonObject.getString("genre"))
                        .build();
                movieList.add(movieDto);
            } catch (Exception e) {
                System.out.println("saveMovieDto error : " + e.getLocalizedMessage());
                return null;
            }
        }
        return movieList;
    }

    private HttpURLConnection connection(String query, Map<String, Object> parameters, String method) {
        HttpURLConnection connection = null;
        String queryUrl = searchUrl;
        if (query.length() == 0) {
            System.out.println("쿼리가 없음");
            return connection;
        } else {
            queryUrl += "?query=" + query;
            if (parameters.size() != 0) {
                Set<String> param_keys = parameters.keySet();
                Iterator<String> it = param_keys.iterator();
                while (it.hasNext()) {
                    String param = it.next();
                    String key = String.valueOf(parameters.get(param));
                    queryUrl += "&" + param + "=" + key;
                }
            }
            try {

                System.out.println("요청 쿼리 : " + queryUrl);
                URL url = new URL(queryUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(method);
                connection.setRequestProperty("X-Naver-Client-Id", this.clientId);
                connection.setRequestProperty("X-Naver-Client-Secret", this.secretKey);
            } catch (Exception e) {
                System.out.println("naver api ERROR : " + e.getLocalizedMessage());
            }

        }

        return connection;

    }

    String codeNation(String nationStr) {
        switch (nationStr) {
            case "한국":
                return "KR";
            case "미국":
                return "US";
            case "일본":
                return "JP";
            case "프랑스":
                return "FR";
            case "영국":
                return "GB";
            case "홍콩":
                return "HK";
            default:
                return "ETC";
        }
    }
}
