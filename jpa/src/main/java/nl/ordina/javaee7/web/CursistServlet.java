package nl.ordina.javaee7.web;

import nl.ordina.javaee7.ejb.CursistService;
import nl.ordina.javaee7.jpa.Cursist;
import nl.ordina.javaee7.jpa.Geslacht;
import nl.ordina.javaee7.jpa.Land;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 */
@WebServlet(urlPatterns = "/go")
public class CursistServlet extends HttpServlet {
  @EJB
  CursistService service;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
    String actie = request.getParameter("actie");
    if (actie == null) throw new RuntimeException("actie is verplicht");
    switch (actie) {
      case "stored":
        service.callStoredProcedure();
        break;
      case "storedNamed":
        service.callStoredProcedure2();
        break;
      case "aanmaken":
        // geeft nog een ClassCast
        service.opslaan(maakCursist("NL", Geslacht.MAN));
        service.opslaan(maakCursist("DE", Geslacht.VROUW));
        service.opslaan(maakCursist("BE", Geslacht.ONBEKEND));
        break;
      case "getConvertedFIeld":
        print(service.getLand(maakLand("NL")));
        break;

      case "getAll":
        print(service.getAll());
        break;

    }

  }

  private void print(List<Cursist> cursisten) {
    for (Cursist cursist : cursisten) {
      System.out.println(cursist);
    }
  }

  private Cursist maakCursist(String landcode, Geslacht geslacht) {
    Cursist cursist = new Cursist(geslacht);
    cursist.setLandVanHerkomst(maakLand(landcode));
    return cursist;
  }

  private Land maakLand(String code) {
    return new Land(code, "");
  }
}
