package nl.ordina.javaee7.rest.client.api;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

import static nl.ordina.javaee7.rest.client.api.AuthorizationHeaderFactory.createOAuthHeader;

/**
 *
 */
public class DropboxUploader {
  private final String oAuthHeader;

  public DropboxUploader(final String oAuthHeader) {
    this.oAuthHeader = oAuthHeader;
  }

  private void execute() {
    // And now something exciting
    File f = getFile("/DukeOnSax.gif");
    FileInputStream fis = openFileInputStream(f);
    final long length = f.length();

    String PUT_FILE_URL = "https://api-content.dropbox.com/1/files_put/sandbox/{file}";

    // BEGIN TO DELETE
    // No logging of binary file
    String uploadResponse = ClientBuilder.newClient().target(PUT_FILE_URL)
            .resolveTemplate("file", "DukeOnSax.gif")
            .request()
            .header("Authorization", oAuthHeader)
            .header("Content-Length", length)
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

  public static void main(String[] args) {
    DropboxUploader uploader = new DropboxUploader(createOAuthHeader());
    uploader.execute();
  }

}
