package io.luis.batch.cnab;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@EnableBatchProcessing
public class CnabBatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(CnabBatchApplication.class, args);
	}

}
