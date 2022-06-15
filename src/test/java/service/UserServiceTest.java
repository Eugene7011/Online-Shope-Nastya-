package service;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService = new UserService();

    @Test
    @DisplayName("test Generate Cookie")
    void testGenerateCookie() {
        Cookie cookie = userService.generateCookie();

        assertEquals("user-token", cookie.getName());
        assertDoesNotThrow(() -> UUID.fromString(cookie.getValue()));
    }

    @Test
    @DisplayName("test Is Auth True After User Logged In")
    void testIsAuthTrueAfterUserLoggedIn() {
        Cookie[] cookies = {userService.generateCookie(), userService.generateCookie()};

        assertTrue(userService.isAuth(cookies));
    }

    @Test
    @DisplayName("test IsAuth False When User Not Logged In")
    void testIsAuthFalseWhenUserNotLoggedIn() {
        Cookie[] cookies = new Cookie[0];

        assertFalse(userService.isAuth(cookies));
    }

    @Test
    @DisplayName("test IsAuth False When Cookies Is Null")
    void testIsAuthFalseWhenCookiesIsNull() {
        Cookie[] cookies = null;

        assertFalse(userService.isAuth(cookies));
    }
}