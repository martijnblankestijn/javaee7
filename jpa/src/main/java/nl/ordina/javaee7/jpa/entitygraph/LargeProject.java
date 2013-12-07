package nl.ordina.javaee7.jpa.entitygraph;

/**
 *
 */

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class LargeProject extends Project{
  @OneToOne(fetch= FetchType.LAZY)
  protected Employee approver;
}
