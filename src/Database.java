import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    public static Connection getConnection() {
        try {
            // Standard JDBC connection string
            String url = "jdbc:mysql://localhost:3306/food_delivery_db";
            String user = "food_user";
            String password = "$@njeel484"; // Change this!
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }
}