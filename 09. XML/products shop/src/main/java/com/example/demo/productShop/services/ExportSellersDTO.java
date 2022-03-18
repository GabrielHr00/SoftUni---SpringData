package com.example.demo.productShop.services;

import com.example.demo.productShop.entities.ExportUserWithSoldProductsDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportSellersDTO {

    @XmlElement(name = "user")
    List<ExportUserWithSoldProductsDTO> users;

    public ExportSellersDTO() {}

    public ExportSellersDTO(List<ExportUserWithSoldProductsDTO> users) {
        this.users = users;
    }

    public List<ExportUserWithSoldProductsDTO> getUsers() {
        return users;
    }

    public void setUsers(List<ExportUserWithSoldProductsDTO> users) {
        this.users = users;
    }
}
