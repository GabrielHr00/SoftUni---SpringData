import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class _08_IncreaseMinionsAge {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "root");

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", props);

        Scanner sc = new Scanner(System.in);
        String[] minionsId = sc.nextLine().split("\\s+");

        try {
            PreparedStatement updateAgeMinions = conn.prepareStatement("UPDATE minions SET age = age + 1 AND name = LOWER(name) WHERE id = ?");

            for (int i = 0; i < minionsId.length; i++) {
                updateAgeMinions.setInt(1, Integer.parseInt(minionsId[i]));
            }
            updateAgeMinions.execute();
            updateAgeMinions.close();
        } catch(SQLException e){
            e.printStackTrace();
        }

        PreparedStatement allMinions = conn.prepareStatement("SELECT DISTINCT name, age FROM minions");
        ResultSet rs = allMinions.executeQuery();
        Map<String, Integer> toPrintMap = new HashMap<>();

        while(rs.next()){
            String name = rs.getString("name");
            Integer age = rs.getInt("age");
            System.out.println(name + " " + age);
            toPrintMap.put(name, age);
        }

    }
}
