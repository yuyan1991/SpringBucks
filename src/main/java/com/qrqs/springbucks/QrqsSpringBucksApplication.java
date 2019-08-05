package com.qrqs.springbucks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SuppressWarnings({"unused"})
@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
public class QrqsSpringBucksApplication {
	public static void main(String[] args) {
		SpringApplication.run(QrqsSpringBucksApplication.class, args);
	}
}
