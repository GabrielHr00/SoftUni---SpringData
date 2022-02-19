import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class _04_AddMinion {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "root");

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", props);

        Scanner sc = new Scanner(System.in);
        String[] minionInfo = sc.nextLine().split("\\s+");
        String minionName = minionInfo[1];
        int minionAge = Integer.parseInt(minionInfo[2]);
        String minionTown = minionInfo[3];

        String villainInfo = sc.nextLine().split("\\s+")[1];

        int townId = getOrInsertTown(conn, minionTown);
        int villainID = getOrInsertVillain(conn, villainInfo);

        PreparedStatement insertIntoMinions = conn.prepareStatement(
                "INsert into minions(name, age, town_id) values(?, ?, ?);");
        insertIntoMinions.setString(1, minionName);
        insertIntoMinions.setInt(2, minionAge);
        insertIntoMinions.setInt(3, townId);
        insertIntoMinions.executeUpdate();

        PreparedStatement lastMinion = conn.prepareStatement(
                "SELECT id FROM minions ORDER BY id DESC;");
        ResultSet rs  = lastMinion.executeQuery();
        rs.next();
        int lastMinionId  = rs.getInt("id");

        PreparedStatement insertIntoMinionsVillains = conn.prepareStatement(
                "Insert into minions_villains values(?, ?);");
        insertIntoMinionsVillains.setInt(1, lastMinionId);
        insertIntoMinionsVillains.setInt(2, villainID);
        insertIntoMinionsVillains.executeUpdate();


        System.out.printf("Successfully added %s to be minion of %s.%n", minionName, villainInfo);
        conn.close();

    }
    private static int getOrInsertVillain(Connection conn, String villainName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM villains WHERE name = ?;");
        stmt.setString(1, villainName);

        ResultSet villainSet = stmt.executeQuery();

        int villainId = 0;
        if(!villainSet.next()){
            PreparedStatement newVillain = conn.prepareStatement("INSERT INTO villains(name, evilness_factor) VALUES (?, ?);");
            newVillain.setString(1, villainName);
            newVillain.setString(2, "evil");
            newVillain.executeUpdate();

            ResultSet newVillainSet = stmt.executeQuery();
            newVillainSet.next();
            villainId = newVillainSet.getInt("id");
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }else{
            villainId = villainSet.getInt("id");
        }
        return villainId;
    }


    private static int getOrInsertTown(Connection conn, String minionTown) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM towns WHERE name = ?;");
        stmt.setString(1, minionTown);

        ResultSet townRs = stmt.executeQuery();

        int townId = 0;
        if(!townRs.next()){
            PreparedStatement newTown = conn.prepareStatement("INSERT INTO towns(name) VALUES (?);");
            newTown.setString(1, minionTown);
            newTown.execute();

            ResultSet newTownSet = stmt.executeQuery();
            newTownSet.next();
            townId = newTownSet.getInt("id");
            System.out.printf("Town %s was added to the database.%n", minionTown);
        }else{
            townId = townRs.getInt("id");
        }
        return townId;
    }
}
