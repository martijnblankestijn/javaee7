package nl.ordina.javaee7.ejb;

import nl.ordina.javaee7.jpa.Cursist;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
}
