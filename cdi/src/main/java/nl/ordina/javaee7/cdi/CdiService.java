package nl.ordina.javaee7.cdi;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

/**
 *
 */
public class CdiService {
    public void doIt() {
        CDI<Object> cdi = CDI.current();
        BeanManager beanManager = cdi.getBeanManager();
    }
}
