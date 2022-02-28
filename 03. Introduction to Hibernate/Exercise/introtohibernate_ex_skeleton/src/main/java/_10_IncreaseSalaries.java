import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class _10_IncreaseSalaries {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        List<Employee> emps = em.createQuery("SELECT e FROM Employee e" +
                " WHERE e.department.name = :dep1 OR e.department.name = :dep2 OR e.department.name = :dep3 OR e.department.name = :dep4",  Employee.class)
                .setParameter("dep1", "Engineering")
                .setParameter("dep2", "Tool Design")
                .setParameter("dep3", "Marketing")
                .setParameter("dep4", "Information Services")
                .getResultList();


        for (Employee e : emps) {
            e.setSalary(e.getSalary().multiply(new BigDecimal(1.12)));
            System.out.println(String.format("%s %s - ($%.2f)", e.getFirstName(), e.getLastName(), e.getSalary()));
            em.persist(e);
        }

        em.getTransaction().commit();
        em.close();
    }
}
