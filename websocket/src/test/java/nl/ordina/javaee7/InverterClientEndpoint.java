package nl.ordina.javaee7;

import javax.websocket.*;

/**
 *
 */
@ClientEndpoint
public class InverterClientEndpoint {
  @OnOpen
  public void onOpen(Session client) {
    log("Connect");
  }

//  @OnClose
//  public void onClose(Session client) {
//    log("Removing client " + client);
//  }

  @OnMessage
  public void message(String message, Session client) {
    log("Got message from " + client + ": " + message);
  }

//  @OnError
//  public void onError(Throwable throwable, Session client) {
//    log("Got error from " + client);
//  }

  private void log(String msg) {
    System.out.println("InverterDataEndpoint " + hashCode() + " - " + msg);
  }

}
