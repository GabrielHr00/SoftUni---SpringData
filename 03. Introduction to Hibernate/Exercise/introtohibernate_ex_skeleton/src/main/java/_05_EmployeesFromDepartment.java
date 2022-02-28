import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class _05_EmployeesFromDepartment {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        em.createQuery("SELECT e FROM Employee e WHERE e.department.name = :department_name ORDER BY e.salary ASC, e.id ASC", Employee.class)
                .setParameter("department_name", "Research and Development").getResultStream()
                .forEach(e -> {
                    String result = String.format("%s %s from Research and Development - $%.2f", e.getFirstName(), e.getLastName(), e.getSalary());
                    System.out.println(result);
                });

        em.getTransaction().commit();
        em.close();
    }
}
