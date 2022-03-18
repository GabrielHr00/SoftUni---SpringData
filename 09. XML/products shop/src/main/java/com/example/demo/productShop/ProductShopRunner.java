package com.example.demo.productShop;

import com.example.demo.productShop.entities.CategoryStats;
import com.example.demo.productShop.entities.ProductsInRangeDTO;
import com.example.demo.productShop.services.ExportSellersDTO;
import com.example.demo.productShop.services.ProductService;
import com.example.demo.productShop.services.SeedService;
import com.example.demo.productShop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

@Component
public class ProductShopRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public ProductShopRunner(SeedService seedService, ProductService productService, UserService userService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        JAXBContext context = JAXBContext.newInstance(CategoryStats.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

//        ProductsInRangeDTO productsInRangeDTO = this.productService.productsInRange(500, 1000);
//        marshaller.marshal(productsInRangeDTO, System.out);


//        ExportSellersDTO allWithSoldProducts = this.userService.findAllWithSoldProducts();
//        marshaller.marshal(allWithSoldProducts, System.out);

        CategoryStats categoryStats = this.productService.categoryStatsWithCount();
        marshaller.marshal(categoryStats, System.out);
    }

}
