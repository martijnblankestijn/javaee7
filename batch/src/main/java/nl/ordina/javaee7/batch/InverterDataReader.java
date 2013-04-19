package nl.ordina.javaee7.batch;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemReader;
import java.io.Externalizable;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.FINEST;


/**
 *
 */
public class InverterDataReader implements ItemReader {
  private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  private AtomicInteger checkpoint;
  private Scanner scanner;

  @BatchProperty String inverterCsvFile;


  @Override public void open(Serializable checkpoint) throws Exception {

    LOG.log(FINE, "Path inverter csv file: {0}", inverterCsvFile);
    Path path = Paths.get(inverterCsvFile);
    if (Files.notExists(path)) {
      throw new IllegalStateException("Path " + path + " does not exist!.");
    }
    this.checkpoint = (checkpoint == null) ? new AtomicInteger(0) : ((ExternalizableAtomicInteger) checkpoint).getInteger();

    LOG.log(FINEST, "Opening reader with path {0} for checkpoint {1}", new Object[]{path, this.checkpoint});

    scanner = new Scanner(path);

  }

  @Override public void close() throws Exception {
    LOG.log(FINEST, "Closing reader with checkpoint {0}", new Object[]{this.checkpoint});
    scanner.close();

  }

  @Override public String readItem() throws Exception {
    if (isHeaderline()) {
      LOG.log(FINEST, "Headerline : {0} (will be ignored)", scanner.nextLine());
    }
    return determineOutput();
  }

  @Override public Externalizable checkpointInfo() throws Exception {
    LOG.log(FINEST, "Checkpoint info {0} ", checkpoint);
    return new ExternalizableAtomicInteger(checkpoint);
  }

  private String determineOutput() {
    if (scanner.hasNextLine()) {
      return readLine();
    } else {
      LOG.log(FINEST, "Read all items, returning null to indicate end of file");
      return null;
    }
  }

  private String readLine() {
    String item = scanner.nextLine();
    checkpoint.getAndIncrement();
    if (LOG.isLoggable(FINEST)) {
      LOG.log(FINEST, "Read item {0} for checkpoint {1}", new Object[]{item, checkpoint});
    }
    return item;
  }

  private boolean isHeaderline() {
    // this example: there is 1 header
    return checkpoint.get() == 0;
  }

}
