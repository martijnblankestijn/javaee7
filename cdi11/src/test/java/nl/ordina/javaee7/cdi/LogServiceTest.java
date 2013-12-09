package nl.ordina.javaee7.cdi;


import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Test;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

import static org.junit.Assert.assertTrue;

public class LogServiceTest {
    @Test
    public void test() {
        // throws Singleton is not set. Is your Thread.currentThread().getContextClassLoader() set correctly?
//        final CDI<Object> beanContainer = CDI.current();
        final WeldContainer container = new Weld().initialize();
        final Instance<Object> beanManager = container.instance();
        assertTrue(beanManager.select(LogService.class).isUnsatisfied());
    }

}
