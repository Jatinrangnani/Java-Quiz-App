import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegistrationPage extends JFrame {
    public RegistrationPage() {
        setTitle("Register");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 255));
        setLayout(null);

        JLabel heading = new JLabel("Register");
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        heading.setBounds(600, 50, 200, 40);
        add(heading);

        JTextField nameT = new JTextField();
        JTextField userT = new JTextField();
        JPasswordField passT = new JPasswordField();
        JTextField emailT = new JTextField();

        JLabel[] labels = {
            new JLabel("Name:"), new JLabel("Username:"),
            new JLabel("Password:"), new JLabel("Email:")
        };
        JComponent[] fields = {nameT, userT, passT, emailT};

        for (int i = 0; i < labels.length; i++) {
            labels[i].setBounds(500, 150 + (i * 50), 100, 30);
            fields[i].setBounds(600, 150 + (i * 50), 200, 30);
            labels[i].setFont(new Font("Arial", Font.BOLD, 14));
            add(labels[i]);
            add(fields[i]);
        }

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(600, 370, 100, 40);
        add(registerBtn);

        JButton loginBtn = new JButton("Already have an account?");
        loginBtn.setBounds(600, 430, 200, 30);
        add(loginBtn);

        registerBtn.addActionListener(e -> {
            String name = nameT.getText().trim();
            String username = userT.getText().trim();
            String password = new String(passT.getPassword()).trim();
            String email = emailT.getText().trim();

            if (name.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required.");
                return;
            }

            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Database connection failed.");
                return;
            }

            try {
                String sql = "INSERT INTO users (name, username, password, email) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, username);
                stmt.setString(3, password);
                stmt.setString(4, email);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Registration successful!");
                    dispose();
                    new LoginPage();
                } else {
                    JOptionPane.showMessageDialog(null, "Registration failed. Try again.");
                }

                stmt.close();
                conn.close();
            } catch (SQLIntegrityConstraintViolationException ex) {
                JOptionPane.showMessageDialog(null, "Username or Email already exists.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        loginBtn.addActionListener(e -> {
            dispose();
            new LoginPage();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistrationPage::new);
    }
}
