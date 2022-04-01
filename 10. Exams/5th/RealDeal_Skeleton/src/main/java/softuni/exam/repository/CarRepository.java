package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.Car;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByMakeAndModelAndKilometers(String make, String model, int kilometers);

    @Query("SELECT COUNT(p.id) FROM Car AS c JOIN Picture AS p ON c.id = p.car.id WHERE p.car.id = :id")
    long getCountOfPicturesWithId(long id);

    @Query("SELECT c FROM Car as c JOIN Picture AS p ON c.id = p.car.id WHERE p.car.id = c.id GROUP BY c.id ORDER BY count(p.id) DESC, c.make ASC")
    List<Car> findAllCarsAndCountOfPictures();
}
