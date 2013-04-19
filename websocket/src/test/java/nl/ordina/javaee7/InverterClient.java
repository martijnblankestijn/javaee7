package nl.ordina.javaee7;

import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 */
public class InverterClient {
  public static void main(String[] args) throws URISyntaxException, IOException, DeploymentException {
    URI uri = new URI("ws://localhost:8080/websocketsample/inverters/X12345");
    WebSocketContainer container = ContainerProvider.getWebSocketContainer();

    final ClientManager client = ClientManager.createClient();
    final String sendText = "{ \"systemId\":\"COMMANDLINE_CLIENT\",\"reading\":12} ";

    Session session;
    Endpoint endpoint = new Endpoint() {

      @Override
      public void onOpen(final Session session, EndpointConfig endpointConfig) {
        System.out.println("CONNECTED");


        try {
          session.getBasicRemote().sendText(sendText);
          System.out.println("Did remote call");
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          System.out.println("End it");
        }

        session.addMessageHandler(new MessageHandler.Whole<String>() {
          @Override
          public void onMessage(String message) {

            try {
              System.out.println("RECEIVED: " + message);
              session.getBasicRemote().sendText(sendText);
            } catch (IOException ex) {
              ex.printStackTrace();
            }
          }
        });
      }
      @Override
      public void onClose(Session session, CloseReason closeReason) {
        System.out.println("CloseReason: " + closeReason);
      }

      public void onError(Session session, Throwable thr) {
        System.err.println("ON ERROR");
        thr.printStackTrace();
       }
    };

    session = container.connectToServer(endpoint, ClientEndpointConfig.Builder.create().build(), uri);


//    session = container.connectToServer(InverterClientEndpoint.class, uri);
    System.out.println("Is open  : " + session.isOpen());
    System.out.println("Is secure: " + session.isSecure());
//
//    session.addMessageHandler(new MessageHandler.Whole<String>() {
//
//      @Override
//      public void onMessage(String s) {
//        System.out.println("Got " + s);
//      }
//    });
    try {
      Thread.sleep(500000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }
}