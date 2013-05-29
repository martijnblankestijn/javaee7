package nl.ordina.javaee7.server.websockets.stock;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 */
@ClientEndpoint
public class StockClient {
  public static void main(String[] args) throws URISyntaxException, IOException, DeploymentException, InterruptedException {
    Session session = ContainerProvider
            .getWebSocketContainer()
            .connectToServer(StockClient.class, new URI("ws://localhost:8080/websocketsample/stocks"));

    RemoteEndpoint.Basic basicRemote = session.getBasicRemote();
    for (int i = 0; i < 10; i++) {
      double v = Math.random() * 100;
      basicRemote.sendText("[" + System.currentTimeMillis() + ", " + v + "]");
      Thread.sleep(100);
    }
    Thread.sleep(2000);
  }

  @OnMessage public void got(String msg, Session client) {
    System.out.println(msg);
  }
}
