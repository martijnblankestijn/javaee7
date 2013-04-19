package nl.ordina.javaee7.batch;

import javax.batch.api.partition.PartitionReducer;
import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

/**
 *
 */
public class InverterPartitionReducer implements PartitionReducer {
  private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @Override public void beginPartitionedStep() throws Exception {
    LOG.log(INFO, "beginPartitionedStep");
  }

  @Override public void beforePartitionedStepCompletion() throws Exception {
    LOG.log(INFO, "beforePartitionedStepCompletion");
  }

  @Override public void rollbackPartitionedStep() throws Exception {
    LOG.log(INFO, "rollbackPartitionedStep");
  }

  @Override public void afterPartitionedStepCompletion(final PartitionStatus partitionStatus) throws Exception {
    LOG.log(INFO, "afterPartitionedStepCompletion, status {0}", partitionStatus);
  }
}
