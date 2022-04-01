package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.CarsImportDto;
import softuni.exam.models.dtos.PicturesImportDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Picture;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
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
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final CarRepository carRepository;
    private final ModelMapper mapper;
    private final Validator validator;
    private final Gson gson;
    private final Path path = Path.of("src", "main", "resources", "files", "json", "pictures.json");

    public PictureServiceImpl(PictureRepository pictureRepository, CarRepository carRepository, ModelMapper mapper, Validator validator, Gson gson) {
        this.pictureRepository = pictureRepository;
        this.carRepository = carRepository;
        this.mapper = mapper;
        this.validator = validator;
        this.gson = gson;
        this.mapper.addConverter(e -> LocalDateTime.parse(e.getSource(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), String.class, LocalDateTime.class);
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importPictures() throws IOException {
        List<String> result = new ArrayList<>();
        PicturesImportDto[] dtos = this.gson.fromJson(readPicturesFromFile(), PicturesImportDto[].class);

        for (PicturesImportDto c: dtos) {
            Set<ConstraintViolation<PicturesImportDto>> validate = this.validator.validate(c);

            if(validate.isEmpty()){
                Picture pic = this.mapper.map(c, Picture.class);
                Optional<Picture> byName = this.pictureRepository.findByName(pic.getName());

                if(byName.isEmpty()) {
                    Optional<Car> byId = this.carRepository.findById(c.getCar());

                    if(byId.isPresent()) {
                        pic.setCar(byId.get());
                        this.pictureRepository.save(pic);
                        result.add("Successfully imported picture - " + pic.getName());
                    }
                    else{
                        result.add("Invalid picture");
                    }
                }
                else {
                    result.add("Invalid picture");
                }
            }
            else {
                result.add("Invalid picture");
            }
        }

        return String.join("\n", result);
    }
}
