package exam.model.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "towns")
public class TownsImportDto {

    @XmlElement(name = "town")
    private List<TownImportDto> towns;

    public TownsImportDto() {
    }

    public List<TownImportDto> getTowns() {
        return towns;
    }
}
