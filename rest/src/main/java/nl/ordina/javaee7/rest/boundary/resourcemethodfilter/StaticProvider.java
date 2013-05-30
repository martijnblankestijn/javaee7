package nl.ordina.javaee7.rest.boundary.resourcemethodfilter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 *
 */
@Provider
public class StaticProvider implements ContainerRequestFilter {
  @Override public void filter(final ContainerRequestContext requestContext) throws IOException {
    System.out.println("StaticProvider filtering");
  }
}
