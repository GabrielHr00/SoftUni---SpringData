package _02;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class _02Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CodeFirst");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Customer c = new Customer("pencho", "pencho@abv.bg", "119114741");
        Product p = new Product("lotion", 1, new BigDecimal(3.50));
        StoreLocation sl = new StoreLocation("Sofia");
        Sale s = new Sale(LocalDateTime.now(), p, c, sl);

        em.persist(c);
        em.persist(p);
        em.persist(sl);
        em.persist(s);


        em.getTransaction().commit();
        em.close();
    }
}
