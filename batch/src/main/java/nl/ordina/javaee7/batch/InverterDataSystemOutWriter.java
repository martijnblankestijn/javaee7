package nl.ordina.javaee7.batch;

import javax.batch.api.chunk.ItemWriter;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 */
public class InverterDataSystemOutWriter implements ItemWriter{
    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @Override public void open(final Serializable serializable) throws Exception {
    LOG.log(Level.FINEST, "Open " + serializable);
  }

  @Override public void close() throws Exception {
    LOG.log(Level.FINEST, "Close");
  }

  @Override public void writeItems(final List<Object> lijst) throws Exception {
    for (Object data : lijst) {
      LOG.log(Level.FINEST, "Write data " + data);
    }
  }

  @Override public Serializable checkpointInfo() throws Exception {
    LOG.log(Level.FINEST, "Checkpointinfo");
    return null;
  }
}
