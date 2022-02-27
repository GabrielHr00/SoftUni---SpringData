import enitites.Student;
import enitites.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Teacher teacher = new Teacher("Petka", LocalDate.now());
        em.persist(teacher);
        Student student = new Student("gosho");
        em.persist(student);
        
        Student first = em.find(Student.class, 2);
        em.remove(first);

        em.getTransaction().commit();
        em.close();
    }
}
