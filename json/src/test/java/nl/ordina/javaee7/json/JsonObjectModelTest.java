package nl.ordina.javaee7.json;

import org.junit.Test;

import javax.json.*;
import java.io.StringReader;
import java.io.StringWriter;

/**
 *
 */
public class JsonObjectModelTest {
  @Test
  public void showJsonObjectModelAPI() {
    System.out.println("Show Object Model API output");
JsonObject json = Json.createObjectBuilder()
        .add("id", 5)
        .add("serialNumber", "ABC1223")
        .add("capacity", 125)
        .build();

JsonWriter w = Json.createWriter(System.out);
w.write(json);
w.close();

StringWriter output = new StringWriter();
JsonWriter writer = Json.createWriter(output);
writer.writeObject(json);
writer.close();

    String input = output.getBuffer().toString();
    System.out.println("MODEL API: " + input);

JsonReader r = Json.createReader(new StringReader(input));

Systeem systeem = new Systeem();

JsonObject object = r.readObject();
systeem.setSerialNumber(object.getString("serialNumber"));
systeem.setId(object.getInt("id"));
systeem.setCapacity(object.getInt("capacity"));

    System.out.println("Systeem JAVA: " + systeem);

    r = Json.createReader(new StringReader(input));
    JsonStructure jsonStructure = r.read();
    JsonValue.ValueType valueType = jsonStructure.getValueType();
    System.out.println("ValueType: " + valueType);


  }
}
