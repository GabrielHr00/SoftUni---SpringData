package com.example.demo.productShop.services;

import com.example.demo.productShop.entities.*;
import com.example.demo.productShop.repositories.CategoryRepository;
import com.example.demo.productShop.repositories.ProductsRepository;
import com.example.demo.productShop.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService{
    private static final String USERS_XML = "/Users/mac/Desktop/Mac/github/SoftUni---SpringData/09. XML/products shop/src/main/resources/productShop/productShop/users.xml";
    private static final String CATEGORIES_XML = "/Users/mac/Desktop/Mac/github/SoftUni---SpringData/09. XML/products shop/src/main/resources/productShop/productShop/categories.xml";
    private static final String PRODUCTS_XML = "/Users/mac/Desktop/Mac/github/SoftUni---SpringData/09. XML/products shop/src/main/resources/productShop/productShop/products.xml";

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductsRepository productsRepository;

    private final ModelMapper mapper;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductsRepository productsRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productsRepository = productsRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public void seedUsers() throws FileNotFoundException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(UserImportDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        UserImportDTO unmarshal = (UserImportDTO) unmarshaller.unmarshal(new FileReader(USERS_XML));
        List<User> entities = unmarshal
                .getUsers()
                .stream()
                .map(dto -> this.mapper.map(dto, User.class))
                .collect(Collectors.toList());

        this.userRepository.saveAll(entities);
    }

    @Override
    public void seedCategories() throws FileNotFoundException, JAXBException{
        JAXBContext context = JAXBContext.newInstance(CategoryImportDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        CategoryImportDTO result = (CategoryImportDTO) unmarshaller.unmarshal(new FileReader(CATEGORIES_XML));
        List<Category> entities = result.
                getCategories()
                .stream()
                .map(e -> new Category(e.getName()))
                .collect(Collectors.toList());

        this.categoryRepository.saveAll(entities);
    }

    @Override
    public void seedProducts() throws FileNotFoundException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(ProductImportDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ProductImportDTO result = (ProductImportDTO) unmarshaller.unmarshal(new FileReader(PRODUCTS_XML));
        List<Product> entities = result.getProducts()
                .stream()
                .map(e -> new Product(e.getName(), e.getPrice()))
                .map(this::sendRandomCategories)
                .map(this::setRandomSeller)
                .map(this::setRandomBuyer)
                .collect(Collectors.toList());

        this.productsRepository.saveAll(entities);
    }

    private Product sendRandomCategories(Product product) {
        long counts = this.categoryRepository.count();

        int count = new Random().nextInt((int) counts);

        Set<Category> categories = new HashSet<>();
        for(int i = 0; i < count; i++){
            int randomId = new Random().nextInt((int) counts) + 1;

            Optional<Category> randomCategory = this.categoryRepository.findById(randomId);
            categories.add(randomCategory.get());
        }

        product.setCategories(categories);
        return product;
    }

    private Product setRandomBuyer(Product product) {
        if(product.getPrice().compareTo(BigDecimal.valueOf(944)) > 0){
            return product;
        }

        Optional<User> buyer = getRandomSeller();
        product.setBuyer(buyer.get());

        return product;
    }

    private Optional<User> getRandomSeller() {
        long usersCount = this.userRepository.count();

        Random random = new Random();

        int randomUserId = random.nextInt((int)usersCount) + 1;

        return this.userRepository.findById(randomUserId);
    }

    private Product setRandomSeller(Product product) {
        long usersCount = this.userRepository.count();

        Random random = new Random();

        int randomUserId = random.nextInt((int)usersCount) + 1;

        Optional<User> seller = this.userRepository.findById(randomUserId);
        product.setSeller(seller.get());

        return product;
    }
}
