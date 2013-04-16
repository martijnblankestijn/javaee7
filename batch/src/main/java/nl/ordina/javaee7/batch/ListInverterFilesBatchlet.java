package nl.ordina.javaee7.batch;

import javax.batch.api.AbstractBatchlet;
import javax.batch.api.BatchProperty;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class ListInverterFilesBatchlet extends AbstractBatchlet {
  private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  private DirectoryStream.Filter<Path> csvFilter = new DirectoryStream.Filter<Path>() {
    @Override public boolean accept(final Path entry) throws IOException {
      boolean csv = entry.getFileName().toFile().getPath().endsWith("csv");
      LOG.log(Level.FINEST, "Beoordeel {0}: {1}", new Object[]{entry, csv});
      return csv;
    }
  };

  @Inject @BatchProperty private String inverterCsvDirectory;
  @Inject JobContext jobContext;

  @Override public String process() throws Exception {
    LOG.log(Level.INFO, "Reading from directory {0}", inverterCsvDirectory);

    Path path = Paths.get(inverterCsvDirectory);
    if(!Files.exists(path)) {
      throw new RuntimeException(inverterCsvDirectory + "does not exist.");
    }
    List<String> paths = new ArrayList<>();
    try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path, csvFilter)) {
      for (Path file : directoryStream) {
        paths.add(file.toFile().getCanonicalPath());
      }
    }
    LOG.log(Level.INFO, "Aantal gevonden bestanden {0}", paths.size());
    jobContext.setTransientUserData(paths);


    LOG.log(Level.INFO, "Transient user data on job context is {0}", jobContext.getTransientUserData());
    return "OK";
  }

}
