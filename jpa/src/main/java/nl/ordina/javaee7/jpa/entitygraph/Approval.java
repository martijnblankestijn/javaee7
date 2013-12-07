package nl.ordina.javaee7.jpa.entitygraph;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 */
@Entity
public class Approval {
  @Id @GeneratedValue
  private Long id;
}
