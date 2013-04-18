package nl.ordina.javaee7.cdi;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.transaction.TransactionScoped;
import java.io.Serializable;

/**
 *
 */
@TransactionScoped
public class TransactionContextObject implements Serializable {
  public TransactionContextObject() {
    System.out.println("ctor TransactionContextObject");
  }

  @PostConstruct
  public void init() {
    System.out.println("postConstruct TransactionContextObject");
  }

  @PreDestroy
  public void destroy() {
    System.out.println("preDestroy TransactionContextObject");
  }

  public void log(String message) {
    System.out.println("Message received: " + message);
  }
}
