import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class _13_RemoveTowns {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Scanner sc = new Scanner(System.in);
        String town = sc.nextLine();

        List<Address> aToDelete = em.createQuery("SELECT a FROM Address a WHERE a.town.name = :town_name",  Address.class)
                .setParameter("town_name", town)
                .getResultList();

        int count = aToDelete.size();

        for (Address a : aToDelete) {
            em.remove(a);
        }
        System.out.println(String.format("%d address in %s deleted", count, town));

        em.getTransaction().commit();
        em.close();
    }
}
