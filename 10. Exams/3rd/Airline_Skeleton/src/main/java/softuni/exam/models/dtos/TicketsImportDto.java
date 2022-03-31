package softuni.exam.models.dtos;

import softuni.exam.models.entities.Ticket;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tickets")
public class TicketsImportDto {
    @XmlElement(name = "ticket")
    private List<TicketImportDto> tickets;

    public TicketsImportDto() {
    }

    public List<TicketImportDto> getTickets() {
        return tickets;
    }
}
