package nl.ordina.javaee7.rest.boundary.async;

import nl.ordina.javaee7.rest.boundary.EndpointInfo;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.StringWriter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import static nl.ordina.javaee7.rest.boundary.EndpointInfo.CONTEXT_ROOT;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class CursusResourceTest {
  private static final Logger LOG = Logger.getAnonymousLogger();
  private Client client;

  @Before
  public void before() {
    LoggingFilter loggingFilter = new LoggingFilter(Logger.getAnonymousLogger(), true);
    client = ClientBuilder.newBuilder()
            .register(loggingFilter)
            .build();
  }

  @Test
  public void get() {
    Response response = client.target(CONTEXT_ROOT + "/cursussen").request().get();
    assertEquals(200, response.getStatus());
  }

  @Test
  public void put() throws InterruptedException, ExecutionException, TimeoutException {
    JsonObject cursus = Json.createObjectBuilder()
            .add("titel", "Java EE 7")
            .build();
    StringWriter writer = new StringWriter();
    JsonWriter jsonWriter = Json.createWriter(writer);
    jsonWriter.writeObject(cursus);
    jsonWriter.close();


    LOG.info("Inhoud: " + writer.toString());

    Future<Response> future = client.target(CONTEXT_ROOT + "/cursussen/{code}")
            .resolveTemplate("code", "javaee7")
            .request()
            .async()
            .put(Entity.entity(writer.toString(), MediaType.APPLICATION_JSON_TYPE));

    LOG.info("Async call made");
    Response response = future.get(5, TimeUnit.SECONDS);
    LOG.info("" + response);
  }

  }
