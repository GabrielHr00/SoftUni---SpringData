import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.format.DateTimeFormatter;

public class _09_FindLatest10Projects {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        em.createQuery("SELECT p FROM Project p" +
                " ORDER BY p.startDate DESC",  Project.class)
                .setMaxResults(10)
                .getResultStream().sorted((a,b) -> a.getName().compareTo(b.getName()))
                .forEach(e -> {
            String result = String.format("Project name: " + e.getName() + "\n" +
                    "         Project Description: " + e.getDescription() + "\n"
                    + "         Project Start Date: " + (e.getStartDate() == null ? "null" : e.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) + "\n")
                    + "         Project End Date: " + (e.getEndDate() == null ? "null" : e.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            System.out.println(result);
        });

        em.getTransaction().commit();
        em.close();
    }
}
