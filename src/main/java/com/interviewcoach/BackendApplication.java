package com.interviewcoach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void applicationReady() {
		System.out.println("\n=== APPLICATION SUCCESSFULLY LAUNCHED ===");
		System.out.println("Interview Coach Backend is running on http://localhost:8080");
		System.out.println("==========================================\n");
	}

}
