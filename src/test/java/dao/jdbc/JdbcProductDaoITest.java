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

    private static final String DB_URL = "jdbc:h2:mem:test";///"jdbc:h2:./test.db"; jdbc:h2:~/test
    //    private static final String JDBC_DRIVER = "org.h2.Driver";//org.h2.Driver
    private static final String USER = "";
    private static final String PASS = "";
    private Product product;

    JdbcDataSource jdbcDataSource = new JdbcDataSource();
    JdbcProductDao jdbcProductDao;

    @BeforeEach
    void setUp() throws SQLException {

        jdbcDataSource.setURL(DB_URL);
        jdbcDataSource.setUser(USER);
        jdbcDataSource.setPassword(PASS);

        DataSource dataSource = jdbcDataSource;
        jdbcProductDao = new JdbcProductDao();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS products;");
            statement.execute("CREATE TABLE products (id SERIAL PRIMARY KEY, name VARCHAR(100), price NUMERIC (8,2), creation_date TIMESTAMP);");
            statement.execute("INSERT INTO products (name,price,creation_date) VALUES ('snowboard',50000.93, now());");

        }
    }

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
    @DisplayName("when FindAll then Return Correct Size")
    void whenFindAll_thenReturnCorrectSize() {
        List<Product> products = jdbcProductDao.findAll();
        assertEquals(28, products.size());
    }

    @Test
    @DisplayName("when FindAll then List Of Appropriate Products Return")
    void whenFindAll_thenListOfAppropriateProductsReturn() {
        List<Product> products = jdbcProductDao.findAll();

        assertEquals(6, products.get(0).getId());
        assertEquals(6, products.get(1).getId());
        assertEquals(6, products.get(2).getId());

        assertEquals("", products.get(0).getName());
        assertEquals("", products.get(1).getName());
        assertEquals("", products.get(2).getName());

        assertEquals(3000.00, products.get(0).getPrice());
        assertEquals(4359.85, products.get(1).getName());
        assertEquals(4900.00, products.get(2).getPrice());
    }

    @Test
    @DisplayName("when FindAll then List Of Appropriate Products Return")
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
    @DisplayName("when FindAll then List Of Appropriate Products Return")
    void whenAdd_thenNewProductAdded() {
        Product newProduct = Product.builder().
                name("snowboard").
                price(3000.00).
                creationDate(LocalDateTime.now())
                .build();
        int sizeBeforeAdd = jdbcProductDao.findAll().size();

        jdbcProductDao.add(newProduct);

        List<Product> products = jdbcProductDao.findAll();

        assertEquals(6, products.get(0).getId());
        assertEquals(6, products.get(1).getId());
        assertEquals(6, products.get(2).getId());
        assertEquals(6, products.get(3).getId());

        assertEquals("", products.get(0).getName());
        assertEquals("", products.get(1).getName());
        assertEquals("", products.get(2).getName());
        assertEquals("", products.get(3).getName());

        assertEquals(3000.00, products.get(0).getPrice());
        assertEquals(4359.85, products.get(1).getPrice());
        assertEquals(4900.00, products.get(2).getPrice());
        assertEquals(60, products.get(3).getPrice());
    }

    @Test
    @DisplayName("when FindAll then List Of Appropriate Products Return")
    void whenDelete_thenSizeOfListDecreaseByOne() {
        int sizeBeforeDelete = jdbcProductDao.findAll().size();

        jdbcProductDao.delete(9);
        int sizeAfterDelete = jdbcProductDao.findAll().size();

        assertEquals(sizeBeforeDelete - 1, sizeAfterDelete);
    }


    @Test
    @DisplayName("when FindAll then List Of Appropriate Products Return")
    void whenDelete_thenPreviousProductsDoNotChange() {
        List<Product> products = jdbcProductDao.findAll();

        jdbcProductDao.delete(2);

        assertEquals(6, products.get(0).getId());
        assertEquals(6, products.get(1).getId());


        assertEquals("", products.get(0).getName());
        assertEquals("", products.get(1).getName());


        assertEquals(3000.00, products.get(0).getPrice());
        assertEquals(4359.85, products.get(1).getPrice());
    }

    @Test
    @DisplayName("when FindAll then List Of Appropriate Products Return")
    void whenDelete_thenDeletedElement_isNull() {
        List<Product> products = jdbcProductDao.findAll();

        jdbcProductDao.delete(2);

        assertNull(products.get(2).getName());
        assertNull(products.get(2).getPrice());
    }

    @Test
    @DisplayName("when FindAll then List Of Appropriate Products Return")
    void whenGetNotExistingProductById_thenObjectIsNull() {
        Product product = Product.builder().
                id(2).
                name("snowboard").
                price(3000.00).
                creationDate(LocalDateTime.now())
                .build();
        jdbcProductDao.update(product);

        List<Product> products = jdbcProductDao.findAll();

        assertEquals(6, products.get(1).getId());
        assertEquals("", products.get(1).getName());
        assertEquals(4359.85, products.get(1).getPrice());
    }


}


//    private static final String DB_URL = "jdbc:h2:mem:test";///"jdbc:h2:./test.db"; jdbc:h2:~/test
//    private static final String JDBC_DRIVER = "org.h2.Driver";//org.h2.Driver
//    private static final String USER = "postgres";
//    private static final String PASS = "postgres";
//    private Product product;
//
//    private JdbcProductDao jdbcProductDao;
//
//    @BeforeEach
//    void setUp() throws SQLException, ClassNotFoundException {
////        Class.forName(JDBC_DRIVER);
//
//        JdbcDataSource jdbcDataSource = new JdbcDataSource();
//        jdbcDataSource.setURL(DB_URL);
//        jdbcDataSource.setUser(USER);
//        jdbcDataSource.setPassword(PASS);
//
//
//
//        DataSource dataSource = jdbcDataSource;
//        jdbcProductDao=new JdbcProductDao();
//
//
//        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
//             Statement statement = connection.createStatement()) {
//            statement.execute("DROP TABLE IF EXISTS products;");
//            statement.execute("CREATE TABLE products (id SERIAL PRIMARY KEY, name VARCHAR(100), price NUMERIC (8,2), creation_date TIMESTAMP);");
//            statement.execute("""
//                    INSERT INTO products (name,price,creation_date) VALUES ('snowboard',50000.93, now());""");
////            String sql =  """CREATE TABLE   products
////                 (id INTEGER not NULL, " +
////                    " first VARCHAR(255), " +
////                    " last VARCHAR(255), " +
////                    " age INTEGER, " +
////                    " PRIMARY KEY ( id ))";
////            statement.executeUpdate(sql);
////            String sql1 = "INSERT INTO Registration " + "VALUES (100, 'Zara', 'Ali', 18)";
////            statement.executeUpdate(sql1);""";
//
//
//        }
//    }
//
//
////    @Test
////    @DisplayName("test Find All Return Not Null Data")
////    public void testFindAllReturnNotNullData() {
////        List<Product> products = jdbcProductDao.findAll();
////        assertFalse(products.isEmpty());
////        for (Product product : products) {
////            assertNotNull(product.getId());
////            assertNotNull(product.getName());
////            assertNotNull(product.getPrice());
////            assertNotNull(product.getCreationDate());
////        }
////    }
////
////    @Test
////    @DisplayName(" ff")
////    void testFindAllReturnCorrectSizeOfProductsList() {
////        List<Product> products = jdbcProductDao.findAll();
////        assertEquals(28, products.size());
////    }


