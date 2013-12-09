package nl.ordina.javaee7.cdi.classveto;

import nl.ordina.javaee7.cdi.LogService;

import javax.enterprise.inject.Vetoed;

@Vetoed
public class DumbLogService implements LogService {
    @Override
    public void log(String msg) {
        throw new IllegalStateException("log niet ondersteund");
    }
}
