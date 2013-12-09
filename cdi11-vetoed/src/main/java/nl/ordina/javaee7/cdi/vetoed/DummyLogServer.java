package nl.ordina.javaee7.cdi.vetoed;

import nl.ordina.javaee7.cdi.LogService;

public class DummyLogServer implements LogService{
    @Override
    public void log(String msg) {
        throw new IllegalStateException("log not defined");
    }
}
