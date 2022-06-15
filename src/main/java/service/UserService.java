package service;

import dao.UserDao;
import entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserService {
    private UserDao userDao;

    public User search(String login) {
        return userDao.search(login);
    }
}
