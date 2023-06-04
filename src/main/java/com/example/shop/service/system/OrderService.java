package com.example.shop.service.system;

import com.example.shop.enums.OrderStatus;
import com.example.shop.exceptions.ShopException;
import com.example.shop.model.dto.request.OrderItemDTO;
import com.example.shop.model.dto.request.OrderRequestDTO;
import com.example.shop.model.dto.response.OrderResponseDTO;
import com.example.shop.model.entity.*;
import com.example.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ArticleService articleService;

    public Order createOrder(OrderRequestDTO orderRequestDto) throws ShopException {
        Customer customer = customerService.getOrCreateCustomer(orderRequestDto);
        BigDecimal totalPrice = BigDecimal.ZERO;
        Shop shop = articleService.getShop(orderRequestDto.items());
        Order order = new Order(
                Calendar.getInstance(),
                OrderStatus.NEW,
                orderRequestDto.address(),
                shop,
                customer
        );

        Article article;
        BigDecimal price;
        BigDecimal sum;
        OrderItem orderItem;
        for(OrderItemDTO item: orderRequestDto.items()){
            article =  articleService.getById(item.articleId());
            price = article.getPrice();
            sum = price.multiply(BigDecimal.valueOf(item.count()));
            totalPrice = totalPrice.add(sum);
            orderItem = new OrderItem(
                    price,
                    item.count(),
                    sum,
                    article,
                    order
            );
            order.addOrderItem(orderItem);
        }
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    public List<OrderResponseDTO> getOrdersByCustomer(String email, String phone) throws ShopException {
        Customer customer = customerService.getByEmailAndPhone(email,phone);
        List <Order> orderList = orderRepository.findByCustomer(customer);
        return orderList.stream().map(item -> toOrderResponseDto(item)).toList();
    }

    private OrderResponseDTO toOrderResponseDto(Order order){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return new OrderResponseDTO(
                format.format(order.getDate().getTime()),
                order.getTotalPrice().toString(),
                order.getOrderItemList().size(),
                order.getAddress(),
                order.getStatus()
        );
    }

}
