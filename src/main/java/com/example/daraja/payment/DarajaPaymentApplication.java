package com.example.daraja.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.logging.Logger;

@SpringBootApplication
public class DarajaPaymentApplication {
	private static final Logger logger = Logger.getLogger(DarajaPaymentApplication.class.getName());

	public static void main(String[] args) {
		logger.info("Starting Daraja Payment Application...");
		ConfigurableApplicationContext context = SpringApplication.run(DarajaPaymentApplication.class, args);

		logger.info("Application started with the following beans:");
		String[] beanNames = context.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			if (beanName.contains("Router") || beanName.contains("Handler") || beanName.contains("netty") || beanName.contains("webflux")) {
				logger.info("Bean: " + beanName);
			}
		}

		logger.info("Application context type: " + context.getClass().getName());
	}
}