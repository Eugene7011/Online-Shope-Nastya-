import dao.jdbc.JdbcProductDao;
import dao.jdbc.JdbcUserDao;
import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import security.SecurityService;
import service.ProductService;
import service.UserService;
import web.security.SecurityFilter;
import web.servlets.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        JdbcProductDao jdbcProductDao = new JdbcProductDao();
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        ProductService productService = new ProductService(jdbcProductDao);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        List<String> userTokens = new ArrayList<>();

        UserService userService = new UserService();
        SecurityService securityService = new SecurityService(Collections.synchronizedList(userTokens), jdbcUserDao, userService);
        SecurityFilter securityFilter = new SecurityFilter(securityService, userService);

        context.addServlet(new ServletHolder(new AllProductsServlet(productService)), "/*");
        context.addServlet(new ServletHolder(new AllProductsServlet(productService)), "/products");
        context.addServlet(new ServletHolder(new AddProductServlet(productService)), "/products/add");
        context.addServlet(new ServletHolder(new DeleteProductServlet(productService)), "/products/delete");
        context.addServlet(new ServletHolder(new UpdateProductServlet(productService)), "/products/update");
        context.addServlet(new ServletHolder(new SearchProductServlet(productService)), "/products/search");
        context.addServlet(new ServletHolder(new LoginServlet(securityService)), "/login");
        context.addFilter(new FilterHolder(securityFilter), "/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}


