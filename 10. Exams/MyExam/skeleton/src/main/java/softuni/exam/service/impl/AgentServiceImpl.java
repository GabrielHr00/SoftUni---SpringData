package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.AgentsImportDto;
import softuni.exam.models.dto.json.TownsImportDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AgentServiceImpl implements AgentService {
    private final AgentRepository agentRepository;
    private final TownRepository townRepository;
    private final ModelMapper mapper;
    private final Validator validator;
    private final Gson gson;
    private final Path path = Path.of("src", "main", "resources", "files", "json", "agents.json");

    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }


    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importAgents() throws IOException {
        List<String> result = new ArrayList<>();
        AgentsImportDto[] dtos = this.gson.fromJson(readAgentsFromFile(), AgentsImportDto[].class);

        for (AgentsImportDto t : dtos) {
            Set<ConstraintViolation<AgentsImportDto>> validate = this.validator.validate(t);

            if(validate.isEmpty()) {
                Optional<Agent> byName = this.agentRepository.findByFirstName(t.getFirstName());

                if(byName.isPresent()) {
                    result.add("Invalid agent");
                }
                else{
                    Optional<Town> byTownName = this.townRepository.findByTownName(t.getTown());

                    if(byTownName.isEmpty()) {
                        result.add("Invalid agent");
                    }
                    else {
                        Agent agent = this.mapper.map(t, Agent.class);
                        agent.setTown(byTownName.get());
                        this.agentRepository.save(agent);
                        result.add("Successfully imported agent " + agent.getFirstName() + " " + agent.getLastName());
                    }
                }
            }
            else {
                result.add("Invalid agent");
            }
        }

        return String.join("\n", result);
    }
}
