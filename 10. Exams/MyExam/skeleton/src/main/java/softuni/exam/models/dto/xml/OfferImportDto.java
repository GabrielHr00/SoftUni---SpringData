package softuni.exam.models.dto.xml;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "offer")
public class OfferImportDto {
    @XmlElement
    @Positive
    private BigDecimal price;

    @XmlElement(name = "agent")
    private AgentOfferDto agent;

    @XmlElement(name = "apartment")
    private ApartmentOfferDto apartment;

    @XmlElement(name = "publishedOn")
    private String publishedOn;

    public OfferImportDto() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AgentOfferDto getAgent() {
        return agent;
    }

    public ApartmentOfferDto getApartment() {
        return apartment;
    }

    public String getPublishedOn() {
        return publishedOn;
    }
}
