package dao.jdbc.mapper;

import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String login = resultSet.getString("login");
        String hashedPassword = resultSet.getString("hashed_password");
        String salt = resultSet.getString("salt");

        User user = User.builder()
                .id(id)
                .login(login)
                .password(hashedPassword)
                .salt(salt)
                .build();
        return user;
    }
}
