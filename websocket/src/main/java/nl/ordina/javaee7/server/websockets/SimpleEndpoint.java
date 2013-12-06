package nl.ordina.javaee7.server.websockets;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 *
 */
@ServerEndpoint(value = "/simple")
public class SimpleEndpoint {
  @OnOpen public void onOpen(Session client) { log("OPEN", client); }

  @OnClose public void onClose(Session client) { log("CLOSE", client); }

  @OnError public void onError(Session client, Throwable throwable) throws Exception { log(client, throwable); }

  @OnMessage
  public void message(String message, Session client) {
    System.out.println("Received " + message + ", distribute to " + client.getOpenSessions().size());
    for (Session peer : client.getOpenSessions()) {
      if(peer.isOpen()) {
        System.out.println("Sent message to " + peer);

        try {
          peer.getBasicRemote().sendText(message);
        } catch (IOException | NullPointerException e) {
          System.out.println("Got exception, closing peer");
          close(peer);
        }
      }
    }
  }

  private void close(final Session peer) {
    try {
      peer.close();
    } catch (Exception e1) {
      System.out.println("ignoring erorr " + e1.toString());
    }
  }

  private void log(final Session client, final Throwable throwable) throws IOException {
    System.out.println("-----Client gave error " + client);
    log("ERROR", client);
    throwable.printStackTrace(System.out);
    System.out.println("-----");
  }

  private void log(final String label, final Session client) {
    System.out.println(label + ",   open: " + client.getOpenSessions().size());

  }


}
