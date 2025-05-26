/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chat;

/**
 *
 * @author lab_services_student
 */
public class Login {
    private static String storedUsername;
    private static String storedPassword;

    
     //Username to ensure that it contains an unserscore ans less or equal 5
    public static boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    //method to ensure password format is correct
    //has uppercase,a number and a special letter
    public static boolean checkPasswordComplexity(String password) {
        boolean hasUppercase = false, hasDigit = false, hasSpecialChar = false;
        //password must be atleast 8 characters long
        
        if (password.length() < 8) return false;
        
        //logic below was written with help from ChatGpt (OpenAI, 2025)
    //OpenAI (2025) ChatGPT [Online]. Available at: https://chat.openai.com/ (Accessed : 16 April 2025)
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUppercase = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecialChar = true;
        }
        return hasUppercase && hasDigit && hasSpecialChar;
    }

    //method that checks cellphone number
    public static boolean checkPhoneNumber(String phoneNumber) {
        return phoneNumber.startsWith("+27") && phoneNumber.length() == 12;
    }

    //method that registers the user and returns a message
    public static String registerUser(String firstName, String lastName, String username, String password, String phoneNumber) {
        storedUsername = username;
        storedPassword = password;
        return "You have been successfully registered.";
    }

    //method to ensure that user to login
    public static boolean loginUser(String inputUsername, String inputPassword) {
        return inputUsername.equals(storedUsername) && inputPassword.equals(storedPassword);
    }

        //method that returns login status
    public static String returnLoginStatus(boolean isLoggedIn, String firstName, String lastName) {
        if (isLoggedIn) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect. Please try again.";
        }
    }
}
