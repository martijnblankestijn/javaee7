package nl.ordina.javaee7.batch;

import javax.batch.api.Decider;
import javax.batch.runtime.StepExecution;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class FilesAvailableDecider implements Decider{
  private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @Inject JobContext jobContext;

  @Override public String decide(final StepExecution[] stepExecutions) throws Exception {
    Object transientUserData = jobContext.getTransientUserData();
    LOG.log(Level.FINE, "Deciding what to do based on user data [{0}]", transientUserData);
    log(stepExecutions);

    if (transientUserData == null || !(transientUserData instanceof List)) {
      return "INVALID_USERDATA";
    }
    return ((List<? extends Object>) transientUserData).isEmpty() ? "NO_FILES" : "FILES_AVAILABLE" ;
  }

  private void log(final StepExecution[] stepExecutions) {
    for (StepExecution stepExecution : stepExecutions) {
      LOG.log(Level.FINE, "Executie van stap {0}", stepExecution);
    }
  }
}
