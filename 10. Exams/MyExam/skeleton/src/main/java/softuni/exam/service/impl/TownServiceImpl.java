package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.TownsImportDto;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;

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

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final ModelMapper mapper;
    private final Validator validator;
    private final Gson gson;
    private final Path path = Path.of("src", "main", "resources", "files", "json", "towns.json");

    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;
        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importTowns() throws IOException {
        List<String> result = new ArrayList<>();
        TownsImportDto[] dtos = this.gson.fromJson(readTownsFileContent(), TownsImportDto[].class);

        for (TownsImportDto t : dtos) {
            Set<ConstraintViolation<TownsImportDto>> validate = this.validator.validate(t);

            if(validate.isEmpty()) {
                Optional<Town> byTownName = this.townRepository.findByTownName(t.getTownName());

                if(byTownName.isPresent()) {
                    result.add("Invalid town");
                }
                else{
                    Town town = (Town)this.mapper.map(t, Town.class);
                    this.townRepository.save(town);
                    result.add("Successfully imported town " + town.getTownName() + " - " + town.getPopulation());
                }
            }
            else {
                result.add("Invalid town");
            }
        }

        return String.join("\n", result);
    }
}
