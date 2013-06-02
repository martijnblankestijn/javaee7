package nl.ordina.javaee7.async;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 *
 */
@WebServlet("/report")
public class ReportServlet extends HttpServlet {
  // @Resource(lookup = "java:comp/DefaultManagedExecutorService")
  @Resource ManagedExecutorService mes;

  @Override protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    String reportName = req.getParameter("reportName");

    Future<String> reportFuture = mes.submit(new ReporterTask(reportName));

    req.getSession().setAttribute("report", reportFuture);
    resp.getWriter().write("<html><body>");
    resp.getWriter().write("<a href=\"report\">Check</a>");
    resp.getWriter().write("</body></html>");
  }

  @Override protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    Future<String> reportFuture = (Future<String>) req.getSession().getAttribute("report");
    try {
      String resultaat = reportFuture.isDone() ? reportFuture.get() : HTML_CHECK_AGAIN;
      resp.getWriter().write(resultaat);

    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }
  private static final String HTML_CHECK_AGAIN = "<html><body> Nog niet: <a href=\"report\">Check again</a> </body></html>";

}
