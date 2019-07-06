package com.qrqs.springbucks;

import com.qrqs.springbucks.database.model.Coffee;
import com.qrqs.springbucks.database.model.Orders;
import com.qrqs.springbucks.service.CoffeeService;
import com.qrqs.springbucks.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.qrqs.springbucks.database.model.state.OrderState.INIT;
import static com.qrqs.springbucks.database.model.state.OrderState.PAID;

@SuppressWarnings({"unused"})
@SpringBootApplication
@Slf4j
public class QrqsSpringBucksApplication implements ApplicationRunner {
	@Autowired
	private JedisPoolConfig jedisPoolConfig;

	@Autowired
	private JedisPool jedisPool;

	@Autowired
	private CoffeeService coffeeService;

	@Autowired
	private OrdersService ordersService;

	public static void main(String[] args) {
		SpringApplication.run(QrqsSpringBucksApplication.class, args);
	}

	@Bean
	@ConfigurationProperties("redis")
	public JedisPoolConfig jedisPoolConfig() {
		return new JedisPoolConfig();
	}

	@Bean(destroyMethod = "close")
	public JedisPool jedisPool(@Value("${redis.host}")  String host) {
		return new JedisPool(jedisPoolConfig(), host);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("Jedis Pool Config :: {}", jedisPoolConfig);

		try (Jedis jedis = jedisPool.getResource()) {
			coffeeService.findAll().forEach(
					coffee -> jedis.hset("springbucks_menu",
											coffee.getName(),
											coffee.getPrice().getAmount().toString()
					));

			Map<String, String> menu = jedis.hgetAll("springbucks_menu");
			log.info("Menus :: {}", menu);

			String price = jedis.hget("springbucks_menu", "latte");
			log.info("latte's price is {}", Money.of(CurrencyUnit.of("CNY"), Double.parseDouble(price)));
		}
	}
}
