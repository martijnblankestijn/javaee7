package nl.ordina.javaee7.batch;

import nl.mb.inverter.entity.InverterData;

import javax.batch.api.chunk.ItemWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.*;


/**
 *
 */
public class InverterDataWriter implements ItemWriter {
  private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
  private EntityManager entityManager;
  private EntityManagerFactory inverterPU;


  @Override
  public void open(Serializable checkpoint) throws Exception {
    inverterPU = Persistence.createEntityManagerFactory("inverterPU");
    entityManager = inverterPU.createEntityManager();
    LOG.log(FINEST, "Opened EntityManager");
  }

  @Override
  public void close() throws Exception {
    if (entityManager != null) {
      LOG.log(FINEST, "Closing entity manager");
      entityManager.close();
    }
    if (inverterPU != null) {
      LOG.log(FINEST, "Closing entity manager factory");
      inverterPU.close();
    }
    LOG.log(FINEST, "Closed EntityManager en EntityManagerFactory");
  }


  @Override
  public void writeItems(List<Object> lijst) throws Exception {
    LOG.log(FINEST, "Writing list with " + lijst.size() + " entries to the database");
    entityManager.getTransaction().begin();

    for (Object obj : lijst) {
      InverterData data = (InverterData) obj;
      entityManager.persist(data);
    }

    entityManager.getTransaction().commit();
    LOG.log(FINEST, "Database committed");
  }

  @Override public Serializable checkpointInfo() throws Exception {
    LOG.log(FINEST, "Checkpointinfo");
    return null;
  }
}
