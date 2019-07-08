package com.qrqs.springbucks;

import com.qrqs.springbucks.database.converter.BytesToMoneyConverter;
import com.qrqs.springbucks.database.converter.MoneyToBytesConverter;
import com.qrqs.springbucks.database.model.Coffee;
import com.qrqs.springbucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.convert.RedisCustomConversions;

import java.util.Arrays;
import java.util.Optional;

@SuppressWarnings({"unused"})
@SpringBootApplication
@Slf4j
public class QrqsSpringBucksApplication implements ApplicationRunner {
	@Autowired
	private CoffeeService coffeeService;

	@Bean
	public RedisCustomConversions redisCustomConversions() {
		return new RedisCustomConversions(Arrays.asList(new BytesToMoneyConverter(), new MoneyToBytesConverter()));
	}

	public static void main(String[] args) {
		SpringApplication.run(QrqsSpringBucksApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Optional<Coffee> coffee = coffeeService.findCoffeeByCache("mocha");
		log.info("Coffee :: {}", coffee);

		coffeeService.findCoffeeByCache("mocha");
		coffee = coffeeService.findCoffeeByCache("mocha");

		log.info("Coffee from Redis :: {}", coffee);
	}
}
