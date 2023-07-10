package com.myprojects.FoodStore.repository;

import com.myprojects.FoodStore.model.User;
import com.myprojects.FoodStore.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findUserByUsername_usernameExists_returnUser(){


        char[] password = {'A', 'n', 'g', 'e', 'l', 'i', 'n', 'a', '1', '2', '3'};

        User user = new User("Angelina", password, "angelina.angelina@gmail.com");
        userRepository.save(user);

        User userFound = userRepository.findUserByUsername("Angelina");

        Assertions.assertEquals(user, userFound);
    }

    @Test
    public void findUserByUsername_usernameDifferentLetterSize_returnNull(){

        char[] password = {'A', 'n', 'g', 'e', 'l', 'i', 'n', 'a', '1', '2', '3'};


        User user = new User("Angelina", password, "angelina.angelina@gmail.com");
        userRepository.save(user);

        User userFound = userRepository.findUserByUsername("angelina");

        Assertions.assertEquals(null, userFound);
    }

    @Test
    public void findUserByUsername_usernameDoesNotExist_returnNull(){

        char[] password = {'A', 'n', 'g', 'e', 'l', 'i', 'n', 'a', '1', '2', '3'};

        String username = "Angelina";
        User user = new User(username, password  , "angelina.angelina@gmail.com");
        userRepository.save(user);

       //deleting if exists
        if (userRepository.findUserByUsername(username) != null) {
            userRepository.delete(user);
        }

        User userFound = userRepository.findUserByUsername(username);

        Assertions.assertEquals(null, userFound);
    }

    @Test
    public void findUserByUsername_usernameIsNull_returnNull(){
        User userFound = userRepository.findUserByUsername(null);

        Assertions.assertEquals(null, userFound);
    }

    @Test
    public void findUserByUsername_usernameIsEmpty_returnNull(){
        User userFound = userRepository.findUserByUsername("");

        Assertions.assertEquals(null, userFound);
    }
}
