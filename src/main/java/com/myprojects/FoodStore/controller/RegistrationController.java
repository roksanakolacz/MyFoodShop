package com.myprojects.FoodStore.controller;

import com.myprojects.FoodStore.model.User;
import com.myprojects.FoodStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user")User user, Model model) {

        char[] password = user.getPasswordChars();

        if (!userService.isPasswordValid(password)) {
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


        return "redirect:/login";
    }


}
