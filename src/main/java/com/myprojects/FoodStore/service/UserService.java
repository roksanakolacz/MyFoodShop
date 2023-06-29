package com.myprojects.FoodStore.service;

import com.myprojects.FoodStore.PasswordValidator;
import com.myprojects.FoodStore.model.Product;
import com.myprojects.FoodStore.model.User;
import com.myprojects.FoodStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordValidator passwordValidator;

    public void registerUser(User user){

        userRepository.save(user);
    }

    public List<User> getAllUsers(){
        List<User> users =  (List<User>)userRepository.findAll();
        return users;
    }


    public User getByUserId(Integer userId){
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
    }
    public boolean isPasswordValid(String password){
        return passwordValidator.isPasswordValid(password);
    }

    public boolean existEmail(String email){
        return getAllUsers().stream()
                .map(user->user.getEmail())
                .filter(mail->mail.equals(email))
                .findAny()
                .isPresent();
    }

    public boolean existUsername(String username){
        return getAllUsers().stream()
                .map(user->user.getUsername())
                .filter(name->name.equalsIgnoreCase(username))
                .findAny()
                .isPresent();
    }


    public boolean isPasswordCorrect(String username, String password){
        if (findByUserName(username)==null){
            return false;
        }
        return userRepository.findByUserName(username).getPassword().equals(password);
    }

    public User findByUserName(String username){

        return userRepository.findByUserName(username);
    }

}
