package com.example.shop;

import com.example.shop.model.entity.Article;
import com.example.shop.model.entity.Shop;
import com.example.shop.repository.ShopRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class ShopApplication {



	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(ShopRepository shopRepository){
		return  args -> {
			if(shopRepository.findAll().size()==0) {
				Shop shop1 = new Shop("KFC");
				shop1.addArticle(new Article(
						"Ice Cream",
						new BigDecimal(39.99),
						"images/img-001.jpg",
						shop1
				));
				shop1.addArticle(new Article(
						"Pizza Best",
						new BigDecimal(99.00),
						"images/img-002.jpg",
						shop1
				));
				shop1.addArticle(new Article(
						"Pizza Italiano",
						new BigDecimal(119.99),
						"images/img-003.jpg",
						shop1
				));
				shop1.addArticle(new Article(
						"Cake",
						new BigDecimal(75.50),
						"images/img-004.jpg",
						shop1
				));
				shopRepository.save(shop1);
				Shop shop2 = new Shop("McDonny");
				shop2.addArticle(new Article(
						"Big Pizza",
						new BigDecimal(200.00),
						"images/img-005.jpg",
						shop2
				));
				shop2.addArticle(new Article(
						"Nuts",
						new BigDecimal(66.21),
						"images/img-006.jpg",
						shop2
				));
				shop2.addArticle(new Article(
						"Burger",
						new BigDecimal(64.84),
						"images/img-007.jpg",
						shop2
				));
				shopRepository.save(shop2);
				Shop shop3 = new Shop("AFK");
				shop3.addArticle(new Article(
						"Raspberry cupcake",
						new BigDecimal(64.84),
						"images/img-008.jpg",
						shop3
				));
				shop3.addArticle(new Article(
						"Strawberry cake",
						new BigDecimal(74.99),
						"images/img-009.jpg",
						shop3
				));
				shop3.addArticle(new Article(
						"Ice lolly",
						new BigDecimal(101.01),
						"images/img-010.jpg",
						shop3
				));
				shopRepository.save(shop3);
			}
		};
	}

}
