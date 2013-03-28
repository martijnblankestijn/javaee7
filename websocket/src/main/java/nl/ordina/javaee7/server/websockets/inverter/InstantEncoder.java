package nl.ordina.javaee7.server.websockets.inverter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.StringWriter;

/**
 *
 */
public class InstantEncoder implements Encoder.Text<InverterInstant> {
  @Override
  public String encode(InverterInstant instant) throws EncodeException {
    JsonObject jsonObject = Json.createObjectBuilder()
            .add("systemId", instant.systemId)
            .add("reading", instant.reading)
            .build();
    StringWriter sw = new StringWriter();
    try (JsonWriter writer = Json.createWriter(sw)) {
      writer.writeObject(jsonObject);
    }
    System.out.println("Encoded to " + sw.getBuffer());
    return sw.getBuffer().toString();
  }

  @Override
  public void init(EndpointConfig endpointConfig) {
    System.out.println("init: " + endpointConfig);
  }

  @Override
  public void destroy() {
    System.out.println("destroy");
  }
}
