package com.example.football.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TeamPlayerDTO {
    @XmlElement
    private String name;

    public TeamPlayerDTO() {
    }

    public String getName() {
        return name;
    }
}
