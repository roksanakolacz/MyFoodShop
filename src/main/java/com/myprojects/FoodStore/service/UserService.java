package com.myprojects.FoodStore.service;

import com.myprojects.FoodStore.PasswordValidator;
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

    public void registerUser(User user) {
        if (user == null) {
            throw new NullPointerException("User cannot be null");
        }
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
            throw new NullPointerException("All arguments have to be provided");
        }
        userRepository.save(user);
    }

    public List<User> getAllUsers(){
        List<User> users =  (List<User>)userRepository.findAll();
        return users;
    }


    public boolean isPasswordValid(String password){

        return passwordValidator.isPasswordValid(password);
    }

    public boolean existEmail(String email){
        return getAllUsers().stream()
                .map(User::getEmail)
                .anyMatch(mail->mail.equals(email));
    }

    public boolean existUsername(String username){
        return getAllUsers().stream()
                .map(User::getUsername)
                .anyMatch(name->name.equalsIgnoreCase(username));
    }


    public boolean isPasswordCorrect(String username, String password){
        if (findUserByUserName(username)==null){
            return false;
        }
        return userRepository.findUserByUsername(username).getPassword().equals(password);
    }

    public User findUserByUserName(String username){

        return userRepository.findUserByUsername(username);
    }

}
