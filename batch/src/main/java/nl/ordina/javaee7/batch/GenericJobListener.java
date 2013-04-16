package nl.ordina.javaee7.batch;

import javax.batch.api.listener.JobListener;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class GenericJobListener implements JobListener {
  private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @Inject JobContext jobContext;

  @Override public void beforeJob() throws Exception {
   LOG.log(Level.INFO, "Before Job {0}, instance {1} and execution {2}",
           new Object[]{jobContext.getJobName(), jobContext.getInstanceId(), jobContext.getExecutionId()});
  }

  @Override public void afterJob() throws Exception {
    LOG.log(Level.INFO, "After Job {0}, instance {1} and execution {2}, batch status [{3}], exit status [{4}]",
            new Object[]{jobContext.getJobName(), jobContext.getInstanceId(), jobContext.getExecutionId(),
            jobContext.getBatchStatus(), jobContext.getExitStatus()});
  }
}
