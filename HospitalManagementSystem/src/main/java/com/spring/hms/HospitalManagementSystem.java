package com.spring.hms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HospitalManagementSystem {

	public static void main(String[] args) {
		SpringApplication.run(HospitalManagementSystem.class, args);
	}

}
