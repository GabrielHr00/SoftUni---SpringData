package com.example.demo.productShop.services;

import com.example.demo.productShop.entities.User;
import com.example.demo.productShop.entities.UserWithSoldProductsDTO;
import com.example.demo.productShop.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    @Transactional
    public List<UserWithSoldProductsDTO> getUsersWithSoldProducts() {
        List<User> allWithSoldProducts = this.userRepository.findAllWithSoldProducts();
        return allWithSoldProducts.stream().map(user -> this.mapper.map(user, UserWithSoldProductsDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<User> getUsersWithSoldProductsOrderByCount() {
        List<User> allWithSoldProductsOrderByItems = this.userRepository.findAllWithSoldProductsOrderByItems();
        allWithSoldProductsOrderByItems.get(0).getSellingItems().size();
        return null;
    }
}
