package com.example.football.models.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TownImportDTO {
    @Size(min = 2)
    private String name;

    @Positive
    private int population;

    @Size(min = 10)
    private String travelGuide;

    public TownImportDTO() {}

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public String getTravelGuide() {
        return travelGuide;
    }

    public boolean isValid(){
        if(name.length() < 2){
            return false;
        }

        if(population < 0){
            return false;
        }

        if(travelGuide.length() < 10){
            return false;
        }

        return true;
    }

}
