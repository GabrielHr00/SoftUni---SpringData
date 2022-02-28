import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class _04_EmployeesSalaryOver50000 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        List<String> richEmployees =  em.createQuery("SELECT e.firstName FROM Employee e WHERE e.salary > 50000", String.class).getResultList();

        System.out.println(String.join("\n", richEmployees));

        em.getTransaction().commit();
    }
}
