package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.ImportPicturesDto;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final ModelMapper mapper;
    private final Validator validator;
    private final Gson gson;
    private final Path path = Path.of("src", "main", "resources", "files", "pictures.json");

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importPictures() throws IOException {
        ImportPicturesDto[] dtos = (ImportPicturesDto[]) gson.fromJson(readFromFileContent(), ImportPicturesDto[].class);

        List<String> result = new ArrayList<>();
        for (ImportPicturesDto p : dtos) {
            Set<ConstraintViolation<ImportPicturesDto>> validate = validator.validate(p);
            if(validate.isEmpty()){
                Picture picture = mapper.map(p, Picture.class);
                this.pictureRepository.save(picture);
                result.add(String.format("Successfully imported Picture, with size %.2f", picture.getSize()));

            } else{
                result.add("Invalid Picture");
            }
        }

        return String.join("\n", result);
    }

    @Override
    public String exportPictures() {
        return null;
    }
}
