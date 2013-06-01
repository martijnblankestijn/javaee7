package nl.ordina.javaee7.rest.client.api;

import org.glassfish.jersey.filter.LoggingFilter;
import sun.org.mozilla.javascript.json.JsonParser;

import javax.json.*;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import static nl.ordina.javaee7.rest.client.ParameterScanner.createOAuthHeader;

/**
 *
 */
public class DropboxDirectoryListing {

  private final String oAuthHeader;

  public DropboxDirectoryListing(final String oAuthHeader) {
    this.oAuthHeader = oAuthHeader;
  }

  public void execute() {
    String metadataUrl = "https://api.dropbox.com/1/metadata/sandbox";

    // TO DELETE
    String response = ClientBuilder.newBuilder()
            .register(new LoggingFilter(Logger.getAnonymousLogger(), true))
            .build()
            .target(metadataUrl)
            .request()
            .header("Authorization", oAuthHeader)
            .get(String.class);

    JsonReader reader = Json.createReader(new StringReader(response));
    JsonObject jsonObject = reader.readObject();
    JsonArray contents = jsonObject.getJsonArray("contents");

    System.out.println("DIRECTORY LISTING OF "  + jsonObject.getString("path"));

    for (JsonValue content : contents) {
      JsonObject file  = (JsonObject) content;
      System.out.println("Path: " + file.getString("path") + ", rev: " + file.getInt("revision"));
    }
    // TO DELETE
  }


  public static void main(String[] args) throws IOException {
    DropboxDirectoryListing command = new DropboxDirectoryListing(AuthorizationHeaderFactory.createOAuthHeader());
    command.execute();
  }

}
