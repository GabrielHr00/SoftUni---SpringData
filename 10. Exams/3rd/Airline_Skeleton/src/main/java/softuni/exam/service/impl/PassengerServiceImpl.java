package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.PassengersImportDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PassengerService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {
    private final TownRepository townRepository;
    private final PassengerRepository passengerRepository;
    private final ModelMapper mapper;
    private final Gson gson;
    private final Path path = Path.of("src", "main", "resources", "files", "json", "passengers.json");
    private final Validator validator;

    @Autowired
    public PassengerServiceImpl(TownRepository townRepository, PassengerRepository passengerRepository, ModelMapper mapper, Gson gson, Validator validator) {
        this.townRepository = townRepository;
        this.passengerRepository = passengerRepository;
        this.mapper = mapper;
        this.gson = gson;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importPassengers() throws IOException {
        List<String> result = new ArrayList<>();
        PassengersImportDto[] dtos = this.gson.fromJson(readPassengersFileContent(), PassengersImportDto[].class);

        for (PassengersImportDto t: dtos) {
            Set<ConstraintViolation<PassengersImportDto>> validate =
                    validator.validate(t);

            if(validate.isEmpty()){
                String s = t.getTown();
                Passenger o = this.mapper.map(t, Passenger.class);
                Optional<Town> byName = this.townRepository.findByName(s);

                o.setTown(byName.get());
                this.passengerRepository.save(o);
                result.add("Successfully imported Passenger " + o.getLastName() + " - " + o.getEmail());

            } else {
                result.add("Invalid Passenger");
            }
        }

        return String.join("\n", result);
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        List<Passenger> bestPassengers = this.passengerRepository.findBestPassengers();
        List<String> result = new ArrayList<>();

        for (Passenger p : bestPassengers) {
            result.add(String.format("Passenger %s %s\n  Email - %s\n  Phone - %s\n  Number of tickets - %d\n", p.getFirstName(), p.getLastName(), p.getEmail(), p.getPhoneNumber(), passengerRepository.getPassengersCount(p.getId())));
        }

        return String.join("\n", result);
    }
}
