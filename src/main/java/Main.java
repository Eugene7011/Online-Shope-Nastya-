import dao.jdbc.JdbcProductDao;
import dao.jdbc.JdbcUserDao;
import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import security.SecurityService;
import service.ProductService;
import web.security.SecurityFilter;
import web.servlets.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        JdbcProductDao jdbcProductDao = new JdbcProductDao();
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        ProductService productService = new ProductService(jdbcProductDao);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        List<String> userTokens = new ArrayList<>();

        SecurityService securityService = new SecurityService(userTokens, jdbcUserDao);
        AddProductServlet addProductServlet = new AddProductServlet(productService);
        SecurityFilter securityFilter = new SecurityFilter(securityService);

        context.addServlet(new ServletHolder(new AllProductsServlet(productService)), "/products");
        context.addServlet(new ServletHolder(new AllProductsServlet(productService)), "/*");
        context.addServlet(new ServletHolder(addProductServlet), "/products/add");
        context.addServlet(new ServletHolder(new DeleteProductServlet(productService)), "/products/delete");
        context.addServlet(new ServletHolder(new UpdateProductServlet(productService)), "/products/update");
        context.addServlet(new ServletHolder(new SearchProductServlet(productService)), "/products/search");
        context.addServlet(new ServletHolder(new LoginServlet(securityService)), "/login");
        context.addFilter(new FilterHolder(securityFilter), "/products", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}


