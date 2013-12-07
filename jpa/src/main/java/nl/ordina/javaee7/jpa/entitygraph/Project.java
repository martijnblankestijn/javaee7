package nl.ordina.javaee7.jpa.entitygraph;

import javax.persistence.*;

/**
 *
 */
@Entity
@Inheritance
public class Project {
  @Id
  @GeneratedValue
  protected long id;

  String name;

  @OneToOne(fetch = FetchType.EAGER)
  protected Requirements doc;
}
