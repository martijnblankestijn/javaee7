package nl.ordina.javaee7.batch;

import javax.batch.api.partition.PartitionCollector;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class InverterPartitionCollector implements PartitionCollector {
  private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
  @Override public Serializable collectPartitionData() throws Exception {
    LOG.log(Level.INFO,  "collectPartitionData, returning OK");
    return "OK";
  }
}
