package com.danielabella.springcomredisv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Springcomredisv2Application {

	public static void main(String[] args) {
		SpringApplication.run(Springcomredisv2Application.class, args);
	}

}
