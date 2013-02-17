package nl.ordina.javaee7.batch;

import nl.mb.inverter.entity.InverterData;

import javax.batch.annotation.ItemWriter;
import javax.batch.annotation.WriteItems;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 */
@ItemWriter(value = "InverterDataWriter")
public class InverterDataSystemOutWriter {
    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @WriteItems
    public void writeIt(List<InverterData> lijst)  throws Exception {
        for (InverterData data : lijst) {
            LOG.log(Level.FINEST, "Write data " + data.temperature);
        }
    }
}
