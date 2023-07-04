package com.myprojects.FoodStore.service;

import com.myprojects.FoodStore.PasswordValidator;
import com.myprojects.FoodStore.model.User;
import com.myprojects.FoodStore.repository.UserRepository;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
    public class UserServiceTests {

        @Mock
        private UserRepository userRepository;

        @Mock
        private PasswordValidator passwordValidator;

        @InjectMocks
        private UserService userService;

        @Test
        public void registerUser_validUser_userRegistered() {

            User user = new User("Johny", "password123!", "john@happy.com");

            when(userRepository.save(user)).thenReturn(user);
            userService.registerUser(user);

            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            Mockito.verify(userRepository).save(userCaptor.capture());

            User capturedUser = userCaptor.getValue();

            Assertions.assertEquals(user.getUsername(), capturedUser.getUsername());
            Assertions.assertEquals(user.getPassword(), capturedUser.getPassword());
            Assertions.assertEquals(user.getEmail(), capturedUser.getEmail());
        }

        @Test
        public void registerUser_missingRequiredField_userNotRegistered() {

            User user = new User("John", "", "john@example.com");


            when(userRepository.save(user)).thenReturn(user);
            userService.registerUser(user);


            Mockito.verify(userRepository, Mockito.never()).save(user);
        }

    @Test
    public void registerUser_userIsNull_userNotRegistered() {

        User user = null;

        assertThatThrownBy(() -> userService.registerUser(user))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("User cannot be null");
    }

    @Test
    public void registerUser_requiredFieldIsNull_userNotRegistered() {

        User user = new User("John", null, "john@example.com");

        assertThatThrownBy(() -> userService.registerUser(user))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("All arguments have to be provided");
    }


    @Test
    public void getAllUsers_returnListOfUsers() {

        List<User> expectedUsers = Arrays.asList(
                new User("John", "password1", "john@happy.com"),
                new User("Alice", "password2", "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(expectedUsers);


        List<User> actualUsers = userService.getAllUsers();


        Assertions.assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void getAllUsers_noUsersInDatabase_returnEmptyList() {

        List<User> expectedUsers = Collections.emptyList();
        when(userRepository.findAll()).thenReturn(expectedUsers);


        List<User> actualUsers = userService.getAllUsers();

        Assertions.assertTrue(actualUsers.isEmpty());
    }


    @Test
    public void isPasswordValid_validPassword_returnsTrue() {
        String password = "StrongPassword!123";


        when(passwordValidator.isPasswordValid(password)).thenReturn(true);

        boolean result = userService.isPasswordValid(password);

        Assertions.assertTrue(result);
    }

    @Test
    public void isPasswordValid_invalidPassword_returnsFalse() {
        String password = "weak";


        when(passwordValidator.isPasswordValid(password)).thenReturn(false);

        boolean result = userService.isPasswordValid(password);

        Assertions.assertFalse(result);
    }

    @Test
    public void isPasswordValid_nullPassword_returnsFalse() {

        when(passwordValidator.isPasswordValid(null)).thenReturn(false);

        boolean result = userService.isPasswordValid(null);

        Assertions.assertFalse(result);
    }

    @Test
    public void isPasswordValid_emptyPassword_returnsFalse() {

        when(passwordValidator.isPasswordValid("")).thenReturn(false);

        boolean result = userService.isPasswordValid("");

        Assertions.assertFalse(result);
    }


    @Test
    public void existEmail_validEmail_returnTrue(){

        List<User> userList = Arrays.asList(
                new User("John", "password1", "john@happy.com"),
                new User("Alice", "password2", "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(userList);

        boolean result = userService.existEmail("john@happy.com");

        Assertions.assertTrue(result);
    }


    @Test
    public void existEmail_emailDoesNotExist_returnFalse(){

        List<User> userList = Arrays.asList(
                new User("John", "password1", "john@happy.com"),
                new User("Alice", "password2", "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(userList);

        boolean result = userService.existEmail("maggie@happy.com");

        Assertions.assertFalse(result);
    }

    @Test
    public void existEmail_emailIsEmpty_returnFalse(){

        List<User> userList = Arrays.asList(
                new User("John", "password1", "john@happy.com"),
                new User("Alice", "password2", "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(userList);

        boolean result = userService.existEmail("");

        Assertions.assertFalse(result);
    }

    @Test
    public void existEmail_emailIsNull_returnFalse(){

        List<User> userList = Arrays.asList(
                new User("John", "password1", "john@happy.com"),
                new User("Alice", "password2", "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(userList);

        boolean result = userService.existEmail(null);

        Assertions.assertFalse(result);
    }

    @Test
    public void existUsername_validUserName_returnTrue(){

        List<User> userList = Arrays.asList(
                new User("John", "password1", "john@happy.com"),
                new User("Alice", "password2", "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(userList);

        boolean result = userService.existUsername("John");

        Assertions.assertTrue(result);
    }

    @Test
    public void existUsername_userNameDoesNotExist_returnFalse(){

        List<User> userList = Arrays.asList(
                new User("John", "password1", "john@happy.com"),
                new User("Alice", "password2", "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(userList);

        boolean result = userService.existUsername("Jerry");

        Assertions.assertFalse(result);
    }

    @Test
    public void existUsername_userIsEmpty_returnFalse(){

        List<User> userList = Arrays.asList(
                new User("John", "password1", "john@happy.com"),
                new User("Alice", "password2", "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(userList);

        boolean result = userService.existUsername("");

        Assertions.assertFalse(result);
    }

    @Test
    public void existUsername_userIsNull_returnFalse(){

        List<User> userList = Arrays.asList(
                new User("John", "password1", "john@happy.com"),
                new User("Alice", "password2", "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(userList);

        boolean result = userService.existUsername(null);

        Assertions.assertFalse(result);
    }


    @Test
    public void isPasswordCorrect_passwordIsCorrect_returnTrue(){
        User user = User.builder().username("johny").password("IamHappyJohn123!").email("email@john.com").build();

        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        boolean passwordChecked = userService.isPasswordCorrect("johny", "IamHappyJohn123!");

        Assertions.assertTrue(passwordChecked);
    }


    @Test
    public void isPasswordCorrect_passwordNotCorrect_returnFalse(){
        User user = User.builder().username("johny").password("IamHappyJohn123!").email("email@john.com").build();

        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        boolean passwordChecked = userService.isPasswordCorrect("johny", "IamHappyJohn123");

        Assertions.assertFalse(passwordChecked);
    }


    @Test
    public void isPasswordCorrect_passwordIsNull_returnFalse(){
        User user = User.builder().username("johny").password("IamHappyJohn123!").email("email@ john.com").build();

        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        boolean passwordChecked = userService.isPasswordCorrect("johny", null);

        Assertions.assertFalse(passwordChecked);
    }


    @Test
    public void findUserByUserName_UserExists_returnUser(){
        User user = User.builder().username("johny").password("IamHappyJohn123!").email("email@john.com").build();

        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        User userFound = userService.findUserByUserName("johny");

        Assertions.assertEquals(user, userFound);
        Assertions.assertTrue(!userFound.getUsername().isEmpty());

    }

    @Test
    public void findUserByUserName_UserDoesNotExist_returnNull() {

        when(userRepository.findUserByUsername("mosquito")).thenReturn(null);

        User userFound = userService.findUserByUserName("mosquito");

        assertNull(userFound);
    }

    @Test
    public void findUserByUserName_UserNameIsNull_returnNull() {

        when(userRepository.findUserByUsername(null)).thenReturn(null);

        User userFound = userService.findUserByUserName(null);

        assertNull(userFound);
    }


}
