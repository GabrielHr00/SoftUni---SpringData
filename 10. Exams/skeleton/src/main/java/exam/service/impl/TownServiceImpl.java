package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dtos.TownImportDto;
import exam.model.dtos.TownsImportDto;
import exam.model.entities.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TownServiceImpl implements TownService {
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "towns.xml");
    private final TownRepository townRepository;
    private final Validator validator;
    private final ModelMapper mapper;

    public TownServiceImpl(TownRepository townRepository) throws JAXBException {
        this.townRepository = townRepository;
        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
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
    public String importTowns() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(TownsImportDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        TownsImportDto dtos = (TownsImportDto) unmarshaller.unmarshal(path.toFile());

        List<String> result = new ArrayList<>();
        for (TownImportDto t: dtos.getTowns()) {
            Set<ConstraintViolation<TownImportDto>> validate =
                    validator.validate(t);

            if(validate.isEmpty()){
                Optional<Town> byName = this.townRepository.findByName(t.getName());
                if(byName.isPresent()){
                    result.add("Invalid Town");
                } else{
                    Town town = mapper.map(t, Town.class);
                    this.townRepository.save(town);
                    result.add("Successfully imported Town " + town.getName());
                }
            } else{
                result.add("Invalid Town");
            }
        }

        return String.join("\n", result);
    }
}
