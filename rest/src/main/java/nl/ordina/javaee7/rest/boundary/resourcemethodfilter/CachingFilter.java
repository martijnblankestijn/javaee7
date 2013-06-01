package nl.ordina.javaee7.rest.boundary.resourcemethodfilter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WARNING .... WORK in progress, absolutely toy-code that should never be used in production
 */
public class CachingFilter implements ContainerRequestFilter, ContainerResponseFilter {
  private ConcurrentHashMap<Object, Response> cache = new ConcurrentHashMap<>();

  @Override public void filter(ContainerRequestContext req) throws IOException {
    Response response = cache.get(req.getUriInfo().getAbsolutePath());
    if (response != null) req.abortWith(response);
  }

  @Override
  public void filter(ContainerRequestContext req, ContainerResponseContext resp) throws IOException {
    URI uri = req.getUriInfo().getAbsolutePath();
    cache.putIfAbsent(uri, Response.ok(resp.getEntity()).build());

    MultivaluedMap<String, String> stream = resp.getStringHeaders();
    System.out.println("Headers: ");
    for (Map.Entry<String, List<String>> s : stream.entrySet()) {
      System.out.println(s.getKey() + " " + s.getValue());
    }
  }
  public CachingFilter() {
	    System.out.println("Constructor");
	  }

}
