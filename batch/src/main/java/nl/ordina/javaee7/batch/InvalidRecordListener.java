package nl.ordina.javaee7.batch;

import javax.annotation.PostConstruct;
import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.listener.SkipProcessListener;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import java.io.Writer;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class InvalidRecordListener implements SkipProcessListener {
  private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @BatchProperty private String invalidRecordDirectory;
  @Inject JobContext jobContext;

  @PostConstruct
  public void postConstruct() {
    Path directory = toPath();
    if (!(Files.exists(directory) && Files.isDirectory(directory))) {
      throw new IllegalStateException(invalidRecordDirectory + " is not a directory");
    }
    LOG.log(Level.INFO, "Logging invalid records to directory {0}", directory );
  }

  private Path toPath() {
    return Paths.get(invalidRecordDirectory);
  }


  @Override public void onSkipProcessItem(final Object item, final Exception ex) throws Exception {
    Path invalidRecordPath = toPath().resolve(jobContext.getJobName() + "-" + jobContext.getInstanceId() + "-" + jobContext.getExecutionId() + ".log");
    Charset charset = Charset.defaultCharset();
    LOG.log(Level.INFO, "Appending to file {0}, invalid record [{1}]", new Object[]{invalidRecordPath, item});
    try (Writer writer = Files.newBufferedWriter(invalidRecordPath, charset, StandardOpenOption.APPEND)) {
      writer.append((String)item);
    }
  }
}
