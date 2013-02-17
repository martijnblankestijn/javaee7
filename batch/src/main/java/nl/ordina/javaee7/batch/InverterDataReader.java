package nl.ordina.javaee7.batch;

import javax.batch.annotation.*;
import java.io.Externalizable;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static java.util.logging.Level.*;


/**
 *
 */
@ItemReader(value = "InverterDataReader")
public class InverterDataReader {
    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private AtomicInteger checkpoint;
    private Scanner scanner;

    @BatchProperty
    private String inverterCsvDirectory;

    @Open
    public void open(ExternalizableAtomicInteger checkpoint) throws Exception {
        if (inverterCsvDirectory == null || inverterCsvDirectory.isEmpty() || inverterCsvDirectory.equals("null")) {
            throw new IllegalStateException("Directory for the comma-separated-value files is required. ["
                    + inverterCsvDirectory + "] is not valid.");
        }

        Path path = Paths.get(inverterCsvDirectory);
        if (Files.notExists(path)) {
            throw new IllegalStateException("Path " + path + " does not exist!.");
        }
        Path file = path.resolve("JAVA_inverter_1204DT0005_20130216.csv");
        this.checkpoint = (checkpoint == null) ? new AtomicInteger(0) : checkpoint.getInteger();

        LOG.log(FINE, "Opening reader with path {0} for checkpoint {1}", new Object[]{file, this.checkpoint});

        scanner = new Scanner(file);

    }

    @Close
    public void close() throws Exception {
        LOG.log(FINE, "Closing reader with checkpoint {0}", new Object[]{this.checkpoint});
        scanner.close();

    }

    @ReadItem
    public String readItem() throws Exception {
        if(isHeaderline()) {
            LOG.log(FINER, "Headerline : {0} (will be ignored)", scanner.nextLine());
        }

        return determineOutput();
    }

    private String determineOutput() {
        if (scanner.hasNextLine()) {
            return readLine();
        } else {
            LOG.log(FINER, "Read all items, returning null to indicate end of file");
            return null;
        }
    }

    private String readLine() {
        String item = scanner.nextLine();
        checkpoint.getAndIncrement();
        if (LOG.isLoggable(FINE)) {
            LOG.log(FINEST, "Read item {0} for checkpoint {1}", new Object[]{item, checkpoint});
        }
        return item;
    }

    private boolean isHeaderline() {
        // this example: there is 1 header
        return checkpoint.get() == 0;
    }

    @CheckpointInfo
    public Externalizable checkpointInfo() throws Exception {
        LOG.log(FINER, "Checkpoint info {0} ", checkpoint);
        return new ExternalizableAtomicInteger(checkpoint);
    }
}
