package dao.jdbc;

import dao.jdbc.mapper.UserRowMapper;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUserDao implements UserDao{
    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private final UserRowMapper userRowMapper = new UserRowMapper();

    private static final String SEARCH_SQL = "SELECT id, login, hashed_password, salt FROM users WHERE (login) LIKE ?;";

    @Override
    public User search(String login) {
        String searchWord = "%" + login + "%";

        try (Connection connection = connectionFactory.connectionToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_SQL)) {

            preparedStatement.setString(1, searchWord);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet == null) {
                    return null;
                }
                while (resultSet.next()) {
                    return userRowMapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error.Can not search user with text: " + searchWord, e);
        }
        return null;
    }

}
