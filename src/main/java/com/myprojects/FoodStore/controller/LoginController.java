package com.myprojects.FoodStore.controller;

import org.springframework.ui.Model;
import com.myprojects.FoodStore.LoginSession;
import com.myprojects.FoodStore.model.User;
import com.myprojects.FoodStore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(String username, @RequestParam("passwordChars") char[] password, HttpSession httpSession, Model model) {
        if (userService.isPasswordCorrect(username, password)) {
            User loggedInUser = userService.findUserByUserName(username);
            LoginSession session = new LoginSession(loggedInUser.getUserId());
            httpSession.setAttribute("userId", session.getUserId());
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid username or password. Try again");
            return "login";
        }
    }

    @GetMapping("/log-out")
    public String logOut(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/home";
    }
}
