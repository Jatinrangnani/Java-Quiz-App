import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ScoreDAO {

    public static void saveScore(int userId, int score) {
        String insertSQL = "INSERT INTO scores (user_id, score) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, score);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Score saved successfully!");
            }

        } catch (SQLException e) {
            System.err.println("❌ Error saving score to database:");
            e.printStackTrace();
        }
    }
}
