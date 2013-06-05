package nl.ordina.javaee7.rest.client.dropbox;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

import static nl.ordina.javaee7.rest.client.dropbox.AuthorizationHeaderFactory.createOAuthHeader;

/**
 *
 */
public class Uploader {
  private final String oAuthHeader;

  public Uploader(final String oAuthHeader) {
    this.oAuthHeader = oAuthHeader;
  }

  private void execute() {
    // And now something more exciting
    File f = getFile("/DukeOnSax.gif");
    FileInputStream fis = openFileInputStream(f);

    String url = "https://api-content.dropbox.com/1/files_put/sandbox/{file}";

    // BEGIN TO DELETE
    // No logging of binary file
    String uploadResponse = ClientBuilder.newClient().target(url)
            .resolveTemplate("file", "DukeOnSax.gif")
            .request()
            .header("Authorization", oAuthHeader)
            .put(Entity.entity(fis, "image/gif"))
            .readEntity(String.class);
    System.out.println(uploadResponse);
    // END TO DELETE
  }

  private FileInputStream openFileInputStream(final File f)  {
    try {
      return new FileInputStream(f);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private File getFile(final String resourceLocation)  {
    URL url = getClass().getResource(resourceLocation);
    try {
      return new File(url.toURI());
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * <ul>
   * <li><a href="https://www.dropbox.com/developers">Developers</a></li>
   * <li><a href="https://www.dropbox.com/home/">Dropbox Home</a></li>
   * </ul>
   */
  public static void main(String[] args) {
    Uploader uploader = new Uploader(createOAuthHeader());
    uploader.execute();
  }

}
