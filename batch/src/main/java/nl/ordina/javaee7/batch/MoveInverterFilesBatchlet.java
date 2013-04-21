package nl.ordina.javaee7.batch;

import javax.batch.api.BatchProperty;
import javax.batch.api.Batchlet;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class MoveInverterFilesBatchlet implements Batchlet {
  private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @Inject JobContext jobContext;
  @BatchProperty String importedDirectory;

  @Override public String process() throws Exception {
    Object transientUserData = jobContext.getTransientUserData();
    if(transientUserData == null || !(transientUserData instanceof List)) {
      throw new IllegalStateException("Lijst met bestanden verwacht als transient user data");
    }

    Path importedDirectory = Paths.get(this.importedDirectory);
    if(!Files.exists(importedDirectory) && !Files.isDirectory(importedDirectory)) {
      throw new IllegalStateException("Imported directory does not exist");
    }
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    String subDirectory = format.format(new Date()) + jobContext.getJobName() + "_" + jobContext.getExecutionId();
    Path targetDirectory = importedDirectory.resolve(subDirectory);

    if(Files.exists(targetDirectory)) {
      throw new IllegalStateException("Directory " + targetDirectory + " already exists, should no be possible!!");
    }
    Files.createDirectory(targetDirectory);
    LOG.log(Level.FINE, "Moving all finished files to target directory {0}", targetDirectory);
    List<String> paths = (List<String>) transientUserData;
    for (String pathString : paths) {
      Path path = Paths.get(pathString);
      LOG.log(Level.FINE, "Moving {0} to {1}", new Object[] {path, targetDirectory});
      Files.move(path, targetDirectory.resolve(path.getFileName()), StandardCopyOption.ATOMIC_MOVE);
    }
    return "OK";
  }

  @Override public void stop() throws Exception {
    // nothing to do
  }
}
