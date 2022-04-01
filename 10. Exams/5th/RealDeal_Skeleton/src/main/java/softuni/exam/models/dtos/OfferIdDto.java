package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OfferIdDto {
    @XmlElement(name = "id")
    private long id;

    public OfferIdDto() {
    }

    public long getId() {
        return id;
    }
}
