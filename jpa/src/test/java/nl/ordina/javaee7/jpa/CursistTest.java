package nl.ordina.javaee7.jpa;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaDelete;
import java.util.List;

/**
 *
 */
public class CursistTest {

  private EntityManager em;

  @Before
  public void before() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("cursus");
    em = emf.createEntityManager();
  }

  @Test
  public void test() {
    Cursist cursist = new Cursist(Geslacht.MAN);
    cursist.setLandVanHerkomst(new Land("NL", "SOmething"));

    em.getTransaction().begin();

    em.persist(cursist);

    em.getTransaction().commit();

    // compleet schone lei
    em.clear();
    em.getEntityManagerFactory().getCache().evictAll();

    List<Cursist> lijst = em.createQuery("select c from Cursist c", Cursist.class).getResultList();
    for (Cursist c: lijst) {
      System.out.println(c);
    }

    em.getTransaction().begin();

CriteriaDelete<Cursist> criteriaUpdate =
        em.getCriteriaBuilder()
          .createCriteriaDelete(Cursist.class);

int aantalGeupdate =
         em.createQuery(criteriaUpdate).executeUpdate();

System.out.println("Aantal :" + aantalGeupdate);

    em.getTransaction().commit();
  }


  }