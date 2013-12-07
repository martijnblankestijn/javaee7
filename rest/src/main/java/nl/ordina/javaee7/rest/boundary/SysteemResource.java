package nl.ordina.javaee7.rest.boundary;


import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

/**
 *
 */
@Path("systemen") @ApplicationScoped
public class SysteemResource {

  @GET @Path("{systeemId}") @Produces({APPLICATION_JSON, APPLICATION_XML})
  public Systeem get(@PathParam("systeemId") long id) {

    System.out.println("Systeem requested with id " + id);

    Systeem systeem = systemen.get(id);
    if(systeem==null) {
      throw new WebApplicationException(NOT_FOUND);
    }
    return systeem;
  }

  @PUT @Path("{systeemId}") @Produces({APPLICATION_XML, APPLICATION_JSON})
  public Response put(@PathParam("systeemId") long id, Systeem systeem) {
    System.out.println("PUT for " + id);
    systemen.put(id, systeem);
    return Response.accepted().build();
  }


  private ConcurrentHashMap<Long, Systeem> systemen = new ConcurrentHashMap<>();
  {
    Systeem systeem = new Systeem();
    systeem.setId(1L);
    systeem.setCapacity(1200);
    systeem.setSerialNumber("12ABC");
    systemen.putIfAbsent(systeem.getId(), systeem);
  }

}
