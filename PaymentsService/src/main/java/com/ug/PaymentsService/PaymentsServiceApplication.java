package com.ug.PaymentsService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PaymentsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentsServiceApplication.class, args);
	}

}
