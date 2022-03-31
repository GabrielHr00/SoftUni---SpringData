package softuni.exam.instagraphlite.models.dtos;

import softuni.exam.instagraphlite.models.entities.Picture;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ImportUsersDto {
    @NotNull
    @Size(min = 2, max = 18)
    private String username;

    @NotNull
    @Size(min = 4)
    private String password;

    private UserPictureDto picture;

    public ImportUsersDto() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserPictureDto getProfilePicture() {
        return picture;
    }
}
