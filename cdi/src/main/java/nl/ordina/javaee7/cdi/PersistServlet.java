package nl.ordina.javaee7.cdi;

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
@WebServlet(urlPatterns = "/persist")
public class PersistServlet extends HttpServlet {
  @Inject TransactionalCdiService cdiService;

  @Override protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    Cursus cursus = new Cursus();
    cursus.setTitel("Java EE 7");
    System.out.println("Before Cdi Service save");
    cdiService.save(cursus);
    System.out.println("After Cdi Service save");
  }
}
