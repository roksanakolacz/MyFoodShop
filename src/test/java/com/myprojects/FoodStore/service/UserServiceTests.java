package com.myprojects.FoodStore.service;

import com.myprojects.FoodStore.PasswordValidator;
import com.myprojects.FoodStore.model.User;
import com.myprojects.FoodStore.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

            char[] password = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd', '1', '2', '3', '!'};

            User user = new User("Johny", password, "john@happy.com");

            when(userRepository.save(user)).thenReturn(user);
            userService.registerUser(user);

            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).save(userCaptor.capture());

            User capturedUser = userCaptor.getValue();

            assertEquals(user.getUsername(), capturedUser.getUsername());
            assertEquals(user.getPassword(), capturedUser.getPassword());
            assertEquals(user.getEmail(), capturedUser.getEmail());
        }

    @Test
    public void registerUser_missingRequiredField_userNotRegistered() {
        char[] password = {};

        User user = new User("John", password, "john@example.com");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(user);
        });

        verify(userRepository, Mockito.never()).save(user);
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

        char[] password1 = "password1".toCharArray();
        char[] password2 = "password2".toCharArray();

        List<User> expectedUsers = Arrays.asList(
                new User("John", password1, "john@happy.com"),
                new User("Alice", password2, "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(expectedUsers);


        List<User> actualUsers = userService.getAllUsers();


        assertEquals(expectedUsers, actualUsers);
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
        char[] password = "StrongPassword!123".toCharArray();



        when(passwordValidator.isPasswordValid(password)).thenReturn(true);

        boolean result = userService.isPasswordValid(password);

        Assertions.assertTrue(result);
    }

    @Test
    public void isPasswordValid_invalidPassword_returnsFalse() {
        char[] password = "weak".toCharArray();


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
            char[] password = {};

        when(passwordValidator.isPasswordValid(password)).thenReturn(false);

        boolean result = userService.isPasswordValid(password);

        Assertions.assertFalse(result);
    }


    @Test
    public void existEmail_validEmail_returnTrue(){

        char[] password1 = "password1".toCharArray();
        char[] password2 = "password2".toCharArray();

        List<User> userList = Arrays.asList(
                new User("John", password1, "john@happy.com"),
                new User("Alice", password2, "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(userList);

        boolean result = userService.existEmail("john@happy.com");

        Assertions.assertTrue(result);
    }


    @Test
    public void existEmail_emailDoesNotExist_returnFalse(){

        char[] password1 = "password1".toCharArray();
        char[] password2 = "password2".toCharArray();



        List<User> userList = Arrays.asList(
                new User("John", password1, "john@happy.com"),
                new User("Alice", password2, "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(userList);

        boolean result = userService.existEmail("maggie@happy.com");

        Assertions.assertFalse(result);
    }

    @Test
    public void existEmail_emailIsEmpty_returnFalse(){

        char[] password1 = "password1".toCharArray();
        char[] password2 = "password2".toCharArray();

        List<User> userList = Arrays.asList(
                new User("John", password1, "john@happy.com"),
                new User("Alice", password2, "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(userList);

        boolean result = userService.existEmail("");

        Assertions.assertFalse(result);
    }

    @Test
    public void existEmail_emailIsNull_returnFalse(){

        char[] password1 = "password1".toCharArray();
        char[] password2 = "password2".toCharArray();



        List<User> userList = Arrays.asList(
                new User("John", password1, "john@happy.com"),
                new User("Alice", password2, "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(userList);

        boolean result = userService.existEmail(null);

        Assertions.assertFalse(result);
    }

    @Test
    public void existUsername_validUserName_returnTrue(){

        char[] password1 = "password1".toCharArray();
        char[] password2 = "password2".toCharArray();



        List<User> userList = Arrays.asList(
                new User("John", password1, "john@happy.com"),
                new User("Alice", password2, "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(userList);

        boolean result = userService.existUsername("John");

        Assertions.assertTrue(result);
    }

    @Test
    public void existUsername_userNameDoesNotExist_returnFalse(){

        char[] password1 = "password1".toCharArray();
        char[] password2 = "password2".toCharArray();



        List<User> userList = Arrays.asList(
                new User("John", password1, "john@happy.com"),
                new User("Alice", password2, "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(userList);

        boolean result = userService.existUsername("Jerry");

        Assertions.assertFalse(result);
    }

    @Test
    public void existUsername_userIsEmpty_returnFalse(){

        char[] password1 = "password1".toCharArray();
        char[] password2 = "password2".toCharArray();



        List<User> userList = Arrays.asList(
                new User("John", password1, "john@happy.com"),
                new User("Alice", password2, "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(userList);

        boolean result = userService.existUsername("");

        Assertions.assertFalse(result);
    }

    @Test
    public void existUsername_userIsNull_returnFalse(){

        char[] password1 = "password1".toCharArray();
        char[] password2 = "password2".toCharArray();



        List<User> userList = Arrays.asList(
                new User("John", password1, "john@happy.com"),
                new User("Alice", password2, "alice@sad.com")
        );

        when(userRepository.findAll()).thenReturn(userList);

        boolean result = userService.existUsername(null);

        Assertions.assertFalse(result);
    }


    @Test
    public void isPasswordCorrect_passwordNotCorrect_returnFalse() {
        char[] password = "IamHappyJohn123!".toCharArray();
        String hashedPasswordFromDb = BCrypt.hashpw(Arrays.toString(password), BCrypt.gensalt());

        User user = User.builder().username("johny").password(hashedPasswordFromDb).email("email@john.com").build();

        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        char[] passwordToCheck = "IaaaamHappyJohn123!".toCharArray();
        boolean passwordChecked = userService.isPasswordCorrect("johny", passwordToCheck);

        Assertions.assertFalse(passwordChecked);
    }


    @Test
    public void isPasswordCorrect_passwordCorrect_returnTrue() {
        char[] password = "IamHappyJohn123!".toCharArray();
        String hashedPasswordFromDb = BCrypt.hashpw(new String(password), BCrypt.gensalt());

        User user = User.builder().username("johny").password(hashedPasswordFromDb).email("email@john.com").build();

        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        char[] passwordToCheck = "IamHappyJohn123!".toCharArray();
        boolean passwordChecked = userService.isPasswordCorrect("johny", passwordToCheck);

        Assertions.assertTrue(passwordChecked);
    }


    @Test
    public void isPasswordCorrect_passwordIsNull_returnFalse(){
        char[] password = "IamHappyJohn123!".toCharArray();
        User user = User.builder().username("johny").passwordChars(password).email("email@john.com").build();

        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        boolean passwordChecked = userService.isPasswordCorrect("johny", null);

        Assertions.assertFalse(passwordChecked);
    }


    @Test
    public void findUserByUserName_UserExists_returnUser(){
            char[] password = "IamHappyJohn123!".toCharArray();
        User user = User.builder().username("johny").passwordChars(password).email("email@john.com").build();

        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        User userFound = userService.findUserByUserName("johny");

        assertEquals(user, userFound);
        Assertions.assertFalse(userFound.getUsername().isEmpty());

    }

    @Test
    public void findUserByUserName_UserDoesNotExist_returnNull() {

        when(userRepository.findUserByUsername("mosquito")).thenReturn(null);

        User userFound = userService.findUserByUserName("mosquito");

        assertNull(userFound);
    }

    @Test
    public void findUserByUserName_UserNameIsNull_returnNull() {

        assertThrows(IllegalArgumentException.class, () -> {
            userService.findUserByUserName(null);
        });

    }


    @Test
    public void findUserByUserId_UserExists_returnUser() {
        Integer userId = 1;

        User user = User.builder().username("johny").passwordChars("IamHappyJohn123!".toCharArray()).email("email@john.com").build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User actualUser = userService.findUserByUserId(userId);

        assertEquals(user, actualUser);
    }

    @Test
    public void findUserByUserId_UserDoesNotExist_returnNull() {
        Integer userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        User actualUser = userService.findUserByUserId(userId);

        assertNull(actualUser);
    }

    @Test
    public void findUserByUserId_UserIdIsNull_returnIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.findUserByUserId(null);
        });
    }


    @Test
    public void grantAdminPrivileges_ValidUserID_updateUser(){
        Integer userId = 1;
        User user = User.builder().username("johny").passwordChars("IamHappyJohn123!".toCharArray()).email("email@john.com").build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.grantAdminPrivileges(1);

        assertTrue(user.isAdmin());

        verify(userRepository, times(1)).save(user);
    }


    @Test
    public void grantAdminPrivileges_UserNotFound_returnNoUser() {
        Integer userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        userService.grantAdminPrivileges(userId);

        verify(userRepository, never()).save(any());

    }


    @Test
    public void grantAdminPrivileges_InvalidUserId_ThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.grantAdminPrivileges(null);
        });
    }


    @Test
    public void revokeAdminPrivileges_ValidUserID_updateUser(){
        Integer userId = 1;
        User user = User.builder().username("johny").passwordChars("IamHappyJohn123!".toCharArray()).email("email@john.com").build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.revokeAdminPrivileges(1);

        assertTrue(!user.isAdmin());

        verify(userRepository, times(1)).save(user);
    }


    @Test
    public void revokeAdminPrivileges_UserNotFound_returnNoUser() {
        Integer userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        userService.revokeAdminPrivileges(userId);

        verify(userRepository, never()).save(any());

    }


    @Test
    public void revokeAdminPrivileges_InvalidUserId_ThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.revokeAdminPrivileges(null);
        });
    }

}
