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

public class CachingFilter implements ContainerRequestFilter, ContainerResponseFilter {
  private ConcurrentHashMap<Object, Response> cache = new ConcurrentHashMap<>();

  public CachingFilter() {
    System.out.println("Constructor");
  }

  @Override public void filter(ContainerRequestContext req) throws IOException {
    System.out.println("ServiceCachingFilterL filterRequest");

    URI uri = req.getUriInfo().getAbsolutePath();
    Response response = cache.get(uri);
    if (response != null) {
      System.out.println("FOUND RESPONSE for uri " + uri + ".");
      req.abortWith(response);
    }

  }

  @Override
  public void filter(ContainerRequestContext req, ContainerResponseContext resp) throws IOException {
    System.out.println("ServiceCachingFilterL filterResponse");

    URI uri = req.getUriInfo().getAbsolutePath();
    cache.putIfAbsent(uri, Response.ok(resp.getEntity()).build());
    System.out.println("PUT IN CACHE with URI: " + uri + " for " + resp.getEntity());

    MultivaluedMap<String, String> stream = resp.getStringHeaders();
    System.out.println("Headers: ");
    for (Map.Entry<String, List<String>> s : stream.entrySet()) {
      System.out.println(s.getKey() + " " + s.getValue());
    }
  }
}
