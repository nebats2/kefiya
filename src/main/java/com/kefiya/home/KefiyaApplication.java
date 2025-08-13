package com.kefiya.home;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class KefiyaApplication {

	public static void main(String[] args) {

		SpringApplication.run(KefiyaApplication.class, args);


	}

}
