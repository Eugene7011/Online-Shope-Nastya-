package web.servlets;

import entity.Product;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pagegenerator.PageGenerator;
import service.ProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateProductServlet extends HttpServlet {
    private ProductService productService;

    public UpdateProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> pageVariables = createPageVariablesMap(request);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("updateproduct.html", pageVariables);

        try {
            response.getWriter().println(page);
        } catch (IOException e) {
            throw new RuntimeException("Cant get data from request");
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int price = Integer.parseInt(request.getParameter("price"));

            response.setContentType("text/html;charset=utf-8");
            Product product = new Product(id, name, price, null);

            productService.update(product);
            response.sendRedirect("/products");
        } catch (NumberFormatException e) {
            PageGenerator pageGenerator = PageGenerator.instance();

            String page = pageGenerator.getPage("updateproduct.html");
            response.getWriter().write(page);
            response.getWriter().write("Product has not been added. \n" +
                    "Check and enter correct data in the fields");
        } catch (IOException e) {
            throw new RuntimeException("Cant show update products");
        }
        try {
            response.getWriter().close();
        } catch (IOException exception) {
            throw new RuntimeException("Cant show update products");
        }
    }

    static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("id", request.getParameter("id"));

        return pageVariables;
    }
}
