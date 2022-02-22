import entities.User;
import orm.EntityManager;
import orm.MyConnector;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        MyConnector.createConnection("root", "root", "miniORM");
        Connection conn = MyConnector.getConnection();

        EntityManager<User> userEntityManager = new EntityManager<>(conn);

        User user = new User("pesho", 25, LocalDate.now());

        userEntityManager.persist(user);

        User foundUser = userEntityManager.findFirst(User.class, "age > 30");
    }
}
