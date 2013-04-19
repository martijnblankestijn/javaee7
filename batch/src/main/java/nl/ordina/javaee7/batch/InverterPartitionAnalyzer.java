package nl.ordina.javaee7.batch;

import javax.batch.api.partition.PartitionAnalyzer;
import javax.batch.runtime.BatchStatus;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class InverterPartitionAnalyzer implements PartitionAnalyzer{
  private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @Override public void analyzeCollectorData(final Serializable serializable) throws Exception {
    LOG.log(Level.INFO, "Receiving data {0}", serializable);
  }

  @Override public void analyzeStatus(final BatchStatus batchStatus, final String s) throws Exception {
    LOG.log(Level.INFO, "AnalyzeStatus for status {0} and {1}", new Object[]{batchStatus, s});
  }
}
