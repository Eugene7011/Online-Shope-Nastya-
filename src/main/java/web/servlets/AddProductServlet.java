package web.servlets;

import entity.Product;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pagegenerator.PageGenerator;
import service.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AddProductServlet extends HttpServlet {
    private ProductService productService;
    private List<String> userTokens;

    public AddProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("add_product.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Product product = getProductFromRequest(request);
            productService.add(product);
            response.sendRedirect("/products");

        } catch (IOException e) {
            String errorMessage = "Product has not been added. Check and enter correct data in the fields";
            PageGenerator pageGenerator = PageGenerator.instance();

            Map<String, Object> parameters = Map.of("errorMessage", errorMessage);
            String page = pageGenerator.getPage("add_product.html", parameters);
            response.getWriter().write(page);
        }
    }

    public Product getProductFromRequest(HttpServletRequest request) {
        return Product.builder().
                name(request.getParameter("name"))
                .price(Double.parseDouble(request.getParameter("price")))
                .build();
    }
}




