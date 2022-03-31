package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneTicketDto {
    @XmlElement(name = "register-number")
    private String registerNumber;

    public PlaneTicketDto() {
    }

    public String getRegisterNumber() {
        return registerNumber;
    }
}
