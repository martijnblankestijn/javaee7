package nl.ordina.javaee7.batch;


import org.junit.Before;
import org.junit.Test;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import java.sql.SQLException;
import java.util.Properties;

import static nl.ordina.javaee7.batch.PutUserDataInScopeBatchlet.*;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class InverterBatchDeciderTest extends AbstractBatchTest {

  private JobOperator jobOperator;

  @Before
  public void setUp() throws Exception {
    jobOperator = BatchRuntime.getJobOperator();
  }

  @Test
  public void testNoUserData() throws SQLException {
    Properties jobParameters = createPropertyWithAction(NO_USERDATA);

    JobExecution execution = startAndGetExecution(jobParameters);

    sleep(2);
    assertEquals("INVALID_USERDATA", execution.getExitStatus());
    assertEquals(BatchStatus.FAILED, execution.getBatchStatus());
  }

  @Test
  public void testInvalidUserData() throws SQLException {
    Properties jobParameters = createPropertyWithAction(INVALID_USERDATA);

    JobExecution execution = startAndGetExecution(jobParameters);

    sleep(2);
    assertEquals("INVALID_USERDATA", execution.getExitStatus());
    assertEquals(BatchStatus.FAILED, execution.getBatchStatus());
  }

  @Test
  public void testNoFiles() throws SQLException {
    Properties jobParameters = createPropertyWithAction(NO_FILES);

    JobExecution execution = startAndGetExecution(jobParameters);

    sleep(2);
    assertEquals("NO_FILES", execution.getExitStatus());
    assertEquals(BatchStatus.COMPLETED, execution.getBatchStatus());
  }

  @Test
  public void testFilesAvailable() throws SQLException {
    Properties jobParameters = createPropertyWithAction(FILES_AVAILABLE);

    JobExecution execution = startAndGetExecution(jobParameters);

    sleep(2);
    assertEquals("FILES_AVAILABLE", execution.getExitStatus());
    assertEquals(BatchStatus.COMPLETED, execution.getBatchStatus());
  }

  private JobExecution startAndGetExecution(final Properties jobParameters) {
    Long execID = jobOperator.start("inverter-batch-decider", jobParameters);

    return jobOperator.getJobExecution(execID);
  }

  private Properties createPropertyWithAction(final String action) {
    Properties jobParameters = new Properties();
    jobParameters.put("action", action);
    return jobParameters;
  }

}
