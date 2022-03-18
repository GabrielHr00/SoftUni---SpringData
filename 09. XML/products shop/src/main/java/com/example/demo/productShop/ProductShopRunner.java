package com.example.demo.productShop;

import com.example.demo.productShop.entities.ProductsInRangeDTO;
import com.example.demo.productShop.services.ProductService;
import com.example.demo.productShop.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

@Component
public class ProductShopRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final ProductService productService;

    @Autowired
    public ProductShopRunner(SeedService seedService, ProductService productService) {
        this.seedService = seedService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        JAXBContext context = JAXBContext.newInstance(ProductsInRangeDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        ProductsInRangeDTO productsInRangeDTO = this.productService.productsInRange(500, 1000);
        marshaller.marshal(productsInRangeDTO, System.out);

    }

}
