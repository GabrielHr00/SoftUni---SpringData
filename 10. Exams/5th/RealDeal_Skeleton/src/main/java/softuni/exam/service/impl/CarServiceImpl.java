package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.CarsImportDto;
import softuni.exam.models.entities.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelMapper mapper;
    private final Validator validator;
    private final Gson gson;
    private final Path path = Path.of("src", "main", "resources", "files", "json", "cars.json");

    public CarServiceImpl(CarRepository carRepository, ModelMapper mapper, Validator validator, Gson gson) {
        this.carRepository = carRepository;
        this.mapper = mapper;
        this.validator = validator;
        this.gson = gson;
        this.mapper.addConverter(e -> LocalDate.parse(e.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy")), String.class, LocalDate.class);
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importCars() throws IOException {
        List<String> result = new ArrayList<>();
        CarsImportDto[] dtos = this.gson.fromJson(readCarsFileContent(), CarsImportDto[].class);

        for (CarsImportDto c: dtos) {
            Set<ConstraintViolation<CarsImportDto>> validate = this.validator.validate(c);

            if(validate.isEmpty()){
                Car car = this.mapper.map(c, Car.class);
                Optional<Car> byName = this.carRepository.findByMakeAndModelAndKilometers(c.getMake(), c.getModel(), c.getKilometers());
                if(byName.isEmpty()) {
                    this.carRepository.save(car);
                    result.add("Successfully imported car - " + car.getMake() + " - " + car.getModel());
                }
                else {
                    result.add("Invalid car");
                }
            }
            else {
                result.add("Invalid car");
            }
        }

        return String.join("\n", result);
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        List<String> result = new ArrayList<>();
        List<Car> allCarsAndCountOfPictures = this.carRepository.findAllCarsAndCountOfPictures();
        for (Car c : allCarsAndCountOfPictures) {
            result.add(String.format("Car make - %s, model - %s\n  Kilometers - %d\n  Registered on - %s\n  Number of pictures - %d\n", c.getMake(), c.getModel(), c.getKilometers(), c.getRegisteredOn().toString(), this.carRepository.getCountOfPicturesWithId(c.getId())));
        }
        return String.join("\n", result);
    }
}
