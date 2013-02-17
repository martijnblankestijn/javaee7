package nl.ordina.javaee7.batch;

import nl.mb.inverter.entity.InverterData;

import javax.batch.annotation.Close;
import javax.batch.annotation.ItemWriter;
import javax.batch.annotation.Open;
import javax.batch.annotation.WriteItems;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.*;


/**
 *
 */
@ItemWriter(value = "InverterDataWriter")
public class InverterDataWriter {
    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private EntityManager entityManager;
    private EntityManagerFactory inverterPU;


    @Open
    public void open(ExternalizableAtomicInteger checkpoint) throws Exception {
        inverterPU = Persistence.createEntityManagerFactory("inverterPU");
        entityManager = inverterPU.createEntityManager();
        LOG.log(INFO, "Opened EntityManager");
    }

    @Close
    public void close() throws Exception {
        if (entityManager != null) {
            LOG.log(FINE, "Closing entity manager");
            entityManager.close();
        }
        if (inverterPU != null) {
            LOG.log(FINE, "Closing entity manager factory");
            inverterPU.close();
        }
        LOG.log(INFO, "Closed EntityManager en EntityManagerFactory");
    }


    @WriteItems
    public void writeIt(List<InverterData> lijst) throws Exception {
        LOG.log(FINER, "Writing list with " + lijst.size() + " entries to the database");
        entityManager.getTransaction().begin();

        for (InverterData data : lijst) {
            entityManager.persist(data);
        }

        entityManager.getTransaction().commit();
        LOG.log(FINER, "Database committed");
    }
}
