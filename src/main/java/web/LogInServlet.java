package web;

import domain.Person;
import repositories.PersonRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by lukaszgodlewski on 02.04.2017.
 */

@WebServlet("/login")
public class LogInServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Class.forName("org.hsqldb.jdbcDriver");
            Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb");
            PersonRepository repo = new PersonRepository(connection);

            boolean exists = repo.exists(username);

            if (!exists) {
                response.getWriter().println("Invalid username or password. Try again.");
            }
            else {
                Person p = repo.get(username);
                HttpSession session = request.getSession();
                session.setAttribute("user", p);

                connection.close();
                response.sendRedirect("/profile");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
