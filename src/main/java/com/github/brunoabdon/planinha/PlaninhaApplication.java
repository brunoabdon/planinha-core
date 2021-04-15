package com.github.brunoabdon.planinha;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = HAL)
public class PlaninhaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaninhaApplication.class, args);
	}

}
