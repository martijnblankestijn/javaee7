package nl.ordina.javaee7.cdi;

import javax.enterprise.context.Dependent;

@Dependent
public class LogService {
    public void log(String msg) {
        System.out.println("[" + msg + "]");
    }
}
