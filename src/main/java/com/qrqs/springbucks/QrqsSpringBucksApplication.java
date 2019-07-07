package com.qrqs.springbucks;

import com.qrqs.springbucks.database.model.Coffee;
import com.qrqs.springbucks.service.CoffeeService;
import com.qrqs.springbucks.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.List;
import java.util.Optional;

@SuppressWarnings({"unused"})
@SpringBootApplication
@Slf4j
@EnableCaching(proxyTargetClass = true)
public class QrqsSpringBucksApplication implements ApplicationRunner {
	@Autowired
	private CoffeeService coffeeService;

	@Autowired
	private OrdersService ordersService;

	public static void main(String[] args) {
		SpringApplication.run(QrqsSpringBucksApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Optional<Coffee> coffee;

		coffeeService.findAll();
		coffeeService.findByName("latte");

		log.info("Reading Cache ::");
		coffeeService.findAll();
		coffeeService.findAll();
		coffeeService.findByName("latte");
		coffeeService.findByName("espresso");

		coffeeService.reloadCoffee();
		log.info("After clearing all cache :: ");
		coffeeService.findAll();
		coffeeService.findByName("latte");
		coffeeService.findByName("espresso");

		log.info("After clearing findAll cache :: ");
		coffeeService.reloadCoffeeFindAll();
		coffeeService.findAll();
		coffeeService.findByName("latte");
		coffeeService.findByName("espresso");

		log.info("After clearing findByName cache :: ");
		coffeeService.reloadCoffeeFindByName("latte");
		coffeeService.reloadCoffeeFindByName("espresso");
		coffeeService.findAll();
		coffeeService.findByName("latte");
		coffeeService.findByName("espresso");
	}
}
