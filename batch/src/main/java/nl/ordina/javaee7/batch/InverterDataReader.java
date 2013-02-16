package nl.ordina.javaee7.batch;

import javax.batch.annotation.*;
import java.io.Externalizable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;


/**
 *
 */
@ItemReader(value = "InverterDataReader")
public class InverterDataReader {
    private String[] items = {"a", "b", "c"};

    private AtomicInteger checkpoint;
    private Scanner scanner;

    @Open
    public void open(ExternalizableAtomicInteger checkpoint) throws Exception {
        System.out.println("Open for checkpoint " + checkpoint);
        this.checkpoint = (checkpoint==null) ? new AtomicInteger(0) : checkpoint.getInteger();

        Path path = Paths.get("src", "test", "resources", "input", "inverter.csv");
        scanner = new Scanner(path);

    }

    @Close
    public void close() throws Exception {
        System.out.println("Close");
        scanner.close();

    }

    @ReadItem
    public String readItem() throws Exception {
        if(scanner.hasNextLine()) {
            String item = scanner.nextLine();
            System.out.println("Read: " + item);
            checkpoint.getAndIncrement();
            return item;
        }
        return null;
    }

    @CheckpointInfo
    public Externalizable checkpointInfo() throws Exception {
        System.out.println("Checkpoint info: " + checkpoint);
        return new ExternalizableAtomicInteger(checkpoint);
    }
}
