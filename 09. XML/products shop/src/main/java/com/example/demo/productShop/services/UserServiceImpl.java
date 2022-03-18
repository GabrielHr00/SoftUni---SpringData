package com.example.demo.productShop.services;

import com.example.demo.productShop.entities.ExportUserWithSoldProductsDTO;
import com.example.demo.productShop.entities.User;
import com.example.demo.productShop.entities.UserWithSoldProductsDTO;
import com.example.demo.productShop.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    @Transactional
    public ExportSellersDTO findAllWithSoldProducts() {
        List<User> users = this.userRepository.findAllWithSoldProducts();

        List<ExportUserWithSoldProductsDTO> dtos = users.stream()
                .map(e -> this.mapper.map(e, ExportUserWithSoldProductsDTO.class))
                .collect(Collectors.toList());

        return new ExportSellersDTO(dtos);
    }
}
