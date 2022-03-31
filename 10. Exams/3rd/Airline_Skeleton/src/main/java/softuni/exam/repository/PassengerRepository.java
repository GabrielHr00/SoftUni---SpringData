package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.Passenger;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository  extends JpaRepository<Passenger, Long> {

    Optional<Passenger> findByEmail(String passenger);

    @Query("SELECT COUNT(t.id) FROM Passenger AS p JOIN Ticket AS t ON p.id = t.passenger.id WHERE p.id = :id")
    int getPassengersCount(long id);

    @Query("SELECT p FROM Passenger AS p JOIN Ticket AS t ON p.id = t.passenger.id WHERE t.passenger.id = p.id GROUP BY p.id ORDER BY count(t.id) DESC, p.email DESC")
    List<Passenger> findBestPassengers();
}
