import entities.Department;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Set;

public class _12_EmployeesMaximumSalaries {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        em.createQuery("FROM Department d",  Department.class)
                .getResultStream().forEach(e -> {
            BigDecimal maxy = e.getEmployees().stream().map(f -> f.getSalary()).max(Comparator.naturalOrder())
                    .orElse(BigDecimal.ZERO);
            if(maxy.compareTo(new BigDecimal(30000)) == 0 || maxy.compareTo(new BigDecimal(70000)) == 1){
                System.out.println(String.format(
                        "%s  %.2f",
                        e.getName(),
                        maxy));
            }
        });

        em.getTransaction().commit();
        em.close();
    }
}
