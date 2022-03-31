package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "planes")
public class PlanesImportDto {
    @XmlElement(name = "plane")
    List<PlaneImportDto> planes;

    public PlanesImportDto() {
    }

    public List<PlaneImportDto> getPlanes() {
        return planes;
    }
}
