import entities.Town;

import javax.persistence.*;
import java.util.List;

public class _02_ChangeCasing {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Query from_town = em.createQuery("SELECT t FROM Town t", Town.class);
        List<Town> towns = from_town.getResultList();

        for (var t: towns) {
            if(t.getName().length() <= 5){
                t.setName(t.getName().toUpperCase());
                em.persist(t);
            }
        }

        em.getTransaction().commit();
    }
}
