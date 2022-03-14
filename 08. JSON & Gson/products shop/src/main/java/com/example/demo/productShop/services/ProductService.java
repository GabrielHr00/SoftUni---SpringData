package com.example.demo.productShop.services;

import com.example.demo.productShop.entities.ProductWithoutBuyerDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<ProductWithoutBuyerDTO> getProductsInPriceRangeForSell(float from, float to);

}
