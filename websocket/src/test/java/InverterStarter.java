import nl.ordina.javaee7.server.websockets.inverter.InverterEndpoint;
import org.glassfish.tyrus.server.Server;

import javax.websocket.DeploymentException;
import java.io.IOException;

/**
 *
 */
public class InverterStarter {
    public static void main(String[] args) {
        Server server = new Server("localhost", 8025, "/websocket", InverterEndpoint.class);
        try {
            server.start();
            System.out.println("Press any key to stop the server...");
            System.in.read();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (DeploymentException e) {
            e.printStackTrace();
        } finally {
            server.stop();
            System.out.println("Server stopped.");
        }

    }
}
