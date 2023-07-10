package com.myprojects.FoodStore.service;

import com.myprojects.FoodStore.Cart;
import com.myprojects.FoodStore.PasswordValidator;
import com.myprojects.FoodStore.model.User;
import com.myprojects.FoodStore.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordValidator passwordValidator;



    public void registerUser(User user) {
        if (user == null) {
            logger.error("Trying to add user which is null");
            throw new NullPointerException("User cannot be null");
        }
        if (user.getUsername() == null || user.getPasswordChars() == null || user.getEmail() == null) {
            logger.error("Trying to add user with not all required field filled");
            throw new NullPointerException("All arguments have to be provided");
        }

        if (user.getUsername().isEmpty() || user.getPasswordChars().length == 0 || user.getEmail().isEmpty()) {
            logger.error("Trying to add user with not all required field filled");
            throw new IllegalArgumentException("All arguments have to be provided");
        }

        char[] passwordChars = user.getPasswordChars();
        String passwordString = new String(passwordChars);
        String hashedPassword = BCrypt.hashpw(passwordString, BCrypt.gensalt());
        user.setPassword(hashedPassword);
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


    public boolean isPasswordValid(char[] password){

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


    public boolean isPasswordCorrect(String username, char[] passwordChars) {
        User user = findUserByUserName(username);
        if (user != null && passwordChars != null) {
            String hashedPasswordFromDatabase = user.getPassword();
            String password = new String(passwordChars);

            boolean isCorrect = BCrypt.checkpw(password, hashedPasswordFromDatabase);

            Arrays.fill(passwordChars, '\0');

            return isCorrect;
        }
        logger.warn("Wrong password entered for user: {}", username);
        return false;
    }




    public User findUserByUserName(String username){
        if (username == null) {
            logger.error("Username was null");
            throw new IllegalArgumentException("Username must not be null");
        }
        User user = userRepository.findUserByUsername(username);
        return user;
    }

    public User findUserByUserId(Integer userId){
        if (userId == null) {
            logger.error("UserId was null");
            throw new IllegalArgumentException("userId must not be null");
        }
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);

    }

    public void grantAdminPrivileges(Integer userId) {
        if (userId==null){
            logger.error("UserId was null");
            throw new IllegalArgumentException("UserID cannot be null");
        }
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> {
            value.setAdmin(true);
            userRepository.save(value);
        });
        logger.info("Admin privileges granted for user with id {}", userId);
    }

    public void revokeAdminPrivileges(Integer userId) {
        if (userId==null){
            logger.error("UserId was null");
            throw new IllegalArgumentException("UserID cannot be null");
        }
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> {
            value.setAdmin(false);
            userRepository.save(value);
        });
        logger.info("Admin privileges revoked for user with id {}", userId);
    }
}
