package nl.ordina.javaee7.batch;

//import com.ibm.batch.container.config.IBatchConfig;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import java.util.Properties;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class BatchMain extends AbstractBatchTest {
  private static final Logger LOG = Logger.getLogger(BatchMain.class.getSimpleName());


//  @Test
  public void test() throws Exception {
    LOG.fine("Start up");

    Properties jobParameters = new Properties();
    jobParameters.put("inverter-csv-directory", "/home/martijn/workspaces/zonnepanelen/testdata");

    JobOperator jobOp = BatchRuntime.getJobOperator();

    Long execID = jobOp.start("inverter-batch", jobParameters);

    JobExecution execution = jobOp.getJobExecution(execID);

    printJobExecution(execution);

    sleep(600);
    assertEquals("COMPLETED", execution.getExitStatus());
  }

}
