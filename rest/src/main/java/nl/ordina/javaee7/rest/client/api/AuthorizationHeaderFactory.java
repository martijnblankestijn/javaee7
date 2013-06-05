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
    Properties properties = readProperties("/dropbox.properties");
    Map<String, String> genericParameters = convertToMap(properties);

    // returns something like:
    // Authorization: OAuth oauth_signature="o2b4gl15yz5vdgu&z8ygnzlnrr6tljd",
    // oauth_version="1.0",oauth_consumer_key="411mtiy1cb4e5ve",
    // oauth_signature_method="PLAINTEXT",oauth_token="kuiwssj7v9or4k0"
    return ParameterScanner.createOAuthHeader(genericParameters);
  }

  private static Map<String, String> convertToMap(final Properties properties) {
    Map<String, String> genericParameters =  new HashMap<>();
    for (Map.Entry<Object, Object> entry : properties.entrySet()) {
      genericParameters.put((String)entry.getKey(), (String) entry.getValue());
    }
    return genericParameters;
  }

  private static Properties readProperties(final String propertyFile) {
    final Properties properties = new Properties();
    try {
      properties.load(DropboxDirectoryListing.class.getResourceAsStream(propertyFile));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return properties;
  }


}
