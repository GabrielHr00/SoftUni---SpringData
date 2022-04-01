package softuni.exam.models.dtos;

import javax.validation.constraints.Size;

public class PicturesImportDto {
    @Size(min = 3, max = 19)
    private String name;

    private String dateAndTime;

    private long car;

    public PicturesImportDto() {
    }

    public String getName() {
        return name;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public long getCar() {
        return car;
    }
}
