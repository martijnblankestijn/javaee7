package nl.ordina.javaee7.batch;

import javax.batch.api.Decider;
import javax.batch.runtime.StepExecution;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import java.util.List;

/**
 *
 */
public class FilesAvailableDecider implements Decider{
  @Inject JobContext jobContext;

  @Override public String decide(final StepExecution[] stepExecutions) throws Exception {
    Object transientUserData = jobContext.getTransientUserData();
    if (transientUserData == null) {
      return "INVALID_USERDATA";
    }
    List<String> paths = (List<String>) transientUserData;
    return paths.isEmpty() ? "NO_FILES" : "FILES_AVAILABLE" ;

  }
}
