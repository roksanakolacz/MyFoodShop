package com.myprojects.FoodStore.controller;

import com.myprojects.FoodStore.LoginSession;
import com.myprojects.FoodStore.model.User;
import com.myprojects.FoodStore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private User user;


    @GetMapping("/login")
    public String showLoginForm() {

        return "login";
    }

    @PostMapping("/login")
    public String processLogin(String username, String password, HttpSession httpSession) {
        if (userService.isPasswordCorrect(username, password)) {
            User loggedInUser = userService.findUserByUserName(username);
            LoginSession session = new LoginSession(loggedInUser.getUserId());
            httpSession.setAttribute("userId", session.getUserId());
            return "redirect:/home";
        } else {
            return "redirect:/login?error";
        }
    }




}
