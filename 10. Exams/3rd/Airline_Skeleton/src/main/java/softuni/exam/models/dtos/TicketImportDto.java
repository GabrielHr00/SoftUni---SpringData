package softuni.exam.models.dtos;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ticket")
public class TicketImportDto {
    @XmlElement(name = "serial-number")
    @Size(min = 2)
    private String serialNumber;

    @XmlElement
    @Positive
    private BigDecimal price;

    @XmlElement(name = "take-off")
    private String takeoff;

    @XmlElement(name = "to-town")
    private TownTicketDto toTown;

    @XmlElement(name = "from-town")
    private TownTicketDto fromTown;

    @XmlElement(name = "passenger")
    private PassengerTicketDto passenger;

    @XmlElement(name = "plane")
    private PlaneTicketDto plane;

    public TicketImportDto() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getTakeoff() {
        return takeoff;
    }

    public TownTicketDto getToTown() {
        return toTown;
    }

    public TownTicketDto getFromTown() {
        return fromTown;
    }

    public PassengerTicketDto getPassenger() {
        return passenger;
    }

    public PlaneTicketDto getPlane() {
        return plane;
    }
}
