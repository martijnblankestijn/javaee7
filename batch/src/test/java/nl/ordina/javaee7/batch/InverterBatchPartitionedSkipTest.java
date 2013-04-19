package nl.ordina.javaee7.batch;


import org.junit.Test;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class InverterBatchPartitionedSkipTest extends AbstractBatchTest {

  private static final int AANTAL_GELDIGE_RECORDS = 14;

  @Test
  public void testOngeldigeRecordsOvergeslagen() throws SQLException {
    Connection connection = DatabaseCreator.getInverterDatabaseConnection();
    long aantal = queryAantalRecords(connection);

    Properties jobParameters = createJobParametersWithDirectory("/input/invalidrecords");

    JobExecution execution = startAndGetJobExecution(jobParameters);

    sleep(12);
    assertEquals(aantal + AANTAL_GELDIGE_RECORDS, queryAantalRecords(connection));
    assertEquals("FILES_PROCESSED", execution.getExitStatus());
    assertEquals(BatchStatus.COMPLETED, execution.getBatchStatus());
  }

  @Test
  public void testGeenBestandenInDirectory() throws SQLException {
    Properties jobParameters = createJobParametersWithDirectory("/input/no_files");

    JobExecution execution = startAndGetJobExecution(jobParameters);

    sleep(12);
    assertEquals("NO_FILES", execution.getExitStatus());
    assertEquals(BatchStatus.COMPLETED, execution.getBatchStatus());
  }



  private JobExecution startAndGetJobExecution(final Properties jobParameters) {
    JobOperator jobOp = BatchRuntime.getJobOperator();

    Long execID = jobOp.start("inverter-batch-partitioned", jobParameters);

    return jobOp.getJobExecution(execID);
  }

  private Properties createJobParametersWithDirectory(final String relativeDirectory) {
    String directory = InverterBatchPartitionedSkipTest.class.getResource(relativeDirectory).getFile();
    Properties jobParameters = new Properties();
    jobParameters.put("inverter-csv-directory", directory);
    return jobParameters;
  }


  private long queryAantalRecords(final Connection connection) throws SQLException {
    ResultSet resultSet = connection.createStatement().executeQuery("select count(*) from INVERTERDATA");
    resultSet.next();
    return resultSet.getLong(1);
  }
}
