package dao.jdbc;

import dao.jdbc.JdbcProductDao;
import entity.Product;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcProductDaoITest {

    private final JdbcProductDao jdbcProductDao = new JdbcProductDao();

    @Test
    @DisplayName("when FindAll then Return Not Null Data")
    public void whenFindAll_thenReturnNotNullData() {
        List<Product> products = jdbcProductDao.findAll();
        assertFalse(products.isEmpty());
        for (Product product : products) {
            assertNotNull(product.getId());
            assertNotNull(product.getName());
            assertNotNull(product.getPrice());
            assertNotNull(product.getCreationDate());
        }
    }

    @Test
    @DisplayName("when FindAll then Return  Is Not Null")
    void whenFindAll_thenSize_isNotNull() {
        List<Product> products = jdbcProductDao.findAll();
        assertNotNull(products.size());
    }

    @Test
    @DisplayName("when Add then Size Of List Increase By One")
    void whenAdd_thenSizeOfListIncreaseByOne() {
        Product newProduct = Product.builder().
                name("snowboard").
                price(3000.00).
                creationDate(LocalDateTime.now())
                .build();
        int sizeBeforeAdd = jdbcProductDao.findAll().size();
        jdbcProductDao.add(newProduct);
        int sizeAfterAdd = jdbcProductDao.findAll().size();
        assertEquals(sizeBeforeAdd + 1, sizeAfterAdd);
    }

    @Test
    @DisplayName("when Search New Product By Name then List With Searched Product is Not Empty")
    void whenSearchNewProductByName_thenListWithSearchedProduct_isNotEmpty() {
        Product newProduct = Product.builder().
                name("new_snowboard").
                price(3000.00).
                creationDate(LocalDateTime.now())
                .build();
        jdbcProductDao.add(newProduct);
        List<Product> listWithSearchedProduct = jdbcProductDao.search("snowboard");
        assertNotNull(listWithSearchedProduct);
    }

    @Test
    @DisplayName("when Update then Size Of List does Not Change")
    void whenUpdate_thenSizeOfList_doesNotChange() {
        Product newProduct = Product.builder().
                name("snowboard").
                price(3000.00).
                creationDate(LocalDateTime.now())
                .build();
        jdbcProductDao.add(newProduct);
        int sizeBeforeUpdate = jdbcProductDao.findAll().size();
        jdbcProductDao.update(newProduct);
        int sizeAfterUpdate = jdbcProductDao.findAll().size();
        assertEquals(sizeBeforeUpdate, sizeAfterUpdate);
    }
}



