package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.TownsImportDto;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final ModelMapper mapper;
    private final Gson gson;
    private final Path path = Path.of("src", "main", "resources", "files", "json", "towns.json");
    private final Validator validator;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ModelMapper mapper, Gson gson, Validator validator) {
        this.townRepository = townRepository;
        this.mapper = mapper;
        this.gson = gson;
        this.validator = validator;
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

        for (TownsImportDto t: dtos) {
            Set<ConstraintViolation<TownsImportDto>> validate =
                    validator.validate(t);

            if(validate.isEmpty()){
                Town o = (Town)this.mapper.map(t, Town.class);
                this.townRepository.save(o);
                result.add("Successfully imported Town " + o.getName() + " - " + o.getPopulation());

            } else {
                result.add("Invalid Town");
            }
        }

        return String.join("\n", result);
    }
}
