package security;

import dao.jdbc.JdbcUserDao;
import entity.User;
import jakarta.servlet.http.Cookie;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;
import java.util.UUID;

public class SecurityService {
    private List<String> userTokens;
    JdbcUserDao jdbcUserDao = new JdbcUserDao();

    public SecurityService(List<String> userTokens) {
        this.userTokens = userTokens;
    }

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

    public Cookie generateCookie() {
        String userToken = UUID.randomUUID().toString();
        userTokens.add(userToken);
        return new Cookie("user-token", userToken);
    }

    public boolean isAuth(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (userTokens.contains(cookie.getValue())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isValidCredentials(String login, String password) {
        return isValidCredentialsForTesting(login, password, jdbcUserDao.search(login));
    }

    public boolean isValidCredentialsForTesting(String login, String password, User userFromDB) {
        if (userFromDB != null) {
            String enteredHashedPassword = hashMD5(password + userFromDB.getSalt());
            if (userFromDB.getPassword().equals(enteredHashedPassword)) {
                return true;
            }
        }
        return false;
    }
}

