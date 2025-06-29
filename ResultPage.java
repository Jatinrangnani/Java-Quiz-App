import javax.swing.*;
import java.awt.*;

public class ResultPage extends JFrame {
    public ResultPage(User user, int score) {
        setTitle("Quiz Result");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(new Color(235, 255, 235));
        setLayout(null);

        String name = (user != null && user.getName() != null) ? user.getName() : "User";

        JLabel msg = new JLabel("Thank you, " + name + "!");
        msg.setFont(new Font("Arial", Font.BOLD, 36));
        msg.setBounds(500, 200, 600, 50);
        add(msg);

        JLabel sc = new JLabel("Your Score: " + score + " / 5");
        sc.setFont(new Font("Arial", Font.BOLD, 32));
        sc.setBounds(500, 280, 600, 40);
        add(sc);
if (user != null) {
    ScoreDAO.saveScore(user.getId(), score);
}

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(500, 360, 100, 40);
        exitBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        add(exitBtn);
        exitBtn.addActionListener(e -> System.exit(0));

        JButton restartBtn = new JButton("Restart Quiz");
        restartBtn.setBounds(620, 360, 150, 40);
        restartBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        add(restartBtn);
        restartBtn.addActionListener(e -> {
            dispose();
            new LoginPage();
        });


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
} 