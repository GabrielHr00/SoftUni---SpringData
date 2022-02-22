import entities.User;
import orm.EntityManager;
import orm.MyConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException {
        MyConnector.createConnection("root", "root", "miniORM");
        Connection conn = MyConnector.getConnection();

        EntityManager<User> userEntityManager = new EntityManager<>(conn);

        User user = new User("pesho", 25, LocalDate.now());

        userEntityManager.persist(user);
    }
}
