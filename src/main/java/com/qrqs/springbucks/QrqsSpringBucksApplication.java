package com.qrqs.springbucks;

import com.mongodb.client.result.UpdateResult;
import com.qrqs.springbucks.database.converter.MoneyReadConverter;
import com.qrqs.springbucks.database.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.*;

@SuppressWarnings({"unused"})
@SpringBootApplication
@Slf4j
public class QrqsSpringBucksApplication implements ApplicationRunner {
	@Autowired
	private MongoTemplate mongoTemplate;

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

		Coffee saved = mongoTemplate.save(espresso);
		log.info("Coffee :: {}", saved);

		log.info("All Coffee :: ");
		List<Coffee> coffeeList = mongoTemplate.find(Query.query(Criteria.where("name").is("espresso")), Coffee.class);
		Optional.ofNullable(coffeeList).ifPresent(
				coffees -> coffees.forEach(
									coffee -> log.info("\tCoffee :: {}", coffee)
				));


		Thread.sleep(1000); // 为了看更新时间
		UpdateResult updateResult = mongoTemplate.updateFirst(
				Query.query(Criteria.where("name").is("espresso")),
				new Update().set("price", Money.of(CurrencyUnit.of("CNY"), 30)).currentDate("updateTime"),
				Coffee.class);
		log.info("Update Result :: {}", updateResult.getModifiedCount());

		Coffee newUpdatedCoffee = mongoTemplate.findById(saved.getId(), Coffee.class);
		log.info("New Updated Coffee :: {}", newUpdatedCoffee);
		Optional.ofNullable(newUpdatedCoffee).ifPresent((updatedCoffee) -> mongoTemplate.remove(updatedCoffee));
	}
}
