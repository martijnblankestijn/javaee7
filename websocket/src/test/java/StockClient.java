import javax.json.Json;
import javax.json.JsonArray;
import javax.websocket.*;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 */
@ClientEndpoint
public class StockClient {
 public static void main(String[] args) throws URISyntaxException, IOException, DeploymentException, InterruptedException {
  URI uri = new URI("ws://localhost:8080/websocketsample/stocks");
  Session session = ContainerProvider.getWebSocketContainer()
    .connectToServer(StockClient.class, uri);
  for (int i = 0; i < 100; i++) {
   try (Writer v = session.getBasicRemote().getSendWriter()) {
    JsonArray array = Json.createArrayBuilder()
      .add(System.currentTimeMillis())
      .add(Math.random() * 100)
      .build();
    Json.createWriter(v).write(array);
    Thread.sleep(300);
   }

  }
 }

 @OnMessage
 public void t(String msg, Session server) {
  System.out.println(msg);
 }
}
