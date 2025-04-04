package com.handmadeMarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HandmadeMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(HandmadeMarketApplication.class, args);
	}

}
