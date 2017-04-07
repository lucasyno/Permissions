package web;

import domain.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by lukaszgodlewski on 07.04.2017.
 */

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Person p = (Person) session.getAttribute("user");
        response.getWriter().println("Welcome " + p.getUsername() + " on your profile site.");
    }
}
