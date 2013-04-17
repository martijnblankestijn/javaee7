package nl.ordina.javaee7.batch;

import javax.batch.api.chunk.listener.SkipProcessListener;

/**
 *
 */
public class SkipInvalidRecordProcessListener implements SkipProcessListener {

  @Override public void onSkipProcessItem(final Object o, final Exception e) throws Exception {
    System.out.println("*************** onSkipProcessItem " + e.getMessage());
    e.printStackTrace(System.out);
  }
}
