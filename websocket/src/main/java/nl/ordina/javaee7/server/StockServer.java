package nl.ordina.javaee7.server;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;

/**
 *
 */
@ServerEndpoint("/stocks")
public class StockServer {
 @OnOpen
 public void z(Session client) {
  System.out.println("CONNECTED: " + client);
 }

 @OnMessage
 public void y(String msg, Session serverSession) throws IOException {
  Set<Session> sessions = serverSession.getOpenSessions();
  for (Session session : sessions) {
   session.getBasicRemote().sendText(msg);
  }
 }
}
