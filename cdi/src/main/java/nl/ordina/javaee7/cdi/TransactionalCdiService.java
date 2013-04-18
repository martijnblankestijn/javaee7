package nl.ordina.javaee7.cdi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.MANDATORY;

/**
 *
 */
@Transactional(value = MANDATORY,
        rollbackOn = IllegalArgumentException.class,
        dontRollbackOn = EntityNotFoundException.class)
public class TransactionalCdiService {
  @PersistenceContext
  EntityManager em;

  @Inject TransactionContextObject transactionContextObject;

  public void save(Object obj) {
    System.out.println("Before persist");
    em.persist(obj);
    System.out.println("After persist");
    transactionContextObject.log("Just persisted it");
  }
}
