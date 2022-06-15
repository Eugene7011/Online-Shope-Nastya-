package dao.jdbc.mapper;

import entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductRowMapperTest {

    @Test
    @DisplayName("testProductRowMapper")
    public void testProductRowMapper() throws SQLException {
        ProductRowMapper productRowMapper = new ProductRowMapper();
        LocalDateTime localDateTime = LocalDateTime.of(2021, 10, 11, 10, 11);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("snowboard");
        when(resultSet.getDouble("price")).thenReturn(3000.00);
        when(resultSet.getTimestamp("creation_date")).thenReturn(timestamp);

        Product actualProduct = productRowMapper.mapRow(resultSet);

        assertNotNull(actualProduct);
        assertEquals(1, actualProduct.getId());
        assertEquals("snowboard", actualProduct.getName());
        assertEquals(3000.00, actualProduct.getPrice());
        assertEquals(localDateTime, actualProduct.getCreationDate());
    }

}
