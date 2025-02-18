package com.techpuram.saascore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.techpuram.saascore")
@EnableScheduling
@EnableAsync
public class SaasCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaasCoreApplication.class, args);
		
	}

}
