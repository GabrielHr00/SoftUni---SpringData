package com.example.demo.productShop.services;

import com.example.demo.productShop.entities.*;
import com.example.demo.productShop.repositories.ProductsRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductsRepository productsRepository;
    private final ModelMapper mapper;
    private final TypeMap<Product, ProductWithAttributesDTO> productToMapping;

    public ProductServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
        this.mapper = new ModelMapper();
        Converter<User, String> userToFullNameConverter = mappingContext -> mappingContext.getSource() == null ? null : mappingContext.getSource().getFullName();
        TypeMap<Product, ProductWithAttributesDTO> typeMap =
                this.mapper.createTypeMap(Product.class, ProductWithAttributesDTO.class);

        this.productToMapping = typeMap.addMappings(m ->
                m.using(userToFullNameConverter).map(Product::getSeller, ProductWithAttributesDTO::setSeller));
        this.mapper.addConverter(userToFullNameConverter);
    }

    @Override
    public ProductsInRangeDTO productsInRange(float from, float to) {
        BigDecimal from1 = new BigDecimal(from);
        BigDecimal to1 = new BigDecimal(to);

        List<Product> allByPriceBetweenAndBuyerIsNullOrderByPriceAsc = this.productsRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(from1, to1);

        List<ProductWithAttributesDTO> dtos = allByPriceBetweenAndBuyerIsNullOrderByPriceAsc
                .stream()
                .map(this.productToMapping::map)
                .collect(Collectors.toList());
        return new ProductsInRangeDTO(dtos);
    }

    @Override
    public CategoryStats categoryStatsWithCount() {
        List<CategoryPropsDTO> categoryStats = this.productsRepository.getCategoryStats();
        CategoryStats dtos = new CategoryStats(categoryStats);
        return dtos;
    }
}
