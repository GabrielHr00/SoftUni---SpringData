package exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exam.model.dtos.CustomersImportDto;
import exam.model.entities.Customer;
import exam.model.entities.Town;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
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
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final TownRepository townRepository;
    private final ModelMapper mapper;
    private final Validator validator;
    private final Gson gson;
    private final Path path = Path.of( "src", "main", "resources", "files", "json", "customers.json");


    public CustomerServiceImpl(CustomerRepository customerRepository, TownRepository townRepository) {
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;
        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.mapper.addConverter(e -> LocalDate.parse(e.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy")), String.class, LocalDate.class);

    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importCustomers() throws IOException {
        CustomersImportDto[] dtos = gson.fromJson(readCustomersFileContent(), CustomersImportDto[].class);

        List<String> result = new ArrayList<>();
        for (CustomersImportDto c: dtos) {
            Set<ConstraintViolation<CustomersImportDto>> validate = validator.validate(c);
            if(validate.isEmpty()) {
                Optional<Customer> byName = this.customerRepository.findByEmail(c.getEmail());

                if(byName.isPresent()) {
                    result.add("Invalid Customer");
                } else {
                    Optional<Town> town = this.townRepository.findByName(c.getTown().getName());
                    Customer map = this.mapper.map(c, Customer.class);

                    map.setTown(town.get());
                    this.customerRepository.save(map);
                    result.add("Successfully imported Customer " + map.getFirstName() + " " + map.getLastName() + " - " + map.getEmail());
                }

            } else {
                result.add("Invalid Customer");
            }

        }

        return String.join("\n", result);

    }
}
