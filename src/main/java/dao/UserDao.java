package dao;

import entity.User;

public interface UserDao {

    User search(String login);
}
