package nl.ordina.javaee7.rest.client.api;

import org.glassfish.jersey.filter.LoggingFilter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static nl.ordina.javaee7.rest.client.ParameterScanner.createOAuthHeader;

/**
 *
 */
public class DropboxAccountInfo {
  private static final String ACCOUNT_INFO_URL = "https://api.dropbox.com/1/account/info";
  private final String oAuthHeader;

  public DropboxAccountInfo(final String oAuthHeader) {
    this.oAuthHeader = oAuthHeader;
  }

  public void showInfo() throws URISyntaxException, FileNotFoundException {
    Client client = ClientBuilder.newBuilder()
            .register(new LoggingFilter(Logger.getLogger("iets"), true))
            .build();

    String responseString = client
            .target(ACCOUNT_INFO_URL)
            .request()
            .accept(APPLICATION_JSON_TYPE)
            .header("Authorization", oAuthHeader)
            .post(null)
            .readEntity(String.class);

    System.out.println("RESPONSE    : " + responseString);
  }

  public static void main(String[] args) throws Exception {
    DropboxAccountInfo lister = new DropboxAccountInfo(AuthorizationHeaderFactory.createOAuthHeader());
    lister.showInfo();
  }
}
