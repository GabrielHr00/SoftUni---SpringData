import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class _09_IncreaseAgeStoredProcedure {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "root");

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", props);

        Scanner sc = new Scanner(System.in);
        int minion_id = Integer.parseInt(sc.nextLine());

        PreparedStatement procedure = conn.prepareStatement("CALL usp_get_older(?);");
        procedure.setInt(1, minion_id);
        procedure.executeQuery();

        PreparedStatement printInfo = conn.prepareStatement("SELECT name, age FROM minions WHERE id = ?");
        printInfo.setInt(1, minion_id);
        ResultSet print = printInfo.executeQuery();

        if(print.next()){
            System.out.println(print.getString("name") + " " + print.getInt("age"));
        }
    }
}
