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
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Optional;

import static com.qrqs.springbucks.database.model.state.OrderState.INIT;
import static com.qrqs.springbucks.database.model.state.OrderState.PAID;

@SuppressWarnings({"unused"})
@SpringBootApplication
@Slf4j
public class QrqsSpringBucksApplication implements ApplicationRunner {
	@Autowired
	private CoffeeService coffeeService;

	@Autowired
	private OrdersService ordersService;

	@Bean
	public RedisTemplate<String, Coffee> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Coffee> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

	public static void main(String[] args) {
		SpringApplication.run(QrqsSpringBucksApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<Coffee> coffeeList = coffeeService.findAll();

		coffeeService.findByName("latte");

		coffeeService.findByName("Latte");

		coffeeService.findOneCoffee("Latte");
		coffeeService.findOneCoffee("latte");

		Optional<Coffee> latte = coffeeService.findOneCoffee("Latte");
		coffeeService.findOneCoffee("espresso");

		if (latte.isPresent()) {
			Orders order = ordersService.createOrder("ziqi", latte.get());
			ordersService.updateState(order, PAID);
			ordersService.updateState(order, INIT);
		}
	}
}
