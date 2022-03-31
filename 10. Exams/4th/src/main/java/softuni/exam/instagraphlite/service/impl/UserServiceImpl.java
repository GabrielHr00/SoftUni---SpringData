package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.ImportUsersDto;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.models.entities.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final ModelMapper mapper;
    private final Validator validator;
    private final Gson gson;
    private final Path path = Path.of("src", "main", "resources", "files", "users.json");


    public UserServiceImpl(UserRepository userRepository, PictureRepository pictureRepository) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }


    @Override
    public boolean areImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importUsers() throws IOException {
        ImportUsersDto[] dtos = (ImportUsersDto[]) gson.fromJson(readFromFileContent(), ImportUsersDto[].class);

        List<String> result = new ArrayList<>();
        for (ImportUsersDto p : dtos) {
            Set<ConstraintViolation<ImportUsersDto>> validate = validator.validate(p);

            if(validate.isEmpty()){
                System.out.println(p.getProfilePicture());
                Optional<Picture> byPath = this.pictureRepository.findByPath(p.getProfilePicture().getPath());
                if(byPath.isEmpty()) {
                    result.add("Invalid User");
                } else {
                    User user = mapper.map(p, User.class);
                    this.userRepository.save(user);
                    result.add(String.format("Successfully imported User: %s", user.getUsername()));
                }
            } else{
                System.out.println(p.getProfilePicture().getPath());
                result.add("Invalid User");
            }
        }

        return String.join("\n", result);
    }

    @Override
    public String exportUsersWithTheirPosts() {
        return null;
    }
}
