import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class _03_CheckEmployeeExists {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Scanner sc = new Scanner(System.in);
        String[] name = sc.nextLine().split("\\s+");


        Long emp = em.createQuery("SELECT COUNT(e) FROM Employee e WHERE e.firstName = :first_name AND e.lastName = :last_name", Long.class).
                setParameter("first_name", name[0]).setParameter("last_name", name[1]).getSingleResult();

        if(emp <= 0){
            System.out.println(name[0] + " " + name[1] + " -- " + "NO");
        } else{
            System.out.println(name[0] + " " + name[1] + " -- " + "YES");
        }

        em.getTransaction().commit();
    }
}
