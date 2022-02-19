import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class _03_SelectByVillain {
    public static void main(String[] args) throws SQLException {
    Properties props = new Properties();
    props.setProperty("user", "root");
    props.setProperty("password", "root");

    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", props);

    PreparedStatement stmt = conn.prepareStatement("SELECT v.name, m.name, m.age" +
            " FROM villains as v JOIN minions_villains as mv ON mv.villain_id = v.id" +
            " JOIN minions as m ON m.id = mv.minion_id" +
            " WHERE v.id = ?;");

    Scanner sc = new Scanner(System.in);
    int villainID = Integer.parseInt(sc.nextLine());
    stmt.setInt(1, villainID);

    ResultSet rs = stmt.executeQuery();

    if(!rs.next()){
        System.out.printf("No villain with ID %d exists in the database.", villainID);
        return;
    }


    String villainsName = rs.getString("name");
    System.out.println("Villain: " + villainsName);

    PreparedStatement minions = conn.prepareStatement("SELECT name, age" +
            " FROM minions as m JOIN minions_villains as mv ON m.id = mv.minion_id" +
            " WHERE mv.villain_id = ?;");

    minions.setInt(1, villainID);
    ResultSet rs1 = minions.executeQuery();

    for (int i = 1; rs1.next(); i++) {
         String name = rs1.getString("name");
         int age = rs1.getInt("age");

        System.out.printf("%d. %s %d%n", i, name, age);
    }

    conn.close();

    }
}
