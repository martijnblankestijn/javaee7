package nl.ordina.javaee7.batch;

import org.junit.Before;

import javax.batch.runtime.JobExecution;

/**
 *
 */
public class AbstractBatchTest {
  protected static void printJobExecution(JobExecution execution) {
    System.out.println("ExitStatus: " + execution.getExitStatus());
  }

  @Before
  public void init() throws Exception {
    DatabaseCreator.createDatabases();
  }

  protected void sleep(int seconds) {
    System.out.println("SLEEPING FOR " + seconds + " SECONDS");
    try {
      Thread.sleep(seconds * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
  }
}
