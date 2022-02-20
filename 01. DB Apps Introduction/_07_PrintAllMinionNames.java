import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class _07_PrintAllMinionNames {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "root");

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", props);

        PreparedStatement allMinions = conn.prepareStatement("SELECT DISTINCT name FROM minions");
        ResultSet minions = allMinions.executeQuery();

        List<String> allM = new ArrayList<>();
        while(minions.next()){
            allM.add(minions.getString("name"));
        }

        for (int i = 0; i < allM.size()/2; i++) {
            System.out.println(allM.get(i));
            System.out.println(allM.get(allM.size() - 1 - i));
        }
    }
}
