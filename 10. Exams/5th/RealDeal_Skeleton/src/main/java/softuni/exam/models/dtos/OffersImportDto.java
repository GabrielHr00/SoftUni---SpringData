package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "offers")
public class OffersImportDto {
    @XmlElement(name = "offer")
    private List<OfferImportDto> offers;

    public OffersImportDto() {
    }

    public List<OfferImportDto> getOffers() {
        return offers;
    }
}
