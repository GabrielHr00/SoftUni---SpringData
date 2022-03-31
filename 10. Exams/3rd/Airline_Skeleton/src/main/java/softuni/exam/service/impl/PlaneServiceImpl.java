package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.PlaneImportDto;
import softuni.exam.models.dtos.PlanesImportDto;
import softuni.exam.models.entities.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "planes.xml");

    @Autowired
    public PlaneServiceImpl(PlaneRepository planeRepository, Validator validator, ModelMapper modelMapper) {
        this.planeRepository = planeRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importPlanes() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(PlanesImportDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        List<String> result = new ArrayList<>();

        PlanesImportDto dtos = (PlanesImportDto) unmarshaller.unmarshal(path.toFile());
        for (PlaneImportDto p: dtos.getPlanes()) {
            Set<ConstraintViolation<PlaneImportDto>> validate = validator.validate(p);

            if(validate.isEmpty()) {
                Plane plane = this.modelMapper.map(p, Plane.class);
                this.planeRepository.save(plane);
                result.add("Successfully imported Plane " + plane.getRegisterNumber());
            }
            else {
                result.add("Invalid Plane");
            }
        }

        return String.join("\n", result);
    }
}
