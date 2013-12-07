package nl.ordina.javaee7.json;

import org.junit.Test;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import java.io.StringReader;
import java.io.StringWriter;

/**
 *
 */
public class JsonStreamingApiTest {
  @Test
  public void showJsonStreamingAPIPresentation() {
    System.out.println("Show Streaming API output");
    StringWriter output = new StringWriter();
JsonGenerator writer = Json.createGenerator(output)
        .writeStartObject()
        .write("id", 5)
        .write("serialNumber", "ABC1223")
        .write("capacity", 125)
        .writeEnd();
writer.flush();
writer.close();
    System.out.println("JSON: " + output.getBuffer());


    String input = output.getBuffer().toString();

String key = null;  Systeem systeem = null;
JsonParser parser = Json.createParser(new StringReader(input));
while (parser.hasNext()) {
  switch (parser.next()) {
    case START_OBJECT:   systeem = new Systeem(); break;
    case END_OBJECT:     System.out.println(systeem); return;
    case KEY_NAME:       key = parser.getString(); break;
    case VALUE_STRING:   systeem.setSerialNumber(parser.getString()); break;
    case VALUE_NUMBER:   switch (key) {
	        case "capacity": systeem.setCapacity(parser.getLong());       break;
	        case "id":       systeem.setId(parser.getLong());             break;
	        default:         throw new IllegalArgumentException("Error");
	      } break;
    case END_ARRAY: 	break;
    case START_ARRAY: 	break;
    case VALUE_FALSE:	break;
    case VALUE_NULL:	break;
    case VALUE_TRUE:	break;
  }}
}


  @Test
  public void showJsonStreamingAPI() {
    System.out.println("Show Streaming API output");
    StringWriter output = new StringWriter();
    JsonGenerator writer = Json.createGenerator(output)
            .writeStartObject()
            .write("id", 5)
            .write("serialNumber", "ABC1223")
            .write("capacity", 125)
            .writeEnd();
    writer.close();
    System.out.println("Streaming API: " + output.getBuffer());


    String input = output.getBuffer().toString();
    JsonParser parser = Json.createParser(new StringReader(input));

    while (parser.hasNext()) {
      JsonParser.Event event = parser.next();
      switch (event) {
        case START_OBJECT:
          Systeem systeem = parseSystem(parser);
          System.out.println("JAVA object: " + systeem);
          break;
      }
    }
    genericParsingStreaming(Json.createParser(new StringReader(input)));

  }

  private void genericParsingStreaming(final JsonParser parser) {
    while (parser.hasNext()) {
      switch (parser.next()) {
        case START_OBJECT:
          System.out.println("Start object at " + parser.getLocation().getLineNumber() + ":" + parser.getLocation().getColumnNumber());
          break;
        case END_OBJECT:
          System.out.println("End object at " + parser.getLocation().getLineNumber() + ":" + parser.getLocation().getColumnNumber());
          break;
        case KEY_NAME:
          System.out.print("Key: " + parser.getString() + "=");
          break;
        case VALUE_STRING:
          System.out.println(parser.getString() + "(String)");
          break;
        case VALUE_NUMBER:
          System.out.println(parser.getLong() + "(Long)");
      }

    }
  }

  private Systeem parseSystem(JsonParser parser) {
    final Systeem systeem = new Systeem();
    String key = null;
    while (parser.hasNext()) {
      JsonParser.Event event = parser.next();
      switch (event) {
        case END_OBJECT:
          return systeem;
        case KEY_NAME:
          key = parser.getString();
          break;
        case VALUE_STRING:
          String value = parser.getString();
          switch (key) {
            case "serialNumber":
              systeem.setSerialNumber(parser.getString());
              break;
            default:
              throw new IllegalArgumentException("Do not understand key (String): " + key);
          }
          break;
        case VALUE_NUMBER:
          switch (key) {
            case "capacity": systeem.setCapacity(parser.getLong());               break;
            case "id":       systeem.setId(parser.getLong());                     break;
            default:         throw new IllegalArgumentException("Do not understand key (Number): " + key);
          }
          break;
        default:
          throw new IllegalStateException("Unsupported event " + event);
      }
    }
    throw new IllegalStateException("Did not receive a complete system: " + systeem);
  }
}