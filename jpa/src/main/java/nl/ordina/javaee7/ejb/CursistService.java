package nl.ordina.javaee7.ejb;

import nl.ordina.javaee7.jpa.Cursist;
import nl.ordina.javaee7.jpa.Land;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

import static javax.persistence.ParameterMode.IN;
import static javax.persistence.ParameterMode.OUT;

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
    EntityGraph<?> entityGraph = em.getEntityGraph("volledig");
    System.out.println("Entity Graph: " + entityGraph);
    return em.createQuery("select c from Cursist c where c.landVanHerkomst = :land", Cursist.class)
            .setParameter("land", land)
            .setHint("javax.persistence.loadgraph", "volledig")
            .getResultList();
  }

  /**
   * Deze werkt niet, met foutmelding
   org.apache.derby.client.am.SqlException: Column name 'EERSTE' appears in a statement without a FROM list.   * @return
   */
public int callStoredProcedure2() {
  StoredProcedureQuery storedProcedureQuery = em
          .createNamedStoredProcedureQuery("plus")
          .setParameter("EERSTE", 5)
          .setParameter("TWEEDE", 8);
  boolean resultaat = storedProcedureQuery.execute();

  return (Integer) storedProcedureQuery.getOutputParameterValue("RESULTAAT");
}

  public int callStoredProcedure() {
    StoredProcedureQuery storedProcedureQuery = em
            .createStoredProcedureQuery("JAVAEE7.PLUS")
            .registerStoredProcedureParameter(1, Integer.class, IN)
            .registerStoredProcedureParameter(2, Integer.class, IN)
            .registerStoredProcedureParameter(3, Integer.class, OUT)
            .setParameter(1, 5)
            .setParameter(2, 8);
    boolean resultaat = storedProcedureQuery.execute();
    if(!resultaat) {
      System.out.println("Geen output om te lezen.");
    }
    // Stored procedure kan bij execute() == false een null List opleveren!
    System.out.println("Resultaat: " + storedProcedureQuery.getResultList());

    // na de getResultList of de getUpdateCount kunnen de INOUT en OUT parameters worden
    // geraadpleegd
    Integer value = (Integer) storedProcedureQuery.getOutputParameterValue(3);
    System.out.println("Output: " + value);

    return value;

  }
}
