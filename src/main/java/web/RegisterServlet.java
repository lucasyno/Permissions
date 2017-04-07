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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb");
            PersonRepository repo = new PersonRepository(connection);

            boolean adminExists = repo.exists("admin");
            if (!adminExists){
                Person p = new Person();
                p.setUsername("admin");
                p.setEmail("admin@gmail.com");
                p.setPassword("admin");
                p.setAdmin(true);
                p.setPremium(true);
                repo.add(p);
                repo.setPremium(p.getUsername());
                repo.setAdmin(p.getUsername());
            }

            boolean isUnique = repo.exists(request.getParameter("username"));
            boolean isValidPassword = request.getParameter("password").equals(request.getParameter("password2"));

            if (!isUnique){
                response.getWriter().println("User with this login already exists in database!");
                return;
            }
            if (!isValidPassword){
                response.getWriter().println("Provided passwords are not the same!");
                return;
            }

            Person p = new Person();
            p.setUsername(request.getParameter("username"));
            p.setPassword(request.getParameter("password"));
            p.setEmail(request.getParameter("email"));
            repo.add(p);

            connection.close();

            HttpSession session = request.getSession();
            session.setAttribute("user", p);
            response.sendRedirect("/profile");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("registration.jsp");
    }
}
