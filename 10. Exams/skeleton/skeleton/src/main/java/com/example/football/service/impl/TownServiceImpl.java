package com.example.football.service.impl;

import com.example.football.models.dto.TownImportDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private ModelMapper mapper;
    private Gson gson;


    @Autowired
    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;
        this.mapper = new ModelMapper();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        Path path = Path.of("src", "main",  "resources", "files",  "json", "towns.json");
        List<String> strings = Files.readAllLines(path);
        return String.join("\n", strings);
    }

    @Override
    public String importTowns() throws IOException {
        TownImportDTO[] townImportDTO = gson.fromJson(this.readTownsFileContent(), TownImportDTO[].class);

        List<String> result = new ArrayList<>();
        for (var i : townImportDTO) {
            if(!i.isValid()){
                result.add("Invalid Town");
            }

            Optional<Town> byName = this.townRepository.findByName(i.getName());
            if(byName.isPresent()){
                result.add("Invalid Town");
            }
            else {
                Town town = mapper.map(i, Town.class);
                this.townRepository.save(town);
                result.add("Successfully imported Town " + town.getName() + " - " + town.getPopulation());
            }
        }

        return String.join("\n", result);
    }
}
