package nl.ordina.javaee7.batch;

import javax.batch.api.chunk.listener.ItemProcessListener;

/**
 *
 */
public class LoggingItemProcessListener implements ItemProcessListener{
  @Override public void beforeProcess(final Object o) throws Exception {
    System.out.println("Before process record [" + o + "]");
  }

  @Override public void afterProcess(final Object o, final Object o2) throws Exception {
    System.out.println("After process morphed [" + o + "] into [" + o2 + "]");
  }

  @Override public void onProcessError(final Object o, final Exception e) throws Exception {
    System.out.println("Error processing [" + o + "] exception " + e.getClass().getName());
  }
}
