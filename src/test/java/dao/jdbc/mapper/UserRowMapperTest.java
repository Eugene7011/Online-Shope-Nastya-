package dao.jdbc.mapper;

import entity.Product;
import entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserRowMapperTest {

    @Test
    @DisplayName("test UserRowMapper")
    public void testUserRowMapper() throws SQLException {
        UserRowMapper userRowMapper = new UserRowMapper();
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("login")).thenReturn("user1");
        when(resultSet.getString("hashed_password")).thenReturn("765f8f982e00176d81befeed250d95c4");
        when(resultSet.getString("salt")).thenReturn("29cbc6c9-d1c4-4455-82c9-d1d577cbc33b");

        User userActual = userRowMapper.mapRow(resultSet);

        assertNotNull(userActual);
        assertEquals(1, userActual.getId());
        assertEquals("user1", userActual.getLogin());
        assertEquals("765f8f982e00176d81befeed250d95c4", userActual.getPassword());
        assertEquals("29cbc6c9-d1c4-4455-82c9-d1d577cbc33b", userActual.getSalt());
    }

    @Test
    @DisplayName("test UserRowMapper On Empty Data")
    public void testUserRowMapperOnEmptyData() throws SQLException {
        UserRowMapper userRowMapper = new UserRowMapper();
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getInt("id")).thenReturn(0);
        when(resultSet.getString("login")).thenReturn("");
        when(resultSet.getString("hashed_password")).thenReturn("");
        when(resultSet.getString("salt")).thenReturn("");

        User userActual = userRowMapper.mapRow(resultSet);

        assertNotNull(userActual);
        assertEquals(0, userActual.getId());
        assertEquals("", userActual.getLogin());
        assertEquals("", userActual.getPassword());
        assertEquals("", userActual.getSalt());
    }

}