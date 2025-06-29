import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class QuizPage extends JFrame {
    int index = 0, score = 0;
    List<String[]> questions = new ArrayList<>();
    ButtonGroup group = new ButtonGroup();
    JRadioButton[] options = new JRadioButton[4];
    JLabel questionL = new JLabel();
    User user;

    public QuizPage(User user) {
        this.user = user;
        setTitle("Quiz");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 245, 235));

        questionL.setBounds(300, 100, 1000, 30);
        questionL.setFont(new Font("Arial", Font.BOLD, 18));
        add(questionL);

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            options[i].setBounds(300, 160 + i * 40, 800, 30);
            options[i].setBackground(new Color(255, 245, 235));
            options[i].setFont(new Font("Arial", Font.PLAIN, 16));
            group.add(options[i]);
            add(options[i]);
        }

        JButton nextBtn = new JButton("Next");
        nextBtn.setBounds(300, 350, 100, 30);
        add(nextBtn);

        nextBtn.addActionListener(e -> {
            String selected = null;
            for (JRadioButton opt : options) {
                if (opt.isSelected()) {
                    selected = opt.getText();
                    break;
                }
            }

            if (selected != null && selected.equals(questions.get(index)[5])) {
                score++;
            }

            index++;
            if (index == questions.size()) {
                saveScore();
                dispose();
                new ResultPage(user, score);
            } else {
                loadQuestion();
            }
        });

        loadQuestionsFromDB();

        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions found in the database.");
            dispose();
            new LoginPage();
            return;
        }

        loadQuestion();
        setVisible(true);
    }

    void loadQuestionsFromDB() {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Failed to connect to the database.");
            return;
        }

        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM questions LIMIT 5");
            while (rs.next()) {
                questions.add(new String[]{
                    rs.getString("question_text"),
                    rs.getString("option1"),
                    rs.getString("option2"),
                    rs.getString("option3"),
                    rs.getString("option4"),
                    rs.getString("correct_answer")
                });
            }
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading questions.");
        }
    }

    void loadQuestion() {
        group.clearSelection();
        String[] q = questions.get(index);
        questionL.setText((index + 1) + ". " + q[0]);
        for (int i = 0; i < 4; i++) {
            options[i].setText(q[i + 1]);
        }
    }

    void saveScore() {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Unable to save score due to DB connection issue.");
            return;
        }

        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO scores (user_id, score) VALUES (?, ?)");
            stmt.setInt(1, user.getId());
            stmt.setInt(2, score);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save score.");
        }
    }
}
