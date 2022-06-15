package service;

import dao.jdbc.UserDao;
import security.SecurityService;

import java.util.List;

public class UserService {
    private UserDao userDao;
    private List<String> userTokens;
    private SecurityService securityService = new SecurityService(userTokens);

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
}
