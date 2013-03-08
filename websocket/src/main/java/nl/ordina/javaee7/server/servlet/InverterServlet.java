package nl.ordina.javaee7.server.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@WebServlet(value = "/servlet/inverter")
public class InverterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("  --- Reached the servlet!!!");
        response.setContentType("text/plain");
        // normally this should be escaped
        response.getWriter().write("You said " + request.getParameter("message"));
    }
}
