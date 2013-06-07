package nl.ordina.javaee7.rest.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 */
public class ParameterScanner {
  public static Map<String, String> parse(String input) {
    Map<String, String> results = new HashMap<>();
    Scanner scanner = new Scanner(input);
    scanner.useDelimiter("&");
    while (scanner.hasNext()) {
      Scanner parameterScanner = new Scanner(scanner.next());
      parameterScanner.useDelimiter("=");

      final String parameterName = parameterScanner.next();
      final String parameterValue = parameterScanner.hasNext() ? parameterScanner.next() : null;
      results.put(parameterName, parameterValue);
    }
    return results;
  }

  public static String createOAuthHeader(final Map<String, String> genericParameters) {
    boolean first = true;
    StringBuilder header = new StringBuilder("OAuth ");
    for (Map.Entry<String, String> entry : genericParameters.entrySet()) {
      if(first) {
        first=false;
      }
      else {
        header.append(",");
      }
      header.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
    }
    return header.toString();
  }
}
