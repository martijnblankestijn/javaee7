package nl.ordina.javaee7.jpa;

import javax.persistence.*;

/**
 *
 */
@NamedEntityGraphs({
        @NamedEntityGraph(name = "beperkt", attributeNodes = {@NamedAttributeNode(value = "landVanHerkomst")}),
        @NamedEntityGraph(name = "volledig", includeAllAttributes = true)
})

@NamedStoredProcedureQuery(name="plus", procedureName="APP.PLUS")
//        parameters = {@StoredProcedureParameter(name = "", type = , )})
@Entity
public class Cursist {
  @Id @GeneratedValue
  private Long id;

  private Geslacht geslacht;

  @Convert(converter = LandConverter.class)
  private Land landVanHerkomst;

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
