package com.agl.daily_news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.agl.daily_news.model")
public class DailyNewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyNewsApplication.class, args);
	}

}
