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
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings({"unused"})
@SpringBootApplication
@Slf4j
public class QrqsSpringBucksApplication implements ApplicationRunner {
	@Autowired
	private CoffeeRepository coffeeRepository;

	@Autowired
	private OrdersRepository ordersRepository;

	private Function<List<Orders>, String> joinName = (coffeeInOrder) -> (coffeeInOrder.stream().map(order -> order.getCustomer()).collect(Collectors.joining(", ")));

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
		Coffee latte = Coffee.builder()
							.name("latte")
							.price(Money.of(CurrencyUnit.of("CNY"), 30.00))
							.build();
		coffeeRepository.save(latte);
		log.info("Coffee :: {}", latte);

		Coffee espresso = Coffee.builder()
								.name("espresso")
								.price(Money.of(CurrencyUnit.of("CNY"), 20))
								.build();
		coffeeRepository.save(espresso);
		log.info("Coffee :: {}", espresso);

		Orders order = Orders.builder()
							.customer("yuyan")
							.items(Collections.singletonList(latte))
							.state(Orders.OrderState.INIT)
							.build();
		ordersRepository.save(order);
		log.info("Order :: {}", order);

		order = Orders.builder()
					.customer("ziqi")
					.items(Arrays.asList(espresso, latte))
					.state(Orders.OrderState.INIT)
					.build();
		ordersRepository.save(order);
		log.info("Order :: {}", order);
	}

	private void findOrders() {
		coffeeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
						.forEach(coffee -> log.info("Coffee : {}", coffee));
		List<Orders> coffeeInOrder = ordersRepository.findTop3ByOrderByUpdateTimeDescIdAsc();
		log.info("Order List: {}", joinName.apply(coffeeInOrder));

		coffeeInOrder = ordersRepository.findByCustomerOrderByIdAsc("ziqi");
		log.info("Order List: {}", joinName.apply(coffeeInOrder));

		coffeeInOrder = ordersRepository.findByItemsName("latte");
		log.info("Order List: {}", joinName.apply(coffeeInOrder));
	}
}
