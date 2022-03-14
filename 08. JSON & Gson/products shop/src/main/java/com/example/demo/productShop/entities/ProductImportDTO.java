package com.example.demo.productShop.entities;

import java.math.BigDecimal;

public class ProductImportDTO {
    private String name;
    private BigDecimal price;

    public ProductImportDTO(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
