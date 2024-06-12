package com.msavaliadorcredito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AvaliadorDeCreditoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvaliadorDeCreditoApplication.class, args);
	}

}
