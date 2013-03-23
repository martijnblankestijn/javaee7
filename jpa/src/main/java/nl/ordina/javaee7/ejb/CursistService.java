package nl.ordina.javaee7.ejb;

import nl.ordina.javaee7.jpa.Cursist;
import nl.ordina.javaee7.jpa.Land;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

/**
 *
 */
@Stateless
public class CursistService {
  @PersistenceContext
  EntityManager em;

  public void opslaan(Cursist cursist) {
    em.persist(cursist);
  }

  public List<Cursist> getAll() {
    return em.createQuery("select c from Cursist c", Cursist.class).getResultList();
  }

  public List<Cursist> getLand(Land land) {
    EntityGraph<Object> entityGraph = em.getEntityGraph("volledig");
    System.out.println("Entity Graph: " + entityGraph);
    return em.createQuery("select c from Cursist c where c.landVanHerkomst = :land", Cursist.class)
            .setParameter("land", land)
            .setHint("javax.persistence.loadgraph", "volledig")
            .getResultList();
  }

  public int callStoredProcedure() {
    StoredProcedureQuery storedProcedureQuery = em
            .createStoredProcedureQuery("JAVAEE7.PLUS")
            .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT)
            .setParameter(1, 5)
            .setParameter(2, 8);
    boolean resultaat = storedProcedureQuery.execute();

    System.out.println("Resultaat: " + resultaat);

    Integer value = (Integer) storedProcedureQuery.getOutputParameterValue(3);
    System.out.println("Output: " + value);

    return value;

  }
}
