package exam.service.impl;

import exam.model.dtos.ShopImportDto;
import exam.model.dtos.ShopsImportDto;
import exam.model.entities.Shop;
import exam.model.entities.Town;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
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
public class ShopServiceImpl implements ShopService {
    private final Path path = Path.of( "src", "main", "resources", "files", "xml", "shops.xml");
    private final ShopRepository shopRepository;
    private final TownRepository townRepository;
    private final Validator validator;
    private final ModelMapper mapper;

    public ShopServiceImpl(ShopRepository shopRepository, TownRepository townRepository) {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.mapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(ShopsImportDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ShopsImportDto dtos = (ShopsImportDto) unmarshaller.unmarshal(path.toFile());


        List<String> result = new ArrayList<>();
        for (ShopImportDto t: dtos.getShops()) {
            Set<ConstraintViolation<ShopImportDto>> validate =
                    validator.validate(t);

            if(validate.isEmpty()){
                Optional<Shop> byName = this.shopRepository.findByName(t.getName());
                if(byName.isPresent()){
                    result.add("Invalid Shop");
                } else{
                    Optional<Town> byName1 = this.townRepository.findByName(t.getTown().getName());

                    Shop shop = mapper.map(t, Shop.class);
                    shop.setTown(byName1.get());

                    this.shopRepository.save(shop);
                    result.add("Successfully imported Shop " + shop.getName() + " - " + shop.getIncome());
                }
            } else{
                result.add("Invalid Shop");
            }
        }

        return String.join("\n", result);

    }
}
