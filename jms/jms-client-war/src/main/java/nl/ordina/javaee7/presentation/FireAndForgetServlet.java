package nl.ordina.javaee7.presentation;

import nl.ordina.javaee7.business.service.FireAndForgetSender;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@WebServlet(urlPatterns = "/jms/sendFireAndForget")
public class FireAndForgetServlet extends HttpServlet {
    @Inject
    FireAndForgetSender messageSender;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = req.getParameter("content");
        if(content != null)  {
            messageSender.sendMessage(content);
        }
      resp.sendRedirect("../index.html");
    }
}
