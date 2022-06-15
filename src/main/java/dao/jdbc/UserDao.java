package dao.jdbc;

import entity.User;

public interface UserDao {

    User search(String login);

}
