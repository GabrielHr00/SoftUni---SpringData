package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.SellerImportDto;
import softuni.exam.models.dtos.SellersImportDto;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;

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
import java.util.Optional;
import java.util.Set;


@Service
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final ModelMapper mapper;
    private final Validator validator;
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "sellers.xml");


    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper mapper, Validator validator) {
        this.sellerRepository = sellerRepository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        List<String> result = new ArrayList<>();
        JAXBContext context = JAXBContext.newInstance(SellersImportDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        SellersImportDto dtos = (SellersImportDto)unmarshaller.unmarshal(path.toFile());
        for (SellerImportDto s : dtos.getSellers()) {
            Set<ConstraintViolation<SellerImportDto>> validate = validator.validate(s);

            if(validate.isEmpty()) {
                Seller seller = this.mapper.map(s, Seller.class);
                Optional<Seller> byEmail = this.sellerRepository.findByEmail(seller.getEmail());

                if(byEmail.isEmpty()) {
                    this.sellerRepository.save(seller);
                    result.add("Successfully import seller " + seller.getLastName() + " - " + seller.getEmail());

                } else {
                    result.add("Invalid seller");
                }
            }
            else {
                result.add("Invalid seller");
            }

        }

        return String.join("\n", result);
    }
}
