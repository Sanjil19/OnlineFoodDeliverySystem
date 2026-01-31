import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class AdminDashboard extends JFrame {
    JTable table;
    DefaultTableModel model;

    public AdminDashboard() {
        setTitle("Admin Panel - All Restaurants");
        setSize(500, 400);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Address", "Active"}, 0);
        table = new JTable(model);
        loadData();

        add(new JScrollPane(table));
        setVisible(true);
    }

    private void loadData() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/food_delivery_db", "food_user", "$@njeel484");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM restaurants");
            while(rs.next()){
                model.addRow(new Object[]{rs.getInt("restaurant_id"), rs.getString("name"), rs.getString("address"), rs.getBoolean("is_active")});
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}