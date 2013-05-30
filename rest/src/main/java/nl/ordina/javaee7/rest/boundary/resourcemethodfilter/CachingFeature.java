package nl.ordina.javaee7.rest.boundary.resourcemethodfilter;

import javax.ws.rs.GET;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

/**
 *
 */
@Provider
public class CachingFeature implements DynamicFeature {
  @Override
  public void configure(ResourceInfo info, FeatureContext ctx) {
    if(info.getResourceMethod().isAnnotationPresent(GET.class)) {
      ctx.register(CachingFilter.class);
    }
  }
}
