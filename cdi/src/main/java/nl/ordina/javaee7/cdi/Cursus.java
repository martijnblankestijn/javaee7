package nl.ordina.javaee7.cdi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 */
@Entity
public class Cursus {
  @Id @GeneratedValue private long id;
  private String titel;

  public String getTitel() {
    return titel;
  }

  public void setTitel(final String titel) {
    this.titel = titel;
  }
}
