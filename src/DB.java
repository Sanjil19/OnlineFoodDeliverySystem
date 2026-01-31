import java.sql.*;

public class DB {
    public static Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/food_delivery_db";
        String user = "food_user";
        String pass = "$@njeel484";
        return DriverManager.getConnection(url, user, pass);
    }
}