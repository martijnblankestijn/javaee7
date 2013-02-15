package nl.ordina.derby;

import java.sql.*;

/**
 *
 */
public class JdcbProcedureCaller {
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/sun-appserv-samples");
        CallableStatement call = conn.prepareCall("call JAVAEE7.plus(?,?,?)");
        call.setInt(1, 5);
        call.setInt(2, 8);
        call.registerOutParameter(3, Types.INTEGER);
        boolean result = call.execute();
        int resultaat = call.getInt(3);

        System.out.println("Result: " + result);
        System.out.println("Resultaat: " + resultaat);

        call.close();
        conn.close();
    }
}
