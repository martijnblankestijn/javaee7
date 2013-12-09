package nl.ordina.javaee7.cdi;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/goCdi")
public class MyServlet extends HttpServlet {
    @Inject private LogService logService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(logService == null) {
            System.out.println("Logservice is null, zoals verwacht met bean discovery none");
        }
        else {
            throw new IllegalStateException("Bean discovery is none, geen injectie verwacht");
        }

    }
}
