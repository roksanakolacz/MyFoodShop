package com.myprojects.FoodStore;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.regex.Pattern;

@Component
public class PasswordValidator {


    public boolean isPasswordValid(char[] password) {

        String passwordString = new String(password);

        if (passwordString.length() < 8) {
            return false;
        }


        boolean hasLowerCase = false;
        boolean hasUpperCase = false;
        for (char c : passwordString.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            }
        }
        if (!hasLowerCase || !hasUpperCase) {
            return false;
        }

        boolean hasDigit = false;
        for (char c : passwordString.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
                break;
            }
        }
        if (!hasDigit) {
            return false;
        }

        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        if (!pattern.matcher(passwordString).find()) {
            return false;
        }

        passwordString = null;

        return true;
    }


}
