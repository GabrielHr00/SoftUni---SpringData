package softuni.exam.models.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentOfferDto {
    @XmlElement(name = "id")
    private long id;

    public ApartmentOfferDto() {
    }

    public long getId() {
        return id;
    }
}
