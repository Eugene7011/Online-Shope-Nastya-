import dao.jdbc.JdbcProductDao;
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
        ProductService productService = new ProductService(jdbcProductDao);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        List<String> userTokens = new ArrayList<>();

        SecurityService securityService = new SecurityService(userTokens);
        AddProductServlet addProductServlet = new AddProductServlet(productService);
        SecurityFilter securityFilter = new SecurityFilter(securityService);

        context.addServlet(new ServletHolder(new AllProductsServlet()), "/products");
        //context.addServlet(new ServletHolder(new AllProductsServlet()), "/*");
        context.addServlet(new ServletHolder(addProductServlet), "/products/add");
        context.addServlet(new ServletHolder(new DeleteProductServlet()), "/products/delete");
        context.addServlet(new ServletHolder(new UpdateProductServlet()), "/products/update");
        context.addServlet(new ServletHolder(new SearchProductServlet()), "/products/search");
        context.addServlet(new ServletHolder(new LoginServlet(userTokens, securityService)), "/login");
        context.addFilter(new FilterHolder(securityFilter), "/products", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}


