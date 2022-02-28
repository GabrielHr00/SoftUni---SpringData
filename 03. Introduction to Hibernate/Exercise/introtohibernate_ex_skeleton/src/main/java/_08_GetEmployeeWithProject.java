import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class _08_GetEmployeeWithProject {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Scanner sc = new Scanner(System.in);
        int id = Integer.parseInt(sc.nextLine());


        em.createQuery("SELECT e FROM Employee e" +
                " WHERE e.id = :id_console",  Employee.class)
                .setParameter("id_console", id)
                .getResultStream().forEach(e -> {
                    List<String> names = e.getProjects().stream().map(f -> f.getName()).sorted().collect(Collectors.toList());
                    String result = String.format("%s %s - %s%n     %s", e.getFirstName(), e.getLastName(), e.getJobTitle(), String.join("\n     ", names));
                    System.out.println(result);
                });

        em.getTransaction().commit();
        em.close();
    }
}
