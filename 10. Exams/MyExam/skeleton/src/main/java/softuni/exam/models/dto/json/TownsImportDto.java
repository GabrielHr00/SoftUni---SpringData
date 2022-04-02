package softuni.exam.models.dto.json;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TownsImportDto {
    @Size(min = 2)
    private String townName;

    @Positive
    private int population;

    public TownsImportDto() {
    }

    public String getTownName() {
        return townName;
    }

    public int getPopulation() {
        return population;
    }
}
