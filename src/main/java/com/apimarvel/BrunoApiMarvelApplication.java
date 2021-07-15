package com.apimarvel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BrunoApiMarvelApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrunoApiMarvelApplication.class, args);
	}

}
