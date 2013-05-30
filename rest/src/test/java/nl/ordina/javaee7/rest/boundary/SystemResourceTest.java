package nl.ordina.javaee7.rest.boundary;

import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

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


  @Test
  public void testPut() {
    Long id = 101L;
    String endpoint = "http://localhost:8080/rest/systemen/" + id;

    WebTarget target = ClientBuilder.newClient().target(endpoint);
    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), target.request().get().getStatus());

    Systeem systeem = new Systeem();
    systeem.setId(id);
    systeem.setCapacity(180);
    systeem.setSerialNumber("12123124234");

    target.request().buildPut(Entity.entity(systeem, MediaType.APPLICATION_XML)).invoke();
    System.out.println("PUT IT");

    assertEquals(Response.Status.OK.getStatusCode(), target.request().get().getStatus());

  }

}
