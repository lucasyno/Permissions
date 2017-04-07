package web;

import domain.Person;
import repositories.PersonRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lukaszgodlewski on 07.04.2017.
 */

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb");
            PersonRepository repo = new PersonRepository(connection);

            response.getWriter().println("<table>");
            response.getWriter().println("<tr>" +
                    "<th>username</th>" +
                    "<th>email</th>" +
                    "<th>premium</th>" +
                    "<th>activate premium</th>" +
                    "<th>deactive premium</th>" +
                    "</tr>");

            List<Person> persons = repo.all();
            connection.close();
            for(Person p : persons) {
                if (!p.getUsername().equals("admin")) {
                    response.getWriter().println("<tr>" +
                            "<td>" + p.getUsername() + "</td>" +
                            "<td>" + p.getEmail() + "</td>" +
                            "<td>" + p.isPremium() + "</td>" );
                    if(p.isPremium()){
                        response.getWriter().println("<td>account with premium</td>");
                        response.getWriter().println("<td><form method='post'><button type='submit' name='deactive' value='" + p.getUsername() + "'>deactive</button></form></td>");
                    }
                    else {
                        response.getWriter().println("<td><form method='post'><button type='submit' name='active' value='" + p.getUsername() +"'>active</button></form></td>");
                        response.getWriter().println("<td>account without premium</td>");
                    }


                    response.getWriter().println("</tr>");
                }
            }

            response.getWriter().println("</table>");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb");
            PersonRepository repo = new PersonRepository(connection);
            if (request.getParameterMap().containsKey("active")){
                String username = request.getParameter("active");
                repo.setPremium(username);
            }
            else if (request.getParameterMap().containsKey("deactive")){
                String username = request.getParameter("deactive");
                repo.delPremium(username);
            }

            connection.close();
            response.sendRedirect("/admin");


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
