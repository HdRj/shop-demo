package com.example.shop.service.system;

import com.example.shop.model.entity.Shop;
import com.example.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    public List<Shop> getAll(){
        return shopRepository.findAll();
    }

    public Optional<Shop> getById(Long id){
        return shopRepository.findById(id);
    }
}
