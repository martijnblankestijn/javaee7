package nl.ordina.javaee7.server.websockets.inverter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

/**
 *
 */
public class InstantDecoder implements Decoder.Text<InverterInstant> {
  @Override
  public void init(EndpointConfig endpointConfig) {
    System.out.println("init: " + endpointConfig);
  }

  @Override
  public void destroy() {
    System.out.println("destroy");
  }

  @Override
  public InverterInstant decode(String s) throws DecodeException {
    System.out.println("Decode: [" + s + "]");

    StringReader reader = new StringReader(s);
    JsonObject jsonObject = Json.createReader(reader).readObject();
    reader.close();

    InverterInstant instant = new InverterInstant();
    instant.systemId = jsonObject.getString("systemId");
    instant.reading = jsonObject.getInt("reading");

    System.out.println("Decoded from " + s);
    return instant;
  }

  @Override
  public boolean willDecode(String s) {
    System.out.println("Will decode: [" + s + "]");
    return true;
  }
}
