package com.example.football.service.impl;

import com.example.football.models.dto.ImportSDTO;
import com.example.football.models.dto.ImportStatDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StatServiceImpl implements StatService {
    private final Path path = Path.of("skeleton", "src", "main", "resources", "files", "xml", "stats.xml");
    private final StatRepository statRepository;
    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private ModelMapper mapper;

    @Autowired
    public StatServiceImpl(StatRepository statRepository) throws JAXBException {
        this.statRepository = statRepository;
        JAXBContext context = JAXBContext.newInstance(ImportStatDTO.class);
        this.unmarshaller = context.createUnmarshaller();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.mapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(this.path);
    }

    @Override
    public String importStats() throws IOException, JAXBException {
        ImportStatDTO unmarshal = (ImportStatDTO) this.unmarshaller.unmarshal(this.path.toAbsolutePath().toFile());
        List<ImportSDTO> stats = unmarshal.getStats();

        List<String> result = new ArrayList<>();

        for (ImportSDTO s : stats) {
            Set<ConstraintViolation<ImportSDTO>> validate = this.validator.validate(s);

            if(validate.isEmpty()){
                Optional<Stat> byShootingAndPassingAndEndurance = this.statRepository.findByShootingAndPassingAndEndurance(s.getShooting(), s.getPassing(), s.getEndurance());
                if(byShootingAndPassingAndEndurance.isPresent()){
                    result.add("Invalid Stat");
                }
                else{
                    Stat map = this.mapper.map(s, Stat.class);
                    this.statRepository.save(map);
                    String format = String.format("Successfully imported Stat %.2f - %.2f - %.2f", map.getPassing(), map.getShooting(), map.getEndurance());
                    result.add(format);
                }

            } else{
                result.add("Invalid Stat");
            }
        }
        return String.join("\n", result);
    }
}
