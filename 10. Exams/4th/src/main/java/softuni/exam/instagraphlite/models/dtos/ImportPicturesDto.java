package softuni.exam.instagraphlite.models.dtos;

import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ImportPicturesDto {
    @NotNull
    @UniqueElements
    private String path;

    @Min(500)
    @Max(60000)
    @NotNull
    private float size;

    public ImportPicturesDto() {
    }

    public String getPath() {
        return path;
    }

    public float getSize() {
        return size;
    }
}
