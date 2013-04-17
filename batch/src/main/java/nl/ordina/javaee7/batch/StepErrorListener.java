package nl.ordina.javaee7.batch;

import javax.batch.api.listener.AbstractStepListener;
import javax.batch.runtime.Metric;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.WARNING;

/**
 *
 */
public class StepErrorListener extends AbstractStepListener {
  private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @Inject StepContext stepContext;

  @Override public void afterStep() throws Exception {
    if(stepContext.getException() == null) {
      return;
    }
    Exception e = stepContext.getException();
    LOG.log(WARNING, "Exception in step {0}, ExitStatus={1}", new Object[] {stepContext.getStepName(), stepContext.getExitStatus()});
    LOG.log(WARNING, "Properties={0}", new Object[] {stepContext.getProperties()});
    if (LOG.isLoggable(FINE)) {
      Metric[] metrics = (Metric[]) stepContext.getMetrics();
      LOG.log(FINE, "Metrics");
      for (int i = 0; i < metrics.length; i++) {
        Metric metric = metrics[i];
        LOG.log(FINE, "{0}={1}", new Object[]{metric.getType(), metric.getValue()});
      }
    }
  }
}
