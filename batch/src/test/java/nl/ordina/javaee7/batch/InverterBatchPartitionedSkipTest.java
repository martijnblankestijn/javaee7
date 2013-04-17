package nl.ordina.javaee7.batch;


import org.junit.Test;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
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

    String directory = InverterBatchPartitionedSkipTest.class.getResource("/input/invalidrecords").getFile();
    Properties jobParameters = new Properties();
    jobParameters.put("inverter-csv-directory", directory);

    JobOperator jobOp = BatchRuntime.getJobOperator();

    Long execID = jobOp.start("inverter-batch-partitioned", jobParameters);

    JobExecution execution = jobOp.getJobExecution(execID);

    sleep(12);
    assertEquals(aantal + AANTAL_GELDIGE_RECORDS, queryAantalRecords(connection));
    assertEquals("COMPLETED", execution.getExitStatus());
  }

  private long queryAantalRecords(final Connection connection) throws SQLException {
    ResultSet resultSet = connection.createStatement().executeQuery("select count(*) from INVERTERDATA");
    resultSet.next();
    return resultSet.getLong(1);
  }
}
