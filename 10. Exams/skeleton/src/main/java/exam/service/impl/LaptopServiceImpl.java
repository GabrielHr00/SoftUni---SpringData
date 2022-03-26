package exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exam.model.dtos.CustomersImportDto;
import exam.model.dtos.LaptopsImportDto;
import exam.model.entities.Customer;
import exam.model.entities.Laptop;
import exam.model.entities.Shop;
import exam.model.entities.Town;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.LaptopService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
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
public class LaptopServiceImpl implements LaptopService {
    private final LaptopRepository laptopRepository;
    private final ShopRepository shopRepository;
    private final ModelMapper mapper;
    private final Validator validator;
    private final Gson gson;
    private final Path path = Path.of( "src", "main", "resources", "files", "json", "laptops.json");


    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopRepository shopRepository) {
        this.laptopRepository = laptopRepository;
        this.shopRepository = shopRepository;

        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }


    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importLaptops() throws IOException {
        LaptopsImportDto[] dtos = gson.fromJson(readLaptopsFileContent(), LaptopsImportDto[].class);

        List<String> result = new ArrayList<>();
        for (LaptopsImportDto c: dtos) {
            Set<ConstraintViolation<LaptopsImportDto>> validate = validator.validate(c);

            if(validate.isEmpty()) {
                Optional<Laptop> byName = this.laptopRepository.findByMacAddress(c.getMacAddress());

                if(byName.isPresent()) {
                    result.add("Invalid Laptop");
                } else {
                    Optional<Shop> shop = this.shopRepository.findByName(c.getShop().getName());
                    Laptop map = this.mapper.map(c, Laptop.class);

                    map.setShop(shop.get());
                    this.laptopRepository.save(map);
                    result.add(String.format("Successfully imported Laptop %s - %.2f - %d - %d", map.getMacAddress(), map.getCpuSpeed(), map.getStorage(), map.getRam()));
                }

            } else {
                result.add("Invalid Laptop");
            }

        }

        return String.join("\n", result);

    }

    @Override
    public String exportBestLaptops() {
        List<Laptop> result = this.laptopRepository.findAllByOrderByCpuSpeedDescRamDescStorageDescMacAddressAsc();
        List<String> collect = result.stream().map(e -> e.toString()).collect(Collectors.toList());
        return String.join("\n", collect);
    }
}
