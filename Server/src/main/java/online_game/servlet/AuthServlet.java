package online_game.servlet;

import online_game.dao.UserDAO;
import online_game.dataset.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = UserDAO.get(login).orElseThrow(() -> new RuntimeException(""));

        if (password.equals(user.getPassword())) resp.getWriter().append(user.getPassToken());
        else resp.getWriter().append("Wrong password");
    }
}
