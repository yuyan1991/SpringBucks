package com.qrqs.springbucks;

import com.qrqs.springbucks.database.model.Coffee;
import com.qrqs.springbucks.database.model.Orders;
import com.qrqs.springbucks.database.repositories.CoffeeRepository;
import com.qrqs.springbucks.database.repositories.OrdersRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

@SuppressWarnings({"unused"})
@SpringBootApplication
@Slf4j
public class QrqsSpringBucksApplication implements ApplicationRunner {
	@Autowired
	private CoffeeRepository coffeeRepository;

	@Autowired
	private OrdersRepository ordersRepository;

	public static void main(String[] args) {
		SpringApplication.run(QrqsSpringBucksApplication.class, args);
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		initOrders();
		findOrders();
	}

	private void initOrders() {
		Coffee latte = Coffee
						.builder()
						.name("latte")
						.price(Money.of(CurrencyUnit.of("CNY"), 30))
						.build();
		coffeeRepository.save(latte);
		log.info("Coffee latte :: {}", latte.toString());

		Coffee espresso = Coffee
							.builder()
							.name("espresso")
							.price(Money.of(CurrencyUnit.of("CNY"), 20))
							.build();
		coffeeRepository.save(espresso);
		log.info("Coffee espresso :: {}", espresso.toString());

		Orders order = Orders
							.builder()
							.state(Orders.OrderState.TAKEN)
							.items(Collections.singletonList(latte))
							.build();
		ordersRepository.save(order);
		log.info("Order 1 :: {}", order.toString());

		order = Orders
					.builder()
					.customer("yuyan")
					.state(Orders.OrderState.INIT)
					.items(Collections.singletonList(latte))
					.build();
		ordersRepository.save(order);
		log.info("Order 2 :: {}", order.toString());

		order = Orders
					.builder()
					.customer("ziqi")
					.state(Orders.OrderState.INIT)
					.items(Arrays.asList(latte, espresso))
					.build();
		ordersRepository.save(order);
		log.info("Order 3 :: {}", order.toString());

		order = Orders
					.builder()
					.customer("furk")
					.state(Orders.OrderState.PAID)
					.items(Collections.singletonList(espresso))
					.build();
		ordersRepository.save(order);
		log.info("Order 4 :: {}", order.toString());
	}

	private void findOrders() {
	}
}
