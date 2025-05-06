import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/";
    private static final String dbName = "bill_system";
    private static final String user = "pknatic";
    private static final String password = "Pk@2025";

    public static void connect() {
        try {
            conn = DriverManager.getConnection(jdbcUrl, user, password);
            stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            System.out.println("Database checked/created: " + dbName);

            conn = DriverManager.getConnection(jdbcUrl + dbName, user, password);
            stmt = conn.createStatement();

            String createTableQuery = "CREATE TABLE IF NOT EXISTS users (\n" +
                    "    id INT PRIMARY KEY NOT NULL UNIQUE CHECK (id >= 10000000 AND id <= 99999999),\n" +
                    "    username VARCHAR(20) NOT NULL UNIQUE,\n" +
                    "    name VARCHAR(30) NOT NULL,\n" +
                    "    password VARCHAR(30) NOT NULL,\n" +
                    "    usertype VARCHAR(20) NOT NULL\n" +
                    ");";

            stmt.executeUpdate(createTableQuery);
            System.out.println("Table ''users' ");

            String createCustomerTable = "CREATE TABLE IF NOT EXISTS customer (\n" +
                    "    account_id CHAR(8) PRIMARY KEY,\n" +
                    "    name VARCHAR(100) NOT NULL,\n" +
                    "    address VARCHAR(255) NOT NULL,\n" +
                    "    city VARCHAR(100) NOT NULL,\n" +
                    "    pin_code CHAR(6) NOT NULL,\n" +
                    "    mobile_number CHAR(10) NOT NULL,\n" +
                    "    connection_type VARCHAR(20) NOT NULL,\n " +
                    "    state VARCHAR(100) NOT NULL,\n" +
                    "    id_proof_type VARCHAR(50) NOT NULL,\n" +
                    "    id_proof_number VARCHAR(50) NOT NULL,\n" +
                    "    meter_number CHAR(12) UNIQUE NOT NULL,\n" +
                    "    date_of_issue DATE\n" +
                    ");\n";

            stmt.executeUpdate(createCustomerTable);
            System.out.println("Table 'customer' created successfully!");
            String createTransactionTable = "CREATE TABLE IF NOT EXISTS transaction ("
                    + "account_id VARCHAR(8) , "
                    + "date_of_transaction DATE, "
                    + "unit FLOAT(6,2),"
                    + "rate_per_unit FLOAT(4,2),"
                    + "amount FLOAT(8,2),"
                    + "bill_number VARCHAR(9),"
                    + "payment_status VARCHAR(3) "
                    + ")";

            stmt.executeUpdate(createTransactionTable);
            System.out.println("Table 'transaction' created successfully!");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return conn;
    }

    public static Statement getStatement() {
        return stmt;
    }
}
