package nl.ordina.javaee7.tobeignored;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 */
@Singleton
public class Ignored {
  @Inject private String value;

  public Ignored() {
    System.out.println("Constructor");
  }

  @PostConstruct
  public void postCOnstruct() {
    System.out.println("CONSTRUCTED");
  }
}
