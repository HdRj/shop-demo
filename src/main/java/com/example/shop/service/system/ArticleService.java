package com.example.shop.service.system;

import com.example.shop.exceptions.ShopException;
import com.example.shop.model.dto.request.OrderItemDTO;
import com.example.shop.model.entity.Article;
import com.example.shop.model.entity.Shop;
import com.example.shop.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ShopService shopService;

    public List<Article>getAllByShop(Shop shop){
        return articleRepository.findAllByShop(shop);
    }

    public List<Article>getAllByShopId(Long shopId){
        Optional<Shop> shopOptional = shopService.getById(shopId);
        if(shopOptional.isPresent()){
            return getAllByShop(shopOptional.get());
        }else {
            return new ArrayList<>();
        }
    }

    public List<Article>getListByIds(List<Long>ids){
        return articleRepository.findByIdIn(ids);
    }

    public Article getById(Long id) throws ShopException {
        Optional<Article> articleOptional = articleRepository.findById(id);
        if(articleOptional.isPresent()){
            return articleOptional.get();
        }else {
            throw new ShopException("Article with id "+id+" doesn't exist");
        }
    }

    public Shop getShop(List<OrderItemDTO>items) throws ShopException {
        if(items.isEmpty()){
            throw new ShopException("List of items is empty");
        }
        Optional<Article> articleOptional = articleRepository.findById(items.get(0).articleId());
        if(!articleOptional.isPresent()){
            throw new ShopException("Article with id "+items.get(0).articleId()+" doesn't exist");
        }
        Shop shop = articleOptional.get().getShop();
        Shop checkShop;
        for(int i=1;i<items.size();i++){
            articleOptional = articleRepository.findById(items.get(i).articleId());
            if(!articleOptional.isPresent()){
                throw new ShopException("Article with id "+items.get(i).articleId()+" doesn't exist");
            }
            checkShop = articleOptional.get().getShop();
            if(!shop.equals(checkShop)){
                throw new ShopException("Articles are from different shops");
            }
        }
        return shop;
    }
}
