package nl.ordina.javaee7.rest.boundary.async;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 *
 */
@ApplicationScoped
@Path("cursussen")
public class CursusResource {
  @Context private UriInfo uriInfo;
  private final static Logger LOG = Logger.getLogger("CursusResource");

  private ConcurrentHashMap<String, Cursus> cursussen;

  @PostConstruct
  public void init() {
    LOG.info("CursusResource: Init");
    this.cursussen = new ConcurrentHashMap<>();
  }

  @GET
  @Produces({"application/json", "application/xml"})
  public Collection<Cursus> getAll() {
    Collection<Cursus> list = new ArrayList<Cursus>();
    list.addAll(cursussen.values());
    return list;
  }

  @PUT
  @Path("{code}")
  @Consumes({"application/json", "application/xml"})
  public void createCursus(@Suspended AsyncResponse response, @PathParam("code") String code, Cursus cursus) {
    LOG.info("Received call for " + code + ", cursus " + cursus.titel);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
    LOG.info("Woken up");
    cursussen.putIfAbsent(code, cursus);

    Response.ResponseBuilder createdResponse = Response.created(uriInfo.getAbsolutePathBuilder().path(code).build());
    LOG.info("Resuming with response " + createdResponse);
    response.resume(createdResponse);
  }

  @GET    @Path("{code}")
  @Produces({"application/json", "application/xml"})
  public Cursus getCursus(@PathParam("code") String code) {
    LOG.info("Returning cursus for code " + code);
    return cursussen.get(code);
  }
}
