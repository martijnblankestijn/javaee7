package nl.ordina.javaee7.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 */
@Entity
public class Cursus {
  @Id private long id;

  private String code;

}
