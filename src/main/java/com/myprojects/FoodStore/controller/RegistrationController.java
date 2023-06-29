package com.myprojects.FoodStore.controller;

import com.myprojects.FoodStore.model.User;
import com.myprojects.FoodStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(User user, Model model) {

        if (!userService.isPasswordValid(user.getPassword())) {
            model.addAttribute("error", "Password is too easy");
            return "registration";
        }

        if (userService.existEmail(user.getEmail())) {
            model.addAttribute("errorEmail", "Account with this email already exists.");
            return "registration";
        }

        if (userService.existUsername(user.getUsername())) {
            model.addAttribute("errorUsername", "Account with this username already exists.");
            return "registration";
        }

        userService.registerUser(user);

        return "redirect:/home";
    }


}
