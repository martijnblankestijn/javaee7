package nl.ordina.javaee7.batch;

import javax.batch.annotation.*;
import java.io.Externalizable;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.FINER;
import static java.util.logging.Level.FINEST;


/**
 *
 */
@ItemReader(value = "InverterDataReader")
public class InverterDataReader {
    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private AtomicInteger checkpoint;
    private Scanner scanner;

    @Open
    public void open(ExternalizableAtomicInteger checkpoint) throws Exception {
        Path path = Paths.get("src", "test", "resources", "input", "inverter.csv");
        this.checkpoint = (checkpoint == null) ? new AtomicInteger(0) : checkpoint.getInteger();

        LOG.log(FINE, "Opening reader with path {0} for checkpoint {1}", new Object[]{path, this.checkpoint});

        scanner = new Scanner(path);

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
