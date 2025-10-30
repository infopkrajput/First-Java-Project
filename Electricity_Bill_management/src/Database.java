import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.File;

public class Database {
    // Path of the database file
    private static final String dbPath = "D:\\BillingApp\\Data\\bill_system.db";
    private static final String jdbcUrl = "jdbc:sqlite:" + dbPath;

    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(jdbcUrl);
             Statement stmt = conn.createStatement()) {

            // Ensure the parent directory exists
            File dbFile = new File(dbPath);
            dbFile.getParentFile().mkdirs();

            System.out.println("Connected to SQLite database at: " + dbPath);

            // USERS TABLE
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INTEGER PRIMARY KEY CHECK (id >= 10000000 AND id <= 99999999), "
                    + "username TEXT NOT NULL UNIQUE, "
                    + "name TEXT NOT NULL, "
                    + "password TEXT NOT NULL, "
                    + "usertype TEXT NOT NULL"
                    + ");";
            stmt.executeUpdate(createUsersTable);

            String checkRootUser = "SELECT COUNT(*) AS count FROM users WHERE username = 'root'";
            try (ResultSet rs = stmt.executeQuery(checkRootUser)) {
                if (rs.next() && rs.getInt("count") == 0) {
                    String createRootUser = "INSERT INTO users (id, username, name, password, usertype) " +
                            "VALUES (11111111, 'root', 'Root', 'Root@2025', 'Root')";
                    stmt.executeUpdate(createRootUser);
                    System.out.println("Root user created.");
                } else {
                    System.out.println("Root user already exists.");
                }
            }

            System.out.println("Table 'users' created/verified.");

            // CUSTOMER TABLE
            String createCustomerTable = "CREATE TABLE IF NOT EXISTS customer ("
                    + "account_id TEXT PRIMARY KEY, "
                    + "name TEXT NOT NULL, "
                    + "address TEXT NOT NULL, "
                    + "city TEXT NOT NULL, "
                    + "pin_code TEXT NOT NULL, "
                    + "mobile_number TEXT NOT NULL, "
                    + "connection_type TEXT NOT NULL, "
                    + "state TEXT NOT NULL, "
                    + "id_proof_type TEXT NOT NULL, "
                    + "id_proof_number TEXT NOT NULL, "
                    + "meter_number TEXT UNIQUE NOT NULL, "
                    + "date_of_issue TEXT"
                    + ");";
            stmt.executeUpdate(createCustomerTable);
            System.out.println("Table 'customer' created/verified.");

            // TRANSACTIONS TABLE
            String createTransactionsTable = "CREATE TABLE IF NOT EXISTS transactions ("
                    + "account_id TEXT, "
                    + "date_of_transaction TEXT, "
                    + "date_of_payment TEXT, "
                    + "unit REAL, "
                    + "rate_per_unit REAL, "
                    + "amount REAL, "
                    + "bill_number TEXT, "
                    + "payment_status TEXT"
                    + ");";
            stmt.executeUpdate(createTransactionsTable);
            System.out.println("Table 'transactions' created/verified.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(jdbcUrl);
    }
}
