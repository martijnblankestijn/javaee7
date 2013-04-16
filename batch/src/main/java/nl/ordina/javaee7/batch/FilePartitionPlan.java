package nl.ordina.javaee7.batch;

import javax.batch.api.partition.PartitionPlan;
import java.util.Properties;

/**
 *
 */
public class FilePartitionPlan implements PartitionPlan {
  private int partitionCount;
  private int threadCount;
  private Properties[] partitionProperties;
  private boolean partitionsOverride;

  @Override public void setPartitions(final int partitionCount) {
    this.partitionCount = partitionCount;
  }

  @Override public void setPartitionsOverride(final boolean partitionsOverride) {
    this.partitionsOverride = partitionsOverride;
  }

  @Override public boolean getPartitionsOverride() {
    return partitionsOverride;
  }

  @Override public void setThreads(final int threadCount) {
    this.threadCount = threadCount;
  }

  @Override public void setPartitionProperties(final Properties[] partitionProperties) {
    this.partitionProperties = partitionProperties;
  }

  @Override public int getPartitions() {
    return partitionCount;
  }

  @Override public int getThreads() {
    return threadCount;
  }

  @Override public Properties[] getPartitionProperties() {
    return partitionProperties;
  }
}

