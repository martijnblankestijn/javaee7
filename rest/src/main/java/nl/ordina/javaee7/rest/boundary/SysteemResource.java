package nl.ordina.javaee7.rest.boundary;

import nl.ordina.javaee7.rest.boundary.resourcemethodfilter.Cached;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
@Path("systemen") @ApplicationScoped
@Cached
public class SysteemResource {

  private ConcurrentHashMap<Long, Systeem> systemen = new ConcurrentHashMap<>();
  {
    Systeem systeem = new Systeem();
    systeem.setId(125L);
    systeem.setCapacity(1200);
    systeem.setSerialNumber("12ABC");
    systemen.putIfAbsent(systeem.getId(), systeem);
  }

  @GET @Path("{systeemId}") @Produces(MediaType.APPLICATION_JSON)
  public Systeem get(@PathParam("systeemId") long id) {

    System.out.println("Systeem requested with id " + id);

    Systeem systeem = systemen.get(id);
    if(systeem==null) {
      throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
    return systeem;
  }

  @PUT @Path("{systeemId}") @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response put(@PathParam("systeemId") long id, Systeem systeem) {
    System.out.println("PUT for " + id);
    systemen.put(id, systeem);
    return Response.accepted().build();
  }
}
