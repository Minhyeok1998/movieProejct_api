package com.movie.movieproject.controller;

import java.util.Collections;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movie.movieproject.data.dto.JoinDto;
import com.movie.movieproject.data.entity.UserEntity;
import com.movie.movieproject.service.UserServiceImpl;

@Controller
public class JoinController {
    @Autowired
    private UserServiceImpl userS;

    @PostMapping("/checkUsername")
    public @ResponseBody Map<String, Boolean> checkUsername(@RequestBody String username) {
        JSONObject jsonObject = new JSONObject(username);
        Boolean result = userS.findByUsername(String.valueOf(jsonObject.get("username")));
        return Collections.singletonMap("result", result);
    }

    @PostMapping("/checkEmail")
    public @ResponseBody Map<String, Boolean> checkEmail(@RequestBody String email) {
        JSONObject jsonObject = new JSONObject(email);
        Boolean result = userS.findByEmail(String.valueOf(jsonObject.get("email")));
        return Collections.singletonMap("result", result);
    }

    @PostMapping("/join")
    public String checkUsername(JoinDto joinUser) {
        System.out.println(joinUser);
        boolean result = userS.joinUser(joinUser);
        if (result) {
            return "redirect:/loginForm";
        } else {
            return "redirect:/joinForm";
        }
    }
}
