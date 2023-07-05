package com.myprojects.FoodStore.controller;

import com.myprojects.FoodStore.model.Order;
import com.myprojects.FoodStore.model.User;
import com.myprojects.FoodStore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/allUsers")
    public String getAllUsers(Model model){
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        return "adminView/userList";
    }

    @GetMapping("/users/{userId}/admin")
    public String grantAdminPrivileges(@PathVariable Integer userId) {
        userService.grantAdminPrivileges(userId);
        return "redirect:/allUsers";
    }

    @GetMapping("/users/{userId}/revokeAdmin")
    public String revokeAdminPrivileges(@PathVariable Integer userId) {
        userService.revokeAdminPrivileges(userId);
        return "redirect:/allUsers";
    }

}


