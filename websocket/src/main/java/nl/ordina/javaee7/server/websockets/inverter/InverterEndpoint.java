package nl.ordina.javaee7.server.websockets.inverter;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static javax.websocket.CloseReason.CloseCodes.CLOSED_ABNORMALLY;

/**
 *
 */
@ServerEndpoint(value = "/inverters/{inverterId}",
        configurator = InverterServerEndpointConfigurator.class,
        encoders = InstantEncoderDecoder.class,
        decoders = InstantEncoderDecoder.class)
public class InverterEndpoint {
  @OnOpen
  public void onOpen(Session client) {
    log(" Adding client with id " + client.getId() + ", aantal open " + client.getOpenSessions().size());
  }

  @OnClose
  public void onClose(Session client) {
    log("Removing client " + client);
  }

  @OnMessage(maxMessageSize = 10000)
  public void message(@PathParam("inverterId") String inverterId, InverterData instant, Session client) throws IOException {
    log("Got message from " + client + " for " + inverterId + ": " + instant);

    for (Session peer : client.getOpenSessions()) {
      if (!client.equals(peer)) {
        try {
          peer.getBasicRemote().sendObject(instant);
        } catch (EncodeException e) {
          System.out.println(e.getClass().getSimpleName() + " writing to " + peer + " .... ignoring");
        } catch (IOException | RuntimeException e) {
          System.out.println(e.getClass().getSimpleName() + " writing to " + peer + " remove from sessions");
          peer.close(new CloseReason(CLOSED_ABNORMALLY, "Sorry"));
        }
      }

    }
  }

  @OnError
  public void onError(Throwable throwable, Session client) {
    log("Got error from " + client);
    throwable.printStackTrace();
  }

  private void log(String message) {
    System.out.println("InverterEndpoint - " + message);
  }

}
