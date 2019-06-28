package com.qrqs.springbucks;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class QrqsSpringBucksApplication implements ApplicationRunner {
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
	}

	private void findOrders() {
	}
}
