package nl.ordina.javaee7.batch;

import javax.batch.api.listener.StepListener;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

/**
 *
 */
public class StepLoggerListener implements StepListener {
  private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
  @Inject StepContext stepContext;

  @Override public void beforeStep() throws Exception {
    LOG.log(INFO, "Before executing step {0}, user data {1}", new Object[]{stepContext.getStepName() , stepContext.getPersistentUserData()});
  }

  @Override public void afterStep() throws Exception {
    LOG.log(INFO, "After executing step {0}, batch status [{1}], exit status [{2}]", new Object[]{stepContext.getStepName() , stepContext.getBatchStatus(), stepContext.getExitStatus()});
  }
}
