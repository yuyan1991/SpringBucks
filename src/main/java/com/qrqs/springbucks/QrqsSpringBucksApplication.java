package com.qrqs.springbucks;

import com.qrqs.springbucks.database.model.Coffee;
import com.qrqs.springbucks.database.model.Orders;
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

import static com.qrqs.springbucks.database.model.state.OrderState.INIT;
import static com.qrqs.springbucks.database.model.state.OrderState.PAID;

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
		List<Coffee> coffeeList = coffeeService.findAll();

		log.info("Reading Cache ::");
		coffeeService.findAll();
		coffeeService.findAll();

		coffeeService.reloadCoffee();
		log.info("After clearing cache :: ");
		coffeeService.findAll();
	}
}
