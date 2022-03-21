package com.example.football.models.dto;

import javax.persistence.Column;
import java.util.List;

public class TownImportDTO {
    private String name;

    private int population;

    private String travelGuide;

    public TownImportDTO() {
    }

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
