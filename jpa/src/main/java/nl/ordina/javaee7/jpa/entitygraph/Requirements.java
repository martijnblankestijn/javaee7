package nl.ordina.javaee7.jpa.entitygraph;

import javax.persistence.*;

/**
 *
 */
@Entity
public class Requirements{
  @Id
  protected long id;
  @Lob
  protected String description;

  @OneToOne(fetch= FetchType.LAZY)
  protected Approval approval;
}
