package nl.ordina.javaee7.presentation;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@WebServlet(urlPatterns = "/jms/poisonousMessages")
public class PoisonousMessagesServlet extends HttpServlet {
  @Inject
  JMSContext context;

  @Resource(mappedName = "jms/RedeliveryCountRequestQueue")
  Queue queue;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    context.createProducer().send(queue, "Die, die, die  my darling...");
    resp.getWriter().write("Poison delivered");
  }
}
