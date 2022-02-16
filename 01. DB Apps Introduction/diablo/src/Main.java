import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "root");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", props);
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT user_name, first_name, last_name, COUNT(ug.id) " +
                        "FROM users as u JOIN users_games ug ON u.id = ug.user_id " +
                        "WHERE user_name = ? " +
                        " GROUP BY u.id;"
        );

        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine();

        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if(rs.next()){
            String user = rs.getString("user_name");
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            Integer count = rs.getInt("COUNT(ug.id)");

            System.out.printf("User: %s%n" +
                    "%s %s has played %d games", user, first_name, last_name, count);
        }else{
            System.out.println("No such user exists");
        }

        connection.close();

    }
}
