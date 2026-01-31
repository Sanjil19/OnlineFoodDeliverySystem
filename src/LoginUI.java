import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class LoginUI extends JFrame {
    JTextField userField = new JTextField(20);
    JPasswordField passField = new JPasswordField(20);
    JButton loginBtn = new JButton("Login");
    JButton regBtn = new JButton("Register New Account");
    public LoginUI() {
        setTitle("Food App Login");
        setSize(300, 200);
        setLayout(new java.awt.FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new JLabel("Username (Email):"));
        add(userField);
        add(new JLabel("Password:"));
        add(passField);
        add(loginBtn);
        add(regBtn);
        regBtn.addActionListener(e -> new RegisterUI());
        loginBtn.addActionListener(e -> checkLogin());

        setVisible(true);
    }

    private void checkLogin() {
        String user = userField.getText();
        String pass = new String(passField.getPassword());

        // FIXED ADMIN CHECK
        if (user.equals("admin") && pass.equals("admin123")) {
            JOptionPane.showMessageDialog(this, "Welcome Admin!");
            new AdminDashboard();
            this.dispose();
            return;
        }

        // DATABASE CHECK FOR CUSTOMERS
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/food_delivery_db", "food_user", "$@njeel484");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email=? AND password=?");
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new CustomerUI();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LoginUI();
    }
}