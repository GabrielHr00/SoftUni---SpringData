package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.ApartmentImportDto;
import softuni.exam.models.dto.xml.ApartmentsRootDto;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;
    private final ModelMapper mapper;
    private final Validator validator;
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "apartments.xml");

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        List<String> result = new ArrayList<>();
        JAXBContext context = JAXBContext.newInstance(ApartmentsRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ApartmentsRootDto dtos = (ApartmentsRootDto) unmarshaller.unmarshal(path.toFile());
        for (ApartmentImportDto a : dtos.getApartments()) {
            Set<ConstraintViolation<ApartmentImportDto>> validate = this.validator.validate(a);

            if(validate.isEmpty()) {
                Optional<Town> byTownName = this.townRepository.findByTownName(a.getTown());
                if(byTownName.isEmpty()){
                    result.add("Invalid apartment");
                }
                else {
                    Optional<Apartment> byTownNameAndArea = this.apartmentRepository.findAreaAndTownName(a.getArea(), a.getTown());
                    if(byTownNameAndArea.isEmpty()) {
                        Apartment apartment = this.mapper.map(a, Apartment.class);
                        apartment.setTown(byTownName.get());
                        this.apartmentRepository.save(apartment);
                        result.add(String.format("Successfully imported apartment %s - %.2f", apartment.getApartmentType().name(), apartment.getArea()));
                    }
                    else{
                        result.add("Invalid apartment");
                    }
                }
            }
            else {
                result.add("Invalid apartment");
            }
        }

        return String.join("\n", result);
    }
}
