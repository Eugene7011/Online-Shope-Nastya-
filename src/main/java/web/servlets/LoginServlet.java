package web.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import pagegenerator.PageGenerator;
import security.SecurityService;

import java.io.IOException;

@AllArgsConstructor
public class LoginServlet extends HttpServlet {
    private SecurityService securityService;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("login.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (securityService.isValidCredentials(login, password)) {
            response.addCookie(securityService.generateCookie());
            response.sendRedirect("/products");
        } else {
            PageGenerator pageGenerator = PageGenerator.instance();
            String page = pageGenerator.getPage("login.html");
            response.getWriter().write(page);
            response.getWriter().write("Please enter correct login and password");
        }
    }
}
