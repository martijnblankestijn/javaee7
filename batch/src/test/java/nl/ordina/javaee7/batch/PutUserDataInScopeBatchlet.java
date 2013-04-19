package nl.ordina.javaee7.batch;

import javax.batch.api.BatchProperty;
import javax.batch.api.Batchlet;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class PutUserDataInScopeBatchlet implements Batchlet {
  public static final String INVALID_USERDATA = "INVALID_USERDATA";
  public static final String NO_USERDATA = "NO_USERDATA";
  public static final String NO_FILES = "NO_FILES";
  public static final String FILES_AVAILABLE = "FILES_AVAILABLE";

  @BatchProperty private String action;
  @Inject JobContext jobContext;

  @Override public String process() throws Exception {

    switch (action) {
      case INVALID_USERDATA:
        jobContext.setTransientUserData("THIS IS SO WRONG");
        break;
      case NO_USERDATA:
        jobContext.setTransientUserData(null);
        break;
      case NO_FILES:
        jobContext.setTransientUserData(new ArrayList<String>());
        break;
      case FILES_AVAILABLE:
        jobContext.setTransientUserData(Arrays.asList("test"));
        break;
      default:
        throw new IllegalArgumentException("Onbekende actie");
    }
    return null;
  }

  @Override public void stop() throws Exception {
    // do nothing
  }
}
