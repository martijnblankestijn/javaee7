package nl.ordina.javaee7.batch;

import javax.batch.annotation.*;
import java.io.Externalizable;


/**
 *
 */
@ItemReader(value = "InverterDataReader")
public class InverterDataReader {
    private String[] items = {"a", "b", "c"};
    private int index;

    private Externalizable checkpoint;
    @Open
    public void open(Externalizable checkpoint) throws Exception {
        this.checkpoint = checkpoint;
        System.out.println("Open");
        index = 0;
    }

    @Close
    public void close() throws Exception {
        System.out.println("Close");
    }

    @ReadItem
    public String readItem() throws Exception {
        String item = index < items.length ? items[index] : null;
        System.out.println("Read: " + item);
        index++;
        return item;
    }

    @CheckpointInfo
    public Externalizable checkpointInfo() throws Exception {
        return checkpoint;
    }
}
