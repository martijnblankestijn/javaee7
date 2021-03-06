package nl.ordina.javaee7.rest.client.dropbox;

import org.glassfish.jersey.filter.LoggingFilter;

import javax.json.*;
import javax.ws.rs.client.ClientBuilder;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Logger;

import static nl.ordina.javaee7.rest.client.dropbox.AuthorizationHeaderFactory.createOAuthHeader;

/**
 *
 */
public class DirectoryListing {

  private final String oAuthHeader;

  public DirectoryListing(final String oAuthHeader) {
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

    System.out.println("LISTING OF "  + jsonObject.getString("path"));

    for (JsonValue content : contents) {
      JsonObject file  = (JsonObject) content;
      System.out.println("Path: " + file.getString("path")
              + ", rev: " + file.getInt("revision"));
    }
    // TO DELETE
  }


  /**
   * <ul>
   * <li><a href="https://www.dropbox.com/developers">Developers</a></li>
   * <li><a href="https://www.dropbox.com/home/">Dropbox Home</a></li>
   * </ul>
   */
  public static void main(String[] args) throws IOException {
    DirectoryListing command = new DirectoryListing(createOAuthHeader());
    command.execute();
  }

}
