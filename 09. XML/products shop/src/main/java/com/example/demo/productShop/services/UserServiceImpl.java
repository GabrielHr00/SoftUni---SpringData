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

    @Override
    public List<UserWithSoldProductsDTO> getUsersWithSoldProducts() {
        return null;
    }

    @Override
    public List<User> getUsersWithSoldProductsOrderByCount() {
        return null;
    }
}
