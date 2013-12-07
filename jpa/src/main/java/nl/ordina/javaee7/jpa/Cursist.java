package nl.ordina.javaee7.jpa;

import javax.persistence.*;

import static javax.persistence.ParameterMode.IN;
import static javax.persistence.ParameterMode.OUT;

/**
 *
 */
@NamedEntityGraphs({
        @NamedEntityGraph(name = "beperkt", attributeNodes = {@NamedAttributeNode(value = "landVanHerkomst")}),
        @NamedEntityGraph(name = "volledig", includeAllAttributes = true)
})

@NamedStoredProcedureQuery(name="plus", procedureName="JAVAEE7.PLUS" ,
    parameters = {
  @StoredProcedureParameter(name = "EERSTE", type = Integer.class, mode = IN),
  @StoredProcedureParameter(name = "TWEEDE", type = Integer.class, mode = IN),
  @StoredProcedureParameter(name = "RESULT", type = Integer.class, mode = OUT)
})
@Entity
@Table(indexes = {
        @Index(name = "land", columnList = "landVanHerkomst", unique = false)
})
public class Cursist {
  @Id @GeneratedValue
  private Long id;

  private Geslacht geslacht;

  @Convert(converter = LandConverter.class)
  private Land landVanHerkomst;

  @ManyToOne
  Cursus cursus;

  protected Cursist() {
  }

  public Cursist(Geslacht geslacht) {
    this.geslacht = geslacht;
  }

  public void setLandVanHerkomst(Land landVanHerkomst) {
    this.landVanHerkomst = landVanHerkomst;
  }

  @Override
  public String toString() {
    return "Cursist{" +
            "id=" + id +
            ", geslacht=" + geslacht +
            ", landVanHerkomst=" + landVanHerkomst +
            '}';
  }
}
