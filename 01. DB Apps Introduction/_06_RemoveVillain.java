import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class _06_RemoveVillain {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "root");

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", props);

        Scanner sc = new Scanner(System.in);
        int idVillain =  Integer.parseInt(sc.nextLine());

        PreparedStatement villainThere = conn.prepareStatement("SELECT name FROM villains WHERE id = ?");
        villainThere.setInt(1, idVillain);
        ResultSet villainSet = villainThere.executeQuery();

        if(!villainSet.next()){
            System.out.println("No such villain was found");
            return;
        }

        String villainName = villainSet.getString("name");

        PreparedStatement selectAllMinions = conn.prepareStatement("SELECT Count(DISTINCT minion_id) as m_count FROM minions_villains WHERE villain_id = ?");
        selectAllMinions.setInt(1, idVillain);
        ResultSet minionsSet = selectAllMinions.executeQuery();
        minionsSet.next();
        int countIds = minionsSet.getInt("m_count");

        conn.setAutoCommit(false);


        try{
            PreparedStatement deleteMinionsVillains = conn.prepareStatement("" +
                    "DELETE FROM minions_villains WHERE villain_id = ?");
            deleteMinionsVillains.setInt(1, idVillain);
            deleteMinionsVillains.executeUpdate();

            PreparedStatement deleteVillain = conn.prepareStatement("DELETE FROM villains WHERE id = ?");
            deleteVillain.setInt(1, idVillain);
            deleteVillain.executeUpdate();

            conn.commit();

        } catch(SQLException e){
            conn.rollback();
        }
        System.out.println(villainName + " was deleted");
        System.out.println(countIds + " minions released");
        conn.close();
    }
}

