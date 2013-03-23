package nl.ordina.javaee7.jpa;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "LOKAAL",
        indexes = {
                @Index(name = "LOCATIE_IX", columnList = "LOCATIE_ID", unique = false)
        })
public class Lokaal {
  @Id @GeneratedValue
  private long id;

  @ManyToOne
  private Locatie locatie;
}
