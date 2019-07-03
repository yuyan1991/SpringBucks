package com.qrqs.springbucks;

import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings({"unused"})
@SpringBootApplication
@Slf4j
@MapperScan("com.qrqs.springbucks.database.mapper")
public class QrqsSpringBucksApplication implements ApplicationRunner {
//	@Autowired
//	private CoffeeMapper coffeeMapper;

	public static void main(String[] args) {
		SpringApplication.run(QrqsSpringBucksApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
		generateArtifacts();
//		playArtifacts();
	}

	private void generateArtifacts() throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
		List<String> warnings = new ArrayList<>();
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(
				this.getClass().getResourceAsStream("/generatorConfig.xml"));
		DefaultShellCallback callback = new DefaultShellCallback(true);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}

//	private void playArtifacts() {
//		Coffee espresso = new Coffee()
//								.withName("espresso")
//								.withPrice(Money.of(CurrencyUnit.of("CNY"), 20))
//								.withCreateTime(new Date())
//								.withUpdateTime(new Date());
//		coffeeMapper.insert(espresso);
//		log.info("Coffee espresso :: {}", espresso.toString());
//
//		Coffee latte = new Coffee()
//								.withName("latte")
//								.withPrice(Money.of(CurrencyUnit.of("CNY"), 30))
//								.withCreateTime(new Date())
//								.withUpdateTime(new Date());
//		coffeeMapper.insert(latte);
//		log.info("Coffee latte :: {}", latte.toString());
//
//		Coffee coffee = coffeeMapper.selectByPrimaryKey(1L);
//		log.info("Coffee :: {}", coffee);
//
//		CoffeeExample example = new CoffeeExample();
//		example.createCriteria().andNameEqualTo("latte");
//		List<Coffee> coffeeList = coffeeMapper.selectByExample(example);
//
//		coffeeList.forEach(res -> log.info("Coffee :: {}", res));
//	}
}
