import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterUI extends JFrame {
    // UI Elements
    JTextField nameTxt = new JTextField(20);
    JTextField emailTxt = new JTextField(20);
    JTextField phoneTxt = new JTextField(20);
    JPasswordField passTxt = new JPasswordField(20);
    JTextArea addrTxt = new JTextArea(3, 20);
    JComboBox<String> roleBox = new JComboBox<>(new String[]{"customer", "restaurant"});
    JButton saveBtn = new JButton("Create Account");

    public RegisterUI() {
        setTitle("Create New Account");
        setSize(300, 450);
        setLayout(new FlowLayout());

        add(new JLabel("Full Name:")); add(nameTxt);
        add(new JLabel("Email:")); add(emailTxt);
        add(new JLabel("Phone:")); add(phoneTxt);
        add(new JLabel("Password:")); add(passTxt);
        add(new JLabel("Address:")); add(new JScrollPane(addrTxt));
        add(new JLabel("I am a:")); add(roleBox);
        add(saveBtn);

        saveBtn.addActionListener(e -> handleRegistration());
        setVisible(true);
    }

    private void handleRegistration() {
        try {
            // 1. Connect to DB
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/food_delivery_db", "food_user", "$@njeel484");

            // 2. Prepare SQL (Matches your table structure)
            String sql = "INSERT INTO users (name, email, phone, password, role, address) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, nameTxt.getText());
            pstmt.setString(2, emailTxt.getText());
            pstmt.setString(3, phoneTxt.getText());
            pstmt.setString(4, new String(passTxt.getPassword()));
            pstmt.setString(5, roleBox.getSelectedItem().toString());
            pstmt.setString(6, addrTxt.getText());

            // 3. Execute
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Account Created! You can now login.");
                this.dispose(); // Close registration window
            }

            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}