package nl.ordina.javaee7.server.websockets.inverter;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 */
@ServerEndpoint(value = "/inverters/{inverterId}",
        configurator = InverterServerEndpointConfigurator.class,
        encoders = InstantEncoderDecoder.class,
        decoders = InstantEncoderDecoder.class)
public class InverterEndpoint {
  // Sessions should static to share with different clients
  private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

  @OnOpen
  public void onOpen(Session client) {
    log(" Adding client with id " + client.getId() + ", aantal open " + client.getOpenSessions().size());
    sessions.add(client);

  }

  @OnClose
  public void onClose(Session client) {
    log("Removing client " + client);
  }

  @OnMessage(maxMessageSize = 10000)
  public void message(@PathParam("inverterId") String inverterId, InverterData instant, Session client) {
//  public void message(@PathParam("inverterId") String inverterId, String instant, Session client) {
    log("Got message from " + client + " for " + inverterId + ": " + instant);

    for (Iterator<Session> it = sessions.iterator(); it.hasNext() ;) {
      Session peer = it.next();
      if (!client.equals(peer)) {
        try {
          peer.getBasicRemote().sendObject(instant);
        } catch (EncodeException e) {
          System.out.println(e.getClass().getSimpleName() + " writing to " + peer + " .... ignoring");
        } catch (IOException | RuntimeException e) {
          System.out.println(e.getClass().getSimpleName() + " writing to " + peer + " remove from sessions");
          it.remove();
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

  private void sendMessageOnlyToOthers(String message, Session client) {
    for (Session peer : sessions) {
      // broadcast only to the other clients
      if (peer.equals(client)) {
        System.out.println("Client equals found session, will not echo!");
      } else {
        try {
          log("Sending message to " + peer);
          peer.getBasicRemote().sendText(message);
        } catch (IOException e) {
          System.out.println("IO Exception writing to " + peer + " .... ignoring");
        }
      }
    }
  }

}
