package nl.ordina.javaee7.rest.client.api;

import nl.ordina.javaee7.rest.client.ParameterScanner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 */
public class AuthorizationHeaderFactory {
  public static String createOAuthHeader() {

    final Properties properties = new Properties();
    try {
      properties.load(DropboxDirectoryListing.class.getResourceAsStream("/dropbox.properties"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Map<String, String> genericParameters =  new HashMap<>();
    for (Map.Entry<Object, Object> entry : properties.entrySet()) {
      genericParameters.put((String)entry.getKey(), (String) entry.getValue());
    }
    return ParameterScanner.createOAuthHeader(genericParameters);
  }


}
