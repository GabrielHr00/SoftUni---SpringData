import java.sql.*;
import java.util.Properties;

public class _02_countMinions {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "root");

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", props);

        PreparedStatement stmt = conn.prepareStatement("SELECT v.name, COUNT(DISTINCT mv.minion_id) as minions_count FROM villains as v JOIN minions_villains as mv ON mv.villain_id = v.id GROUP BY v.name HAVING minions_count > 15 ORDER BY minions_count DESC;");
        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            String villainsName = rs.getString("name");
            int count = rs.getInt("minions_count");

            System.out.println(villainsName + " " + count);
        }

        conn.close();
    }
}
