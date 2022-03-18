package com.example.demo.productShop.entities;

import javax.xml.bind.annotation.*;

@XmlRootElement(name ="user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserNameDTO {
    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlAttribute
    private int age;

    public UserNameDTO() {}

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }
}
