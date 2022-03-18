package com.example.demo.productShop.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductImportDTO {
    @XmlElement(name = "product")
    private List<ProductNameDTO> products;

    public ProductImportDTO() {
    }

    public List<ProductNameDTO> getProducts() {
        return products;
    }
}
