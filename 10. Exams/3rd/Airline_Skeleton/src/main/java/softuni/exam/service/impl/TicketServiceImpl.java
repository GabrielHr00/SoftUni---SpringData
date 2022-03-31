package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.TicketImportDto;
import softuni.exam.models.dtos.TicketsImportDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Ticket;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final PlaneRepository planeRepository;
    private final PassengerRepository passengerRepository;
    private final TownRepository townRepository;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final Unmarshaller unmarshaller;
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "tickets.xml");

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, PassengerRepository passengerRepository, PlaneRepository planeRepository, TownRepository townRepository, Validator validator, ModelMapper modelMapper) throws JAXBException {
        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
        this.planeRepository = planeRepository;
        this.townRepository = townRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.modelMapper.addConverter(e -> LocalDateTime.parse(e.getSource(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), String.class, LocalDateTime.class);
        JAXBContext context = JAXBContext.newInstance(TicketsImportDto.class);
        this.unmarshaller = context.createUnmarshaller();
    }

    @Override
    public boolean areImported() {
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importTickets() throws JAXBException {
        List<String> result = new ArrayList<>();

        TicketsImportDto dtos = (TicketsImportDto) unmarshaller.unmarshal(path.toFile());

        for (TicketImportDto p: dtos.getTickets()) {
            Set<ConstraintViolation<TicketImportDto>> validate = validator.validate(p);

            if(validate.isEmpty()) {
                Ticket ticket = new Ticket();

                BigDecimal price = p.getPrice();
                LocalDateTime takeoff = modelMapper.map(p.getTakeoff(), LocalDateTime.class);
                String serialNumber = p.getSerialNumber();
                String fromTown = p.getFromTown().getName();
                System.out.println(fromTown);
                String toTown = p.getToTown().getName();
                String passenger = p.getPassenger().getEmail();
                String plane = p.getPlane().getRegisterNumber();

                Optional<Town> from = this.townRepository.findByName(fromTown);
                Optional<Town> to = this.townRepository.findByName(toTown);
                Optional<Passenger> pass = this.passengerRepository.findByEmail(passenger);
                Optional<Plane> pla = this.planeRepository.findByRegisterNumber(plane);


                //Ticket ticket = this.modelMapper.map(p, Ticket.class);
                ticket.setPrice(price);
                ticket.setTakeoff(takeoff);
                ticket.setSerialNumber(serialNumber);
                if(from.isEmpty() || to.isEmpty() || pass.isEmpty() || pla.isEmpty()) {
                    result.add("Invalid Ticket");
                }
                else {
                    ticket.setFromTown(from.get());
                    ticket.setToTown(to.get());
                    ticket.setPassenger(pass.get());
                    ticket.setPlane(pla.get());
                    this.ticketRepository.save(ticket);
                    result.add("Successfully imported Ticket " + ticket.getToTown().getName() + " - " + ticket.getFromTown().getName());

                }
            }
            else {
                result.add("Invalid Ticket");
            }
        }

        return String.join("\n", result);
    }
}
