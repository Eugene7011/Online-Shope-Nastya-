package dao.jdbc;

import dao.jdbc.JdbcProductDao;
import entity.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JdbcProductDaoTest {

    JdbcProductDao jdbcProductDao = new JdbcProductDao();

    @Test
    public void testJdbcProductDaoTest() {
        List<Product> products = jdbcProductDao.findAll();

        assertFalse(products.isEmpty());

        for (Product product : products) {
            assertNotNull(product.getName());
            assertNotNull(product.getCreationDate());
        }
    }
}
