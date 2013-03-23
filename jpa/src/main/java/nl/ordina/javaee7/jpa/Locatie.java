package nl.ordina.javaee7.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 */
@Entity
public class Locatie {
  @Id @GeneratedValue
  private long id;

  private String straatnaam;
  private int huisnummer;
}
