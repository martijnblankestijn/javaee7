package nl.ordina.javaee7.jpa.entitygraph;

/**
 *
 */

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedEntityGraph;

@NamedEntityGraph
@Entity
public class PhoneNumber {
  @Id
  protected String number;
  protected PhoneTypeEnum type;
}