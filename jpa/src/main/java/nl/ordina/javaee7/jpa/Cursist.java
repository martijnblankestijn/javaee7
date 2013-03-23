package nl.ordina.javaee7.jpa;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 */
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
