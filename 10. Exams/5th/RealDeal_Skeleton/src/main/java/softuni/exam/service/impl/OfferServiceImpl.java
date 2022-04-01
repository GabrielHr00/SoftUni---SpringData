package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.OfferImportDto;
import softuni.exam.models.dtos.OffersImportDto;
import softuni.exam.models.dtos.SellerImportDto;
import softuni.exam.models.dtos.SellersImportDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Offer;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.OfferService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final CarRepository carRepository;
    private final SellerRepository sellerRepository;
    private final ModelMapper mapper;
    private final Validator validator;
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "offers.xml");

    public OfferServiceImpl(OfferRepository offerRepository, CarRepository carRepository, SellerRepository sellerRepository, ModelMapper mapper, Validator validator) {
        this.offerRepository = offerRepository;
        this.carRepository = carRepository;
        this.sellerRepository = sellerRepository;
        this.mapper = mapper;
        this.validator = validator;
        this.mapper.addConverter(e -> LocalDateTime.parse(e.getSource(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), String.class, LocalDateTime.class);
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
        JAXBContext context = JAXBContext.newInstance(OffersImportDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        OffersImportDto dtos = (OffersImportDto)unmarshaller.unmarshal(path.toFile());
        for (OfferImportDto o : dtos.getOffers()) {
            Set<ConstraintViolation<OfferImportDto>> validate = validator.validate(o);

            if(validate.isEmpty()) {
                Offer offer = this.mapper.map(o, Offer.class);

                Optional<Seller> sell = this.sellerRepository.findById(o.getSeller().getId());
                Optional<Car> car = this.carRepository.findById(o.getCar().getId());

                if(sell.isPresent() && car.isPresent()) {
                    offer.setCar(car.get());
                    offer.setSeller(sell.get());
                    this.offerRepository.save(offer);
                    result.add("Successfully import offer " + offer.getAddedOn().toString() + " - " + offer.isHasGoldStatus());

                } else {
                    result.add("Invalid offer");
                }
            }
            else {
                result.add("Invalid offer");
            }

        }

        return String.join("\n", result);
    }
}
