package security;

import dao.jdbc.JdbcUserDao;
import entity.User;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SecurityServiceTest {
    private List<String> userTokens = new ArrayList<>();
    private User user = new User();
    private String login = "Email";
    private String password = "password";
    private SecurityService securityService = new SecurityService();

    @Test
    @DisplayName("test Generate Cookie")
    void testGenerateCookie() {
        Cookie cookie = securityService.generateCookie();

        assertEquals("user-token", cookie.getName());
        assertDoesNotThrow(() -> UUID.fromString(cookie.getValue()));
    }

    @Test
    @DisplayName("test Is Auth True After User Logged In")
    void testIsAuthTrueAfterUserLoggedIn() {
        Cookie[] cookies = {securityService.generateCookie(), securityService.generateCookie()};

        assertTrue(securityService.isAuth(cookies));
    }

    @Test
    @DisplayName("test IsAuth False When User Not Logged In")
    void testIsAuthFalseWhenUserNotLoggedIn() {
        Cookie[] cookies = new Cookie[0];

        assertFalse(securityService.isAuth(cookies));
    }

    @Test
    @DisplayName("test IsAuth False When Cookies Is Null")
    void testIsAuthFalseWhenCookiesIsNull() {
        Cookie[] cookies = null;

        assertFalse(securityService.isAuth(cookies));
    }

    @Test
    @DisplayName("test Generate Salt Returns Not Null Value")
    void testGenerateSaltReturnsNotNullValue() {
        assertNotNull(securityService.generateSalt());
    }

    @Test
    @DisplayName("test Generate Salt Returns Random Value")
    void testGenerateSaltReturnsRandomValue() {
        assertNotEquals(securityService.generateSalt(), securityService.generateSalt());
    }

    @Test
    @DisplayName("test HashMD5 Returns Not Null Value")
    void testHashMD5() {
        assertNotNull(securityService.hashMD5(user.getPassword() + securityService.generateSalt()));
    }

    @Test
    @DisplayName("test HashMD5 Returns Not Random Value")
    void testHashMD5ReturnsNotRandomValue() {
        assertEquals(securityService.hashMD5(user.getPassword() + "salt"),
                securityService.hashMD5(user.getPassword() + "salt"));
    }

    @Test
    @DisplayName("test Salt And Password Are Not Null After SetPasswordAndSalt")
    void testSaltAndPasswordAreNotNullAfterSetPasswordAndSalt() {
        assertNull(user.getSalt());
        assertNull(user.getPassword());

        securityService.setPasswordAndSalt(user, password);

        assertNotNull(user.getSalt());
        assertNotNull(user.getPassword());
    }

    @Test
    @DisplayName("test Salt And Password Are Random After SetPasswordAndSalt")
    void testSaltAndPasswordAreRandomAfterSetPasswordAndSalt() {
        securityService.setPasswordAndSalt(user, password);
        String salt1 = user.getSalt();
        String password1 = user.getPassword();

        securityService.setPasswordAndSalt(user, password);
        String salt2 = user.getSalt();
        String password2 = user.getPassword();

        assertNotEquals(salt1, salt2);
        assertNotEquals(password1, password2);
    }

    @Test
    @DisplayName("test isValidCredentialsForTesting")
    void testIsValidCredentialsForTesting() {
        JdbcUserDao jdbcUserDao = mock(JdbcUserDao.class);

        User userFromDB = new User(1, login, "765f8f982e00176d81befeed250d95c4", "29cbc6c9-d1c4-4455-82c9-d1d577cbc33b");
        when(jdbcUserDao.search(login)).thenReturn(userFromDB);

        assertTrue(securityService.isValidCredentialsForTesting(password, jdbcUserDao.search(login)));
    }

}