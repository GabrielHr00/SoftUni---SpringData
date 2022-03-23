package com.example.football.models.dto;

import com.example.football.models.entity.PlayerPosition;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportPlayerDTO {

    @XmlElement(name = "first-name")
    @Size(min = 3)
    private String firstName;

    @XmlElement(name = "last-name")
    @Size(min = 3)
    private String lastName;

    @XmlElement
    @Email
    private String email;

    @XmlElement(name = "birth-date")
    private String birthDate;

    @XmlElement
    private PlayerPosition position;

    @XmlElement(name = "town")
    private TownPlayerDTO town;

    @XmlElement(name = "team")
    private TeamPlayerDTO team;

    @XmlElement(name = "stat")
    private StatPlayerDTO stat;

    public ImportPlayerDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
    }

    public TownPlayerDTO getTown() {
        return town;
    }

    public void setTown(TownPlayerDTO town) {
        this.town = town;
    }

    public TeamPlayerDTO getTeam() {
        return team;
    }

    public void setTeam(TeamPlayerDTO team) {
        this.team = team;
    }

    public StatPlayerDTO getStat() {
        return stat;
    }

    public void setStat(StatPlayerDTO stat) {
        this.stat = stat;
    }
}
