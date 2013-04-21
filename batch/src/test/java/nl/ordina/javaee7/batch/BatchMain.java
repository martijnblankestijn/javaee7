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
public class BatchMain{
  private static final Logger LOG = Logger.getLogger(BatchMain.class.getSimpleName());

  public static void main(String[] args) throws Exception {
    LOG.fine("Start up");

    Properties jobParameters = new Properties();
    jobParameters.put("inverter-csv-directory", "/home/martijn/workspaces/zonnepanelen/testdata/import/input");
    jobParameters.put("imported-directory", "/home/martijn/workspaces/zonnepanelen/testdata/import/imported");


    JobOperator jobOp = BatchRuntime.getJobOperator();

    Long execID = jobOp.start("inverter-batch-partioned", jobParameters);

    JobExecution execution = jobOp.getJobExecution(execID);

    Thread.sleep(60000);
    assertEquals("COMPLETED", execution.getExitStatus());
  }

}
