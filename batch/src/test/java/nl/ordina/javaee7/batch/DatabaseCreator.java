package nl.ordina.javaee7.batch;

import com.ibm.jbatch.spi.services.IBatchConfig;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 *
 */
public class DatabaseCreator {
  public static void createDatabases() {
    try {
      createBatchDatabase();
      createInverterDatabase();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  private static void createBatchDatabase() throws IOException, ClassNotFoundException, URISyntaxException, SQLException {

    URI ddlUri = IBatchConfig.class.getResource("/jsr352-derby.ddl").toURI();

    createDatabase(ddlUri, getBatchDatabaseConnection());
  }

  public static Connection getBatchDatabaseConnection() throws IOException, ClassNotFoundException, SQLException {
    Properties properties = new Properties();
    properties.load(BatchMain.class.getResourceAsStream("/META-INF/services/batch-config.properties"));
    Class.forName(properties.getProperty("JDBC_DRIVER"));
    String url = properties.getProperty("JDBC_URL");
    String db_user = properties.getProperty("DB_USER");
    String db_password = properties.getProperty("DB_PASSWORD");
    return DriverManager.getConnection(url, db_user, db_password);
  }

  private static void createInverterDatabase() throws IOException, ClassNotFoundException, URISyntaxException, SQLException {

    URI ddlUri = BatchMain.class.getResource("/inverter-data-db.ddl").toURI();

    createDatabase(ddlUri, getInverterDatabaseConnection());
  }

  public static Connection getInverterDatabaseConnection() {
    // properties gelijk aan persistence.xml

    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      String url = "jdbc:derby:target/INVERTERDB;create=true";
      String db_user = "APP";
      String db_password = "SECRET";
      return DriverManager.getConnection(url, db_user, db_password);
    } catch (ClassNotFoundException | SQLException e) {
      throw new IllegalStateException(e);
    }
  }


  private static void createDatabase(URI ddlUri, Connection connection) throws SQLException, IOException {


    Path ddlPath = Paths.get(ddlUri);
    String[] statements = readContent(ddlPath).split(";");
    try {
      for (String statement : statements) {
        Statement sqlStatement = connection.createStatement();
        boolean execute = sqlStatement.execute(statement);
        sqlStatement.close();
      }
    } catch (SQLException e) {
      System.out.println("Exception, probably already exists, continue");
    } finally {
      connection.close();
    }
  }

  private static String readContent(Path path) throws IOException {
    List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
    final StringBuilder builder = new StringBuilder();
    for (String line : lines) {
      builder.append(line);
    }
    return builder.toString();
  }

}
