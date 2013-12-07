package nl.ordina.javaee7.rest.boundary;


import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static nl.ordina.javaee7.rest.boundary.EndpointInfo.CONTEXT_ROOT;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class ClientApiTest {
  private final static String ENDPOINT = CONTEXT_ROOT + "/systemen/1";

  @Test
  public void testResponse() {
    Response response = ClientBuilder.newClient().target(ENDPOINT).request().get();
    assertEquals(200, response.getStatus());
    System.out.println("Entity: " + response.getEntity());
  }

  //
  @Test
  public void testResponseEntity() {
    Client client = ClientBuilder.newClient();

    Systeem systeem = client.target(ENDPOINT).request(MediaType.APPLICATION_XML).get(Systeem.class);
    assertEquals(1, systeem.getId());
  }
}
