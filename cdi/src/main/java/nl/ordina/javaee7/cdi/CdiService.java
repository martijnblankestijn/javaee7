package nl.ordina.javaee7.cdi;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

/**
 *
 */
public class CdiService {
  public void doIt() {
    CDI<Object> cdi = CDI.current();
    PostcodeService service = cdi.select(PostcodeService.class).get();

    BeanManager beanManager = CDI.current().getBeanManager();
  }

  private <T> T getBean(Class<T> clazz) {
    return CDI.current().select(clazz).get();
  }

}
