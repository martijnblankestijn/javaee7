package nl.ordina.javaee7.fetchplan;

import org.junit.Test;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;

import static java.util.Collections.singletonMap;
import static javax.persistence.Persistence.createEntityManagerFactory;

public class FetchPlanTest {
    @Test
    public void testFetchPlan() {
        EntityManagerFactory emf = createEntityManagerFactory("cursus");
        EntityManager em = emf.createEntityManager();

        Set<Sessie> sessies = new HashSet<Sessie>();
        sessies.add(new Sessie(new Date(104, 2,3)));
        sessies.add(new Sessie(new Date(104, 5, 9)));
        Cursus cursus = new Cursus("Java EE 7", "Bla", 3);
        Cursusuitvoering uitvoering = new Cursusuitvoering(cursus, sessies);

        em.getTransaction().begin();
        em.persist(cursus);
        em.persist(uitvoering);
        em.getTransaction().commit();


        em.clear();
        emf.getCache().evictAll();

        listAll(em);
        em.close();
        emf.close();
    }

    private void listAll(EntityManager em) {
        System.out.println("******* Retrieval *********");
        final EntityGraph<?> cursusIncSessies = em.getEntityGraph("CursusIncSessies");
        final List<Cursus> list = em.createQuery("select s from Cursus s", Cursus.class)
            .setHint("javax.persistence.fetchgraph", cursusIncSessies)
            .getResultList();
        long id = list.get(0).getId();
        for (Cursus cursus : list) {
            System.out.println("++++++++ Cursus: " + cursus);
            System.out.println("Aantal uitvoeringen: " + cursus.getUitvoeringen().size());
            for (Cursusuitvoering uitvoering : cursus.getUitvoeringen()) {
                System.out.println("  " + uitvoering + " ");
            }
        }
        System.out.println("******* Find *********");
        em.clear();
        final Map<String, Object> hints = singletonMap("javax.persistence.fetchgraph", (Object) cursusIncSessies);
        final Cursus cursus = em.find(Cursus.class, id, hints);
        System.out.println("====== " + cursus);
    }
}
