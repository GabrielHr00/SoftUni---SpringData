package softuni.exam.models.dto.xml;

import softuni.exam.models.entity.Offer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "offers")
public class OffersRootDto {
    @XmlElement(name = "offer")
    private List<OfferImportDto> offers;

    public OffersRootDto() {
    }

    public List<OfferImportDto> getOffers() {
        return offers;
    }
}
