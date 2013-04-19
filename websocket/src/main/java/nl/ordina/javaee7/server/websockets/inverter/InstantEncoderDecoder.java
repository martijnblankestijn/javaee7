package nl.ordina.javaee7.server.websockets.inverter;

import javax.json.*;
import javax.websocket.*;
import javax.xml.bind.DatatypeConverter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import static java.util.logging.Level.*;

/**
 *
 */
public class InstantEncoderDecoder implements Encoder.Text<InverterData>, Decoder.Text<InverterData> {
  private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

  @Override
  public String encode(InverterData instant) throws EncodeException {
    try {
      return encodeJson(instant);
    } catch (RuntimeException e) {
      LOG.log(WARNING, e.toString() + " error while encoding instant");
      throw new EncodeException(instant, "Error encoding instant", e);
    }
  }

  private String encodeJson(InverterData instant) {
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    addIfHasValue(objectBuilder, "serialNumber", instant.serialNumberInverter);
    addIfHasValue(objectBuilder, "temperature", instant.temperature);
    addIfHasValue(objectBuilder, "vpv", instant.vpv);
    addIfHasValue(objectBuilder, "iac", instant.iac);
    addIfHasValue(objectBuilder, "vac", instant.vac);
    addIfHasValue(objectBuilder, "fac", instant.fac);
    addIfHasValue(objectBuilder, "pac", instant.pac);
    addIfHasValue(objectBuilder, "etotal", instant.etotal);
    addIfHasValue(objectBuilder, "htotal", instant.htotal);
    addIfHasValue(objectBuilder, "etoday", instant.etoday);
    addIfHasValue(objectBuilder, "etotalh", instant.etotalh);
    addIfHasValue(objectBuilder, "htotalh", instant.htotalh);

    if (instant.timestamp != null) {
      Calendar instance = Calendar.getInstance();
      instance.setTime(instant.timestamp);
      objectBuilder.add("timestamp", DatatypeConverter.printDate(instance));
    }
    JsonObject jsonObject = objectBuilder.build();

    String encoded = encode(jsonObject);
    LOG.log(FINER, "Encoded {0}", encoded);
    return encoded;
  }

  private void addIfHasValue(JsonObjectBuilder objectBuilder, String label, String value) {
    if (value != null) {
      objectBuilder.add(label, value);
    }
  }

  private void addIfHasValue(JsonObjectBuilder objectBuilder, String label, BigDecimal value) {
    if (value != null) {
      objectBuilder.add(label, value);
    }
  }

  @Override
  public InverterData decode(String encoded) throws DecodeException {
    System.out.println("Decode " + encoded);
    JsonObject jsonObject = readJsonObject(encoded);

    try {
      return getInverterData(jsonObject);
    } catch(RuntimeException e) {
      e.printStackTrace();
      throw new DecodeException(encoded, e.toString() + " error while decoding.");
    }
  }

  private InverterData getInverterData(JsonObject jsonObject) {
    InverterData instant = new InverterData();
    instant.serialNumberInverter = jsonObject.getString("serialNumber");
    instant.timestamp = getDate(jsonObject, "timestamp");
    instant.temperature = getBigDecimal(jsonObject, "temperature");
    instant.vpv = getBigDecimal(jsonObject, "vpv");
    instant.iac = getBigDecimal(jsonObject, "iac");
    instant.vac = getBigDecimal(jsonObject, "vac");
    instant.fac = getBigDecimal(jsonObject, "fac");
    instant.pac = getBigDecimal(jsonObject, "pac");
    instant.etotal = getBigDecimal(jsonObject, "etotal");
    instant.htotal = getBigDecimal(jsonObject, "htotal");
    instant.etoday = getBigDecimal(jsonObject, "etoday");
    instant.etotalh = getBigDecimal(jsonObject, "etotalh");
    instant.htotalh = getBigDecimal(jsonObject, "htotalh");
    return instant;
  }

  private Date getDate(JsonObject jsonObject, String label) {
    JsonString jsonString = jsonObject.getJsonString(label);
    if (jsonString==null) {
      return null;
    }
    return DatatypeConverter.parseDateTime(jsonString.getString()).getTime();
  }

  private BigDecimal getBigDecimal(JsonObject jsonObject, String label) {
    JsonNumber jsonNumber = jsonObject.getJsonNumber(label);
    if(jsonNumber == null) {
      return null;
    }
    return jsonNumber.bigDecimalValue();
  }


  @Override
  public void init(EndpointConfig endpointConfig) {
    LOG.log(CONFIG, "Initialization for {} ", endpointConfig);
  }

  @Override
  public void destroy() {
    LOG.log(CONFIG, "Destroy");
  }

  @Override
  public boolean willDecode(String decoded) {
    LOG.log(FINEST, "Decode '{}'", decoded);
    return true;
  }

  private String encode(JsonObject jsonObject) {
    StringWriter sw = new StringWriter();
    try (JsonWriter writer = Json.createWriter(sw)) {
      writer.writeObject(jsonObject);
    }
    return sw.getBuffer().toString();
  }

  private JsonObject readJsonObject(String decoded) {
    StringReader reader = new StringReader(decoded);
    JsonObject jsonObject = Json.createReader(reader).readObject();
    reader.close();
    return jsonObject;
  }
}
