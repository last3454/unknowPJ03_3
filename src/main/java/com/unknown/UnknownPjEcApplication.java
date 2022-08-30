package com.unknown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.unknown"})
public class UnknownPjEcApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnknownPjEcApplication.class, args);
	}

}
