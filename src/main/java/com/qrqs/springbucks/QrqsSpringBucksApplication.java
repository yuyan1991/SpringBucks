package com.qrqs.springbucks;

import com.qrqs.springbucks.database.converter.MoneyReadConverter;
import com.qrqs.springbucks.database.model.Coffee;
import com.qrqs.springbucks.database.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.*;

@SuppressWarnings({"unused"})
@SpringBootApplication
@Slf4j
public class QrqsSpringBucksApplication implements ApplicationRunner {
	@Autowired
	private CoffeeRepository coffeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(QrqsSpringBucksApplication.class, args);
	}

	@Bean
	public MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(Collections.singletonList(new MoneyReadConverter()));
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Coffee espresso = Coffee.builder()
								.name("espresso")
								.price(Money.of(CurrencyUnit.of("CNY"), 20))
								.createTime(new Date())
								.updateTime(new Date())
								.build();

		Coffee latte = Coffee.builder()
								.name("latte")
								.price(Money.of(CurrencyUnit.of("CNY"), 30))
								.createTime(new Date())
								.updateTime(new Date())
								.build();

		coffeeRepository.insert(Arrays.asList(espresso, latte));

		List<Coffee> coffeeList = coffeeRepository.findAll(Sort.by("name"));
		Optional.ofNullable(coffeeList).ifPresent(
				coffees -> coffees.forEach(
										(coffee) -> log.info("Coffee :: {}", coffee)
				));

		Thread.sleep(1000);
		latte.setPrice(Money.of(CurrencyUnit.of("CNY"), 35));
		latte.setUpdateTime(new Date());
		log.info("Updated latte :: {}", latte);

		coffeeRepository.save(latte);

		coffeeList = coffeeRepository.findByName("latte");
		Optional.ofNullable(coffeeList).ifPresent(
				coffees -> coffees.forEach(
						coffee -> log.info("Coffee :: {}", coffee)
				));
		coffeeRepository.deleteAll();
	}
}
