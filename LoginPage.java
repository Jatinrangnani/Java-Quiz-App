import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginPage extends JFrame {

    public LoginPage() {
        setTitle("Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(230, 240, 250));
        setLayout(null);

        JTextField userT = new JTextField();
        JPasswordField passT = new JPasswordField();

        JLabel userL = new JLabel("Username:");
        JLabel passL = new JLabel("Password:");

        userL.setBounds(500, 200, 100, 30);
        passL.setBounds(500, 250, 100, 30);
        userT.setBounds(600, 200, 200, 30);
        passT.setBounds(600, 250, 200, 30);

        add(userL);
        add(userT);
        add(passL);
        add(passT);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(600, 320, 100, 40);
        add(loginBtn);

        JButton registerBtn = new JButton("New user? Register");
        registerBtn.setBounds(600, 380, 180, 30);
        add(registerBtn);

        loginBtn.addActionListener(e -> {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Database connection failed. Please check your setup.");
                return;
            }

            try {
                String sql = "SELECT * FROM users WHERE username=? AND password=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, userT.getText());
                stmt.setString(2, new String(passT.getPassword()));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("email")
                    );
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    dispose();
                    new QuizPage(user);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                }

                stmt.close();
                conn.close();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error occurred during login. See console for details.");
                ex.printStackTrace();
            }
        });

        registerBtn.addActionListener(e -> {
            dispose();
            new RegistrationPage();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }
}
