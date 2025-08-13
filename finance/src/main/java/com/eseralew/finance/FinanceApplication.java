package com.kefiya.home;

import com.google.appengine.repackaged.org.joda.time.DateTimeZone;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import reactor.core.scheduler.Schedulers;

import java.util.TimeZone;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class FinanceApplication {

	public static void main(String[] args) {

		SpringApplication.run(FinanceApplication.class, args);


	}
@PostConstruct
	public void init() {
		// Set the default timezone to UTC for Joda-Time
		DateTimeZone.setDefault(DateTimeZone.UTC);
	// Set the default timezone to UTC
	TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@PreDestroy
	public void onShutdown() {
		Schedulers.shutdownNow();
	}
}
