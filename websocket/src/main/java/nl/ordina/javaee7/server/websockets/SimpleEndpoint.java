package nl.ordina.javaee7.server.websockets;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@ServerEndpoint(value = "/simple")
public class SimpleEndpoint {
  private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

  @OnOpen public void onOpen(Session client) {  sessions.add(client);   }

  @OnClose public void onClose(Session client) {  sessions.remove(client); }

  @OnMessage
  public void message(String message, Session client) {
    for (Session peer : sessions) {
      try {
        peer.getBasicRemote().sendText(message);
      } catch (IOException e) {
        System.out.println("IO Exception  on " + peer + " .... ignoring");
      }
    }
  }
}
