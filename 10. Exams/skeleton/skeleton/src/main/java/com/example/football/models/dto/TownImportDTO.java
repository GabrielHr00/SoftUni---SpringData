package com.example.football.models.dto;

import javax.persistence.Column;
import java.util.List;

public class TownImportDTO {
    private String name;

    private int population;

    @Column(name = "travel_guide")
    private String travelGuide;

    public TownImportDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getTravelGuide() {
        return travelGuide;
    }

    public void setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
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
