package nl.ordina.javaee7.batch;

import javax.batch.annotation.ItemWriter;
import javax.batch.annotation.WriteItems;
import java.util.List;


/**
 *
 */
@ItemWriter(value = "InverterDataWriter")
public class InverterDataWriter {
    @WriteItems
    public void writeIt(List<String> lijst)  throws Exception {
        System.out.println("WRITING " + lijst);
    }
}
