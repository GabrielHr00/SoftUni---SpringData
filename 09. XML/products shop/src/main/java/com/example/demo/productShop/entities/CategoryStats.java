package com.example.demo.productShop.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryStats {
    @XmlElement(name = "category")
    private List<CategoryPropsDTO> categories;

    public CategoryStats() {}

    public CategoryStats(List<CategoryPropsDTO> categories) {
        this.categories = categories;
    }

    public List<CategoryPropsDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryPropsDTO> categories) {
        this.categories = categories;
    }
}
