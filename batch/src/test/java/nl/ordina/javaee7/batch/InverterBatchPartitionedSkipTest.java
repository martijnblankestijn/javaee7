package nl.ordina.javaee7.batch;


import org.junit.Before;
import org.junit.Test;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class InverterBatchPartitionedSkipTest extends AbstractBatchTest {

  private static final int AANTAL_GELDIGE_RECORDS = 14;

  @Before
  public void init() {
    // Let eclipselink put tables in the database
//    EntityManagerFactory emf = Persistence.createEntityManagerFactory();
    Persistence.generateSchema("inverterPU", new HashMap());
  }

  @Test
  public void testOngeldigeRecordsOvergeslagen() throws SQLException, IOException {
    Connection connection = DatabaseCreator.getInverterDatabaseConnection();
    long aantal = queryAantalRecords(connection);



    Path path = Paths.get(InverterBatchPartitionedSkipTest.class.getResource("/input/invalidrecords").getFile());
    Path target = path.getParent().resolve("test");
    for (Path fileToBeDeleted : Files.newDirectoryStream(target)) {
      if(Files.isRegularFile(fileToBeDeleted)) {
        Files.delete(fileToBeDeleted);
      }
    }

    for (Path targetPath : Files.newDirectoryStream(path)) {
      Files.copy(targetPath, target.resolve(targetPath.getFileName()));
    }

    Properties jobParameters = createJobParametersWithDirectory("/input/test");
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
    jobParameters.put("imported-directory", directory + "/imported");
    jobParameters.put("inverter-invalid-record-directory", directory + "/invalid");
    return jobParameters;
  }


  private long queryAantalRecords(final Connection connection) throws SQLException {
    ResultSet resultSet = connection.createStatement().executeQuery("select count(*) from INVERTERDATA");
    resultSet.next();
    return resultSet.getLong(1);
  }
}
