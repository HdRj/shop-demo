package com.example.shop.api;

import com.example.shop.mapper.ArticleMapper;
import com.example.shop.mapper.ShopMapper;
import com.example.shop.model.dto.request.OrderRequestDTO;
import com.example.shop.model.dto.response.ArticleResponseDTO;
import com.example.shop.model.dto.response.OrderResponseDTO;
import com.example.shop.model.dto.response.ShopResponseDTO;
import com.example.shop.model.entity.Article;
import com.example.shop.model.entity.Order;
import com.example.shop.model.entity.Shop;
import com.example.shop.service.system.ArticleService;
import com.example.shop.service.system.OrderService;
import com.example.shop.service.system.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/common")
public class CommonController {
    private final ShopService shopService;
    private final ArticleService articleService;
    private final ShopMapper shopMapper;
    private final ArticleMapper articleMapper;
    private final OrderService orderService;

    @GetMapping("shop")
    public ResponseEntity<List<ShopResponseDTO>>getShops(){
        try {
            List <Shop> shops = shopService.getAll();
            return new ResponseEntity<>(shops.stream().map(item->shopMapper.toResponseDto(item)).toList(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("article/{shopId}")
    public ResponseEntity<List<ArticleResponseDTO>>getArticles(@PathVariable Long shopId){
        try {
            List <Article> articles = articleService.getAllByShopId(shopId);
            return new ResponseEntity<>(articles.stream().map(item->articleMapper.toResponseDto(item)).toList(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("article/list")
    public ResponseEntity<List<ArticleResponseDTO>>getArticlesByIds(@RequestParam List<Long> ids){
        try {
            List <Article> articles = articleService.getListByIds(ids);
            return new ResponseEntity<>(articles.stream().map(item->articleMapper.toResponseDto(item)).toList(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("order/history")
    public ResponseEntity<List<OrderResponseDTO>>getHistory(@RequestParam("email") String email, @RequestParam("phone") String phone){
        try {
            return new ResponseEntity<>(orderService.getOrdersByCustomer(email,phone), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("order")
    public ResponseEntity<Long> createOrder(@RequestBody OrderRequestDTO orderRequestDto){
        try {
            Order order = orderService.createOrder(orderRequestDto);
            return new ResponseEntity<>(order.getId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
