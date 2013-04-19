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
    JsonObject jsonObject = Json.createObjectBuilder()
            .add("id", 5)
            .add("serialNumber", "ABC1223")
            .add("capacity", 125)
            .build();

    StringWriter output = new StringWriter();
    JsonWriter writer = Json.createWriter(output);
    writer.writeObject(jsonObject);
    writer.close();

    String input = output.getBuffer().toString();
    System.out.println("MODEL API: " + input);

JsonReader reader = Json.createReader(new StringReader(input));

Systeem systeem = new Systeem();

JsonObject object = reader.readObject();
systeem.setSerialNumber(object.getString("serialNumber"));
systeem.setId(object.getInt("id"));
systeem.setCapacity(object.getInt("capacity"));

    System.out.println("Systeem JAVA: " + systeem);

    reader = Json.createReader(new StringReader(input));
    JsonStructure jsonStructure = reader.read();
    JsonValue.ValueType valueType = jsonStructure.getValueType();
    System.out.println("ValueType: " + valueType);


  }
}
