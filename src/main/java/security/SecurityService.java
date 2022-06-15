package security;

import dao.UserDao;
import entity.User;
import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class SecurityService {
    private List<String> userTokens = new ArrayList<>();
    private UserDao userDao;
    private UserService userService;

    public void setPasswordAndSalt(User user, String password) {
        String salt = generateSalt();
        String passwordWithSalt = password + salt;
        String hashedPasswordWithSalt = hashMD5(passwordWithSalt);

        user.setSalt(salt);
        user.setPassword(hashedPasswordWithSalt);
    }

    public String hashMD5(String passwordWithSalt) {
        return DigestUtils.md5Hex(passwordWithSalt);
    }

    public String generateSalt() {
        return UUID.randomUUID().toString();
    }

    public String login(String login, String password) {
        if (isValidCredentials(login, password)) {
            Cookie cookie = userService.generateCookie();
            String token = cookie.getValue();
            return token;
        }
        return null;
    }


    public boolean isValidCredentials(String login, String password) {
        return isValidCredentialsForTesting(password, userDao.search(login));
    }

    public boolean isValidCredentialsForTesting(String password, User userFromDB) {
        if (userFromDB != null) {
            String enteredHashedPassword = hashMD5(password + userFromDB.getSalt());
            if (userFromDB.getPassword().equals(enteredHashedPassword)) {
                return true;
            }
        }
        return false;
    }
}

