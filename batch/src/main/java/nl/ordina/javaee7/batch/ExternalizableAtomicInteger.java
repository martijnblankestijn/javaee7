package nl.ordina.javaee7.batch;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class ExternalizableAtomicInteger implements Externalizable {
    private AtomicInteger integer;

    public ExternalizableAtomicInteger(AtomicInteger integer) {
        this.integer = integer;
    }

    public AtomicInteger getInteger() {
        return integer;
    }


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(integer);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        integer = (AtomicInteger) in.readObject();
    }
}
