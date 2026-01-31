import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class CustomerUI extends JFrame {
    JPanel menuPanel = new JPanel();
    ArrayList<String> cartItems = new ArrayList<>();
    double totalBill = 0.0;

    public CustomerUI() {
        setTitle("Order Food");
        setSize(600, 500);
        setLayout(new BorderLayout());

        menuPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 2 columns
        loadMenu();

        add(new JScrollPane(menuPanel), BorderLayout.CENTER);

        JButton checkoutBtn = new JButton("View Cart & Checkout");
        checkoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Items: " + cartItems + "\nTotal: $" + totalBill);
        });
        add(checkoutBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadMenu() {
        try (Connection conn = DB.connect()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM food_items WHERE availability=1");

            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String imgFile = rs.getString("image_name");

                // Create a "Card" for each food
                JPanel card = new JPanel();
                card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

                // 1. Add Image
                try {
                    ImageIcon icon = new ImageIcon("images/" + imgFile);
                    Image img = icon.getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH);
                    card.add(new JLabel(new ImageIcon(img)));
                } catch (Exception e) {
                    card.add(new JLabel("[No Image]"));
                }

                // 2. Add Name and Price
                card.add(new JLabel(" " + name));
                card.add(new JLabel(" Price: $" + price));

                // 3. Add to Cart Button
                JButton addBtn = new JButton("Add to Cart");
                addBtn.addActionListener(e -> {
                    cartItems.add(name);
                    totalBill += price;
                    System.out.println("Added " + name + " to cart.");
                });
                card.add(addBtn);

                menuPanel.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}