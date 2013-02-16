package nl.ordina.javaee7.batch;

import com.ibm.batch.container.config.IBatchConfig;
import org.junit.Before;
import org.junit.Test;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 */
public class BatchTest {
    private static final Logger LOG = Logger.getLogger(BatchTest.class.getSimpleName());

    @Before
    public void init() throws Exception {
        createDatabase();
    }


    @Test
    public void test() throws Exception {
        LOG.fine("Start up");

        JobOperator jobOp = BatchRuntime.getJobOperator();
        Properties jobParameters = new Properties();
        jobParameters.put("inverter-csv-directory", "/home/martijn/workspaces/zonnepanelen/solar-data");

        URL jobXMLURL = BatchTest.class.getResource("/inverter-batch.xml");
        Path path = Paths.get(jobXMLURL.toURI());
        String jobXml = readContent(path);
        Long execID = jobOp.start(jobXml, jobParameters);

        JobExecution execution = jobOp.getJobExecution(execID);

        printJobExecution(execution);

        long jobInstanceId = execution.getInstanceId();
        long lastExecutionId = execution.getExecutionId();

        Thread.sleep(1000);

        printJobExecution(execution);

        Thread.sleep(2000);

        printJobExecution(execution);
        System.exit(0);
    }

    private static void printJobExecution(JobExecution execution) {
        System.out.println("ExitStatus: " + execution.getExitStatus());
        System.out.println("Status: " + execution.getStatus());
    }

    private static void createDatabase() throws IOException, ClassNotFoundException, URISyntaxException, SQLException {
        Properties properties = new Properties();
        properties.load(BatchTest.class.getResourceAsStream("/META-INF/services/batch-config.properties"));
        Class.forName(properties.getProperty("JDBC_DRIVER"));
        String url = properties.getProperty("JDBC_URL");
        String db_user = properties.getProperty("DB_USER");
        String db_password = properties.getProperty("DB_PASSWORD");
        Connection connection = DriverManager.getConnection(url, db_user, db_password);


        Path ddlPath = Paths.get(IBatchConfig.class.getResource("/jsr352-derby.ddl").toURI());
        String[] statements = readContent(ddlPath).split(";");
        try {
            for (String statement : statements) {
                boolean execute = connection.createStatement().execute(statement);
            }
        } catch (SQLException e) {
            System.out.println("Exception, probabpy already exists, continue");
        }
    }

    private static String readContent(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
        final StringBuilder jobXmlBuilder = new StringBuilder();
        for (String line : lines) {
            jobXmlBuilder.append(line);
        }
        String jobXml = jobXmlBuilder.toString();
        System.out.println("Inhoud: " + jobXml);
        return jobXml;
    }
}
