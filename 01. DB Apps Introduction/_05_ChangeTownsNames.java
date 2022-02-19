import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class _05_ChangeTownsNames {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "root");

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", props);

        Scanner sc = new Scanner(System.in);
        String country = sc.nextLine();

        PreparedStatement update = conn.prepareStatement(
                "UPDATE towns SET name = UPPER(name) WHERE country = ?;");

        update.setString(1, country);
        int updatedCount = update.executeUpdate();

        if(updatedCount == 0){
            System.out.println("No town names were affected.");
            return;
        }

        System.out.println(updatedCount + " town names were affected.");
        PreparedStatement selectAllTowns = conn.prepareStatement("SELECT name FROM towns WHERE country = ?");

        selectAllTowns.setString(1, country);
        ResultSet towns = selectAllTowns.executeQuery();

        List<String> towns1 = new ArrayList<>();
        while(towns.next()){
            String townName = towns.getString("name");
            towns1.add(townName);
        }

        System.out.println(towns1);
        conn.close();

    }
}
