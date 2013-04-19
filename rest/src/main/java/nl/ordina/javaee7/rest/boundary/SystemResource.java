package nl.ordina.javaee7.rest.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Path("systemen")
public class SystemResource {
  @GET @Path("{systeemId}") @Produces(MediaType.APPLICATION_JSON)
  public Systeem get(@PathParam("systeemId") long id) {
    Systeem systeem = new Systeem();
    systeem.setId(125L);
    systeem.setCapacity(1200);
    systeem.setSerialNumber("12ABC");
    return systeem;
  }
}
