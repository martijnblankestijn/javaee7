package nl.ordina.javaee7.server.websockets.inverter;

import javax.websocket.Extension;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class InverterServerEndpointConfigurator extends ServerEndpointConfig.Configurator {
  public InverterServerEndpointConfigurator() {
    super();
  }

  @Override
  public String getNegotiatedSubprotocol(List<String> supported, List<String> requested) {
    String message = "getNegotiatedSubprotocol, supported: " + supported + ", requested: " + requested;
    print(message);
    return super.getNegotiatedSubprotocol(supported, requested);
  }


  @Override
  public List<Extension> getNegotiatedExtensions(List<Extension> installed, List<Extension> requested) {
    print("getNegotiatedExtensions, installed: " + installed + ", requested: " + requested);
    return super.getNegotiatedExtensions(installed, requested);
  }

  @Override
  public boolean checkOrigin(String originHeaderValue) {
    print("originHeaderValue: " + originHeaderValue);
    return super.checkOrigin(originHeaderValue);
  }

  @Override
  public boolean matchesURI(String path, URI requestUri, Map<String, String> templateExpansion) {
    print("matchesURI: " + path + ", uri: " + requestUri + ", templateExpansion: " + templateExpansion);
    return super.matchesURI(path, requestUri, templateExpansion);
  }

  @Override
  public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
    print("modifyHandshake");
    super.modifyHandshake(sec, request, response);    
  }

  @Override
  public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
    print("getEndpointInstance");
    return super.getEndpointInstance(endpointClass);
  }

  private void print(String message) {
    System.out.println("InverterServerEndpointConfigurator(" + hashCode() + "):" + message);
  }

}
