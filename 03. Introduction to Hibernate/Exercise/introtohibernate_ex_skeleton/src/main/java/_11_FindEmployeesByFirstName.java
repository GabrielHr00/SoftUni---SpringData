import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class _11_FindEmployeesByFirstName {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Scanner sc = new Scanner(System.in);
        String patt = sc.nextLine();

        em.createQuery("SELECT e FROM Employee e" +
                " WHERE e.firstName LIKE :pattern",  Employee.class)
                .setParameter("pattern", patt + "%")
                .getResultStream().forEach(e -> {
                    System.out.println(String.format(
                            "%s %s - %s - ($%.2f)",
                            e.getFirstName(),
                            e.getLastName(),
                            e.getJobTitle(),
                            e.getSalary()));
                });

        em.getTransaction().commit();
        em.close();
    }
}
