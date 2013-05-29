package nl.ordina.javaee7.server.websockets.stock;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 *
 */
@ServerEndpoint("/stocks")
public class StockEndpoint {
  @OnMessage
  public void message(String message, Session client) throws IOException {
    System.out.println("Got: " + message + ", aantal " + client.getOpenSessions().size());
    for (Session peer : client.getOpenSessions()) {
      if(peer.isOpen()) {
          peer.getBasicRemote().sendText(message);
      }
    }
  }
}
