package nl.ordina.javaee7.rest.client.authentication;

import nl.ordina.javaee7.rest.client.ParameterScanner;
import org.glassfish.jersey.filter.LoggingFilter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

/**
 * See https://www.dropbox.com/developers/blog/20
 */
public class DropboxAppAutorization {
  private static final String AUTORIZE_URL = "https://www.dropbox.com/1/oauth/authorize?";
  private static final String REQUEST_TOKEN_URL = "https://api.dropbox.com/1/oauth/request_token";
  private static final String ACCESS_TOKEN_URL = "https://api.dropbox.com/1/oauth/access_token";

  private final String appKey;
  private final String appSecret;

  public DropboxAppAutorization(final String appKey, final String appSecret) {
    this.appKey = appKey;
    this.appSecret = appSecret;
  }

  public void authorizeApp() throws Exception {
    Map<String, String> genericParameters =  new HashMap<>();
    genericParameters.put("oauth_version", "1.0");
    genericParameters.put("oauth_signature_method", "PLAINTEXT");
    genericParameters.put("oauth_consumer_key", appKey);
    genericParameters.put("oauth_signature", appSecret + "&");

    Client client = ClientBuilder.newBuilder()
            .register(new LoggingFilter(Logger.getLogger("iets"), true))
            .build();
    //
    // Request OAUTH Token
    //
    String responseString = client
            .target(REQUEST_TOKEN_URL)
            .request()
            .accept(APPLICATION_JSON_TYPE)
            .header("Authorization", ParameterScanner.createOAuthHeader(genericParameters))
            .post(null).readEntity(String.class);

    System.out.println("RESPONSE    : " + responseString);

    //
    // Authorize the APP in DropboxAppAutorization
    //
    String authorizationUrl = AUTORIZE_URL + responseString;
    System.out.println("Autorize App with url (next line):\n" + authorizationUrl);

    Process process = Runtime.getRuntime().exec("firefox " + authorizationUrl);
    int exitStatus = process.waitFor();
    System.out.println("Exit value of browser: " + exitStatus);


    //
    // Authorize
    //
    Map<String, String> requestToken = ParameterScanner.parse(responseString);
    genericParameters.put("oauth_signature", appSecret + "&" + requestToken.get("oauth_token_secret"));
    genericParameters.put("oauth_token", requestToken.get("oauth_token"));

    String authorizationResponse =
            client
                    .target(ACCESS_TOKEN_URL)
                    .request()
                    .header("Authorization", ParameterScanner.createOAuthHeader(genericParameters))
                    .post(null)
                    .readEntity(String.class);

    System.out.println("RESPONSE    : " + authorizationResponse);

    // FOR LATER USE
    Map<String, String> acceptToken = ParameterScanner.parse(authorizationResponse);

    for (Map.Entry<String, String> entry : acceptToken.entrySet()) {
      System.out.println("\tprivate final String " + entry.getKey() + " = \"" + entry.getValue() + "\";");
    }

  }

  public static void main(String[] args) throws Exception {
    String appKey = args[0];
    String appSecret = args[1];
    DropboxAppAutorization appAutorization = new DropboxAppAutorization(appKey,appSecret);
    appAutorization.authorizeApp();
  }

}
