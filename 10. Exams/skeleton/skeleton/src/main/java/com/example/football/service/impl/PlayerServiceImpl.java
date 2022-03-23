package com.example.football.service.impl;

import com.example.football.config.ApplicationBeanConfiguration;
import com.example.football.models.dto.ImportPlayerDTO;
import com.example.football.models.dto.ImportPlayersDTO;
import com.example.football.models.dto.ImportStatDTO;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final StatRepository statRepository;
    private final Path path = Path.of("skeleton", "src", "main", "resources", "files", "xml", "players.xml");
    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private ModelMapper mapper;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository, TownRepository townRepository, StatRepository statRepository) throws JAXBException {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.statRepository = statRepository;
        JAXBContext context = JAXBContext.newInstance(ImportPlayersDTO.class);
        this.unmarshaller = context.createUnmarshaller();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.mapper = new ModelMapper();
        this.mapper.addConverter(e -> LocalDate.parse(e.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy")), String.class, LocalDate.class);
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(this.path);
    }

    @Override
    public String importPlayers() throws JAXBException {
        ImportPlayersDTO unmarshal = (ImportPlayersDTO)unmarshaller.unmarshal(this.path.toAbsolutePath().toFile());
        List<ImportPlayerDTO> players = unmarshal.getPlayers();

        List<String> result = new ArrayList<>();
        for (ImportPlayerDTO p : players) {
            Set<ConstraintViolation<ImportPlayerDTO>> validate =
                    this.validator.validate(p);

            if(validate.isEmpty()){
                Optional<Player> findBy = this.playerRepository.findByEmail(p.getEmail());
                if(findBy.isPresent()){
                    result.add("Invalid Player");
                } else{
                    Player player = this.mapper.map(p, Player.class);
                    Optional<Team> team = this.teamRepository.findByName(p.getTeam().getName());
                    Optional<Stat> stat = this.statRepository.findById(p.getStat().getId());
                    Optional<Town> town = this.townRepository.findByName(p.getTown().getName());

                    player.setStat(stat.get());
                    player.setTeam(team.get());
                    player.setTown(town.get());

                    this.playerRepository.save(player);
                    result.add("Successfully imported Player " + player.getFirstName() + " " + player.getLastName() + " - " + player.getPosition().name());
                }
            } else{
                result.add("Invalid Player");
            }
        }

        return String.join("\n", result);
    }

    @Override
    public String exportBestPlayers() {
        return null;
    }
}
