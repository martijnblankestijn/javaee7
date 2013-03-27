package nl.ordina.javaee7.presentation;

import nl.ordina.javaee7.business.service.MessageSender;

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
@WebServlet(urlPatterns = "/jms/requestReply")
public class RequestReplyServlet extends HttpServlet {
  @Inject
  MessageSender messageSender;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String content = req.getParameter("content");
    String result = messageSender.sendAndReceive(content);

    resp.getWriter().print("<h1>Got reply</h1>" + result);
  }
}
