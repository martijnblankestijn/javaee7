package nl.ordina.javaee7.rest.boundary;

import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;

/**
 *
 */
public class SystemResourceTest {
  @Test
  public void testGetModel() {
    String endpoint = "http://localhost:8080/rest/systemen/1";
    Invocation.Builder request = ClientBuilder.newClient().target(endpoint).request();
    System.out.println(request.get());

  }

}
