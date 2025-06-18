package com.sklep.web;

import com.sklep.model.User;
import com.sklep.service.UserService;

import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Wyślij prosty formularz HTML przy GET /login
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("""
            <html>
            <head><title>Logowanie</title></head>
            <body>
                <form action="/login" method="post">
                    <input type="text" name="username" placeholder="login" required>
                    <input type="password" name="password" placeholder="hasło" required>
                    <button type="submit">Zaloguj się</button>
                </form>
            </body>
            </html>
        """);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userService.login(username, password);
        resp.setContentType("text/html;charset=UTF-8");
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());
            resp.getWriter().write("Zalogowano jako: " + user.getUsername());
        } else {
            resp.getWriter().write("Błędne dane logowania");
        }
    }
}
