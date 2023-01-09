package com.movie.movieproject.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movie.movieproject.service.KoficApiServiceImpl;

@Controller
public class indexController {

    @Autowired
    KoficApiServiceImpl koficApiS;

    @GetMapping({ "/", "/home" })
    public String index() {
        return "index";
    }

    @GetMapping("/showMovieRank")
    public @ResponseBody Map<String, Object> showMovie() {
        // kofic open api를 활용해서 영화 주간 순위를 얻는다!.. 1~ 8등까지.\
        // System.out.println("hi바보야");
        return koficApiS.weeklyRankMovie();
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

}
