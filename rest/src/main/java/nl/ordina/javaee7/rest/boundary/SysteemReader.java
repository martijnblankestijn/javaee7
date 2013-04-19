package nl.ordina.javaee7.rest.boundary;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 *
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class SysteemReader implements MessageBodyReader<Systeem> {
  @Override public boolean isReadable(final Class<?> aClass, final Type type, final Annotation[] annotations, final MediaType mediaType) {
    return Systeem.class.isAssignableFrom(aClass);
  }

  @Override public Systeem readFrom(final Class<Systeem> systeemClass, final Type type, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, String> stringStringMultivaluedMap, final InputStream inputStream) throws IOException, WebApplicationException {
    Systeem systeem = new Systeem();
//    systeem.setId();
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }
}
