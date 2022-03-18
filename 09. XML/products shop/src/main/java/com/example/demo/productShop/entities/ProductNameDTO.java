package com.example.demo.productShop.entities;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "product")
public class ProductNameDTO {
    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "price")
    private BigDecimal price;

    public ProductNameDTO() {}

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
