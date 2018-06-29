package com.project.Rentingaccommodation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RentingAccommodationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentingAccommodationApplication.class, args);
		
		System.setProperty("javax.net.ssl.trustStore", "backend.p12");
		System.setProperty("javax.net.ssl.trustStorePassword", "password");
	}
}
