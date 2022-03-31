package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TownTicketDto {
    @XmlElement(name = "name")
    private String name;

    public TownTicketDto() {
    }

    public String getName() {
        return name;
    }
}
