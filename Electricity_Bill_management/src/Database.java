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
            // Create DB if not exists
            conn = DriverManager.getConnection(jdbcUrl, user, password);
            stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            System.out.println("Database checked/created: " + dbName);

            // Reconnect to the specific DB
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
            System.out.println("Table checked/created: signup");

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
