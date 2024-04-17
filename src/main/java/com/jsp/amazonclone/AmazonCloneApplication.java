package com.jsp.amazonclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class AmazonCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmazonCloneApplication.class, args);
		log.info("Application Running Successfully");
	}

}
