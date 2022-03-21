package com.example.football.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "towns")
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int population;

    @Column(name = "travel_guide", nullable = false, columnDefinition = "TEXT")
    private String traverGuide;

    public Town() {
    }

    public Town(String name, int population, String traverGuide) {
        this.name = name;
        this.population = population;
        this.traverGuide = traverGuide;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getTraverGuide() {
        return traverGuide;
    }

    public void setTraverGuide(String traverGuide) {
        this.traverGuide = traverGuide;
    }
}
