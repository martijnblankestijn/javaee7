package nl.ordina.javaee7.batch;

import javax.batch.api.partition.PartitionMapper;
import javax.batch.api.partition.PartitionPlan;
import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

/**
 *
 */
public class PartitionOnFilePartitionMapper implements PartitionMapper {
  private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @Inject StepContext stepContext;
  @Inject JobContext jobContext;

  @Override public PartitionPlan mapPartitions() throws Exception {
    Object transientUserData = jobContext.getTransientUserData();
    if(transientUserData == null || !(transientUserData instanceof List)) {
      throw new IllegalStateException("Lijst met bestanden verwachten als transient user data");
    }
    LOG.log(INFO, "jobContext: Map partitions with transient user data {0}", transientUserData);
    List<String> paths = (List<String>) transientUserData;

    Properties[] partitionProperties = new Properties[paths.size()];
    for (int i = 0; i < paths.size(); i++) {
      partitionProperties[i] = new Properties();
      partitionProperties[i].put("inverterCsvFile", paths.get(i));
    }

    FilePartitionPlan partitionPlan = new FilePartitionPlan();
    partitionPlan.setThreads(2);
    partitionPlan.setPartitions(paths.size());
    partitionPlan.setPartitionProperties(partitionProperties);
    partitionPlan.setPartitionsOverride(true);
    return partitionPlan;
  }
}
