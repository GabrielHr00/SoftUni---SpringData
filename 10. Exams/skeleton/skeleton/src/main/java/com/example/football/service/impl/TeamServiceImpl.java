package com.example.football.service.impl;

import com.example.football.models.dto.ImportTeamsDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper mapper;
    private Validator validator;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.mapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        Path path = Path.of("skeleton", "src", "main", "resources", "files", "json", "teams.json");
        return Files.readString(path);
    }

    @Override
    public String importTeams() throws IOException {
        ImportTeamsDTO[] importTeamsDTOS = gson.fromJson(this.readTeamsFileContent(), ImportTeamsDTO[].class);

        List<String> list = new ArrayList<>();
        for (ImportTeamsDTO dto: importTeamsDTOS) {
            Set<ConstraintViolation<ImportTeamsDTO>> validate =
                    this.validator.validate(dto);

            if(validate.isEmpty()){
                Optional<Team> byName = this.teamRepository.findByName(dto.getName());
                if(byName.isPresent()){
                    list.add("Invalid Team");
                } else{
                    Optional<Town> town = this.townRepository.findByName(dto.getTownName());
                    Team team = this.mapper.map(dto, Team.class);
                    team.setTown(town.get());
                    this.teamRepository.save(team);
                    list.add("Successfully imported Team " + team.getName() + " - " + team.getFanBase());
                }
            } else{
                list.add("Invalid Team");
            }
        }
        return String.join("\n", list);
    }
}
