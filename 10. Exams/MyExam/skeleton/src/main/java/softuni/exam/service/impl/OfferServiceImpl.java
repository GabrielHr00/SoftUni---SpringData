package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.ApartmentImportDto;
import softuni.exam.models.dto.xml.ApartmentsRootDto;
import softuni.exam.models.dto.xml.OfferImportDto;
import softuni.exam.models.dto.xml.OffersRootDto;
import softuni.exam.models.entity.*;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final AgentRepository agentRepository;
    private final ApartmentRepository apartmentRepository;
    private final ModelMapper mapper;
    private final Validator validator;
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "offers.xml");

    public OfferServiceImpl(OfferRepository offerRepository, AgentRepository agentRepository, ApartmentRepository apartmentRepository) {
        this.offerRepository = offerRepository;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.mapper.addConverter(e -> LocalDate.parse(e.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy")), String.class, LocalDate.class);
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        List<String> result = new ArrayList<>();
        JAXBContext context = JAXBContext.newInstance(OffersRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        OffersRootDto dtos = (OffersRootDto) unmarshaller.unmarshal(path.toFile());
        for (OfferImportDto a : dtos.getOffers()) {
            Set<ConstraintViolation<OfferImportDto>> validate = this.validator.validate(a);

            if(validate.isEmpty()) {
                Optional<Agent> byAgent = this.agentRepository.findByFirstName(a.getAgent().getName());
                if(byAgent.isEmpty()){
                    result.add("Invalid offer");
                }
                else {
                    Optional<Apartment> app = this.apartmentRepository.findById(a.getApartment().getId());

                    if(app.isPresent()) {
                        Offer offer = this.mapper.map(a, Offer.class);

                        offer.setAgent(byAgent.get());
                        offer.setApartment(app.get());

                        this.offerRepository.save(offer);
                        result.add(String.format("Successfully imported offer %.2f", offer.getPrice().doubleValue()));
                    }
                    else{
                        result.add("Invalid offer");
                    }
                }
            }
            else {
                result.add("Invalid offer");
            }
        }

        return String.join("\n", result);
    }

    @Override
    public String exportOffers() {
        List<Offer> allOffers = this.offerRepository.findAllByApartmentApartmentTypeOrderByApartmentAreaDescPriceAsc(ApartmentType.three_rooms);
        return allOffers.stream().map(e -> e.toString()).collect(Collectors.joining("\n"));
    }
}
