package web.servlets;

import dao.jdbc.JdbcProductDao;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pagegenerator.PageGenerator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeleteProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> pageVariables = createPageVariablesMap(request);

        PageGenerator pageGenerator = PageGenerator.instance();

        String page = pageGenerator.getPage("deleteproduct.html", pageVariables);//deleteproduct.html

        try {
            response.getWriter().println(page);
        } catch (IOException e) {
            throw new RuntimeException("Cant get data from request about delete product");
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String id = request.getParameter("id");

            response.setContentType("text/html;charset=utf-8");

            JdbcProductDao jdbcProductDao = new JdbcProductDao();

            jdbcProductDao.delete(Integer.parseInt(id));
            response.sendRedirect("/products");
        } catch (NumberFormatException e) {
            PageGenerator pageGenerator = PageGenerator.instance();

            String page = pageGenerator.getPage("deleteproduct.html");
            response.getWriter().write(page);
            response.getWriter().write("Cant remove product from database. Try to write id correctly");
        }

        try {
            response.getWriter().close();
        } catch (IOException exception) {
            throw new RuntimeException("Cant show updated products");
        }
    }

    static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("id", request.getParameter("id"));

        return pageVariables;
    }
}
