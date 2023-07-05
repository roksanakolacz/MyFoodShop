package com.myprojects.FoodStore.service;

import com.myprojects.FoodStore.Cart;
import com.myprojects.FoodStore.PasswordValidator;
import com.myprojects.FoodStore.model.User;
import com.myprojects.FoodStore.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(Cart.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordValidator passwordValidator;


    public void registerUser(User user) {
        if (user == null) {
            logger.error("Trying to add user which is null");
            throw new NullPointerException("User cannot be null");
        }
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
            logger.error("Trying to add user with not all required field filled");
            throw new NullPointerException("All arguments have to be provided");
        }
        userRepository.save(user);
        logger.info("User successfully registered with name: {}", user.getUsername());
    }

    public List<User> getAllUsers(){
        List<User> users =  (List<User>)userRepository.findAll();
        if (users.isEmpty()){
            logger.warn("List of user is empty");
        }
        return users;
    }


    public boolean isPasswordValid(String password){

        boolean isValid = passwordValidator.isPasswordValid(password);

        if(isValid){
            logger.info("Password validated successfully: {}", password);
        } else {
            logger.warn("Password doesn't meet the requirements: {}", password);
        }

        return isValid;

    }

    public boolean existEmail(String email){

        boolean existedEmail = getAllUsers().stream()
                .map(User::getEmail)
                .anyMatch(mail->mail.equals(email));

        if (existedEmail){
            logger.warn("Email {} used for user registration already in db", email);
        } else {
            logger.info("Email {} can be used by new user - does not exist in db", email);
        }

        return existedEmail;
    }

    public boolean existUsername(String username){

        boolean existedUsername=getAllUsers().stream()
                .map(User::getUsername)
                .anyMatch(name->name.equalsIgnoreCase(username));

        if (existedUsername){
            logger.warn("Username {} used for user registration already in db", username);
        } else {
            logger.info("Username {} can be used by new user - does not exist in db", username);
        }

        return existedUsername;

    }


    public boolean isPasswordCorrect(String username, String password){
        if (findUserByUserName(username)==null){
            logger.warn("Password {} used for log-in is not correct", password);
            return false;
        }
        logger.info("Password {} used for log-in is correct", password);

        return userRepository.findUserByUsername(username).getPassword().equals(password);
    }

    public User findUserByUserName(String username){
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            logger.warn("User not found for username: {}", username);
        }
        return user;
    }

    public User findUserByUserId(Integer userId){
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);

    }

    public void grantAdminPrivileges(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> {
            value.setAdmin(true);
            userRepository.save(value);
        });
        logger.info("Admin privileges granted for user with id {}", userId);
    }

    public void revokeAdminPrivileges(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> {
            value.setAdmin(false);
            userRepository.save(value);
        });
        logger.info("Admin privileges revoked for user with id {}", userId);
    }
}
