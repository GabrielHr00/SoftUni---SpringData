package com.example.demo.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name="games")
public class Game {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(name = "trailer_id", nullable = false)
    private String trailerId;

    @Column(name = "thumbnail_url", nullable = false)
    private String imageURL;

    private float size;

    @Column(nullable = false)
    private BigDecimal price;

    private String description;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    public Game() {}

    public Game(String title, String trailer, String imageURL, float size, BigDecimal price, String description, LocalDate releaseDate) {
        this.title = title;
        this.trailerId = trailer;
        this.imageURL = imageURL;
        this.size = size;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailer() {
        return trailerId;
    }

    public void setTrailer(String trailer) {
        this.trailerId = trailer;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
