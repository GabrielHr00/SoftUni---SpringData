import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class _06_AddingNewAddress {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        String address = "Vitoshka 15";
        Address address1 = new Address();
        address1.setText(address);
        em.persist(address1);

        Scanner sc = new Scanner(System.in);
        String employee = sc.nextLine();

        Employee empl = em.createQuery("SELECT e FROM Employee e" +
                " WHERE e.lastName = :last ", Employee.class)
                .setParameter("last", employee).getSingleResult();

        empl.setAddress(address1);
        em.persist(empl);

        em.getTransaction().commit();
        em.close();
    }
}
