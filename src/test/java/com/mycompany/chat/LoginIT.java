/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.chat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginIT {

    @Test
    public void testValidUsername() {
        assertTrue(Login.checkUserName("user_"), "Username is valid.");
    }

    @Test
    public void testInvalidUsername() {
        assertFalse(Login.checkUserName("user123"), "Username is invalid.");
    }

    @Test
    public void testValidPassword() {
        assertTrue(Login.checkPasswordComplexity("Test@123"), "Password is valid.");
    }

    @Test
    public void testInvalidPasswordTooShort() {
        assertFalse(Login.checkPasswordComplexity("T@1"), "Password too short.");
    }

    @Test
    public void testValidPhoneNumber() {
        assertTrue(Login.checkPhoneNumber("+27663551200"), "Phone number is valid.");
    }

    @Test
    public void testInvalidPhoneNumber() {
        assertFalse(Login.checkPhoneNumber("0812345678"), "Phone number is invalid.");
    }

    @Test
    public void testUserLoginSuccess() {
        Login.registerUser("Sandile", "Doe", "john_", "Test@1234", "+27812345678");
        assertTrue(Login.loginUser("john_", "Test@1234"), "Login should succeed.");
    }

    @Test
    public void testUserLoginFailure() {
        Login.registerUser("John", "Doe", "john_", "Test@1234", "+27812345678");
        assertFalse(Login.loginUser("john_", "WrongPass"), "Login should fail.");
    }
}