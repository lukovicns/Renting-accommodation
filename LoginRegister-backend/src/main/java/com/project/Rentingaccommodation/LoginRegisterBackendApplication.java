package com.project.Rentingaccommodation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(excludeFilters={ @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AgentDBConfig.class) })
public class LoginRegisterBackendApplication {

	
	
	public static void main(String[] args) {
		SpringApplication.run(LoginRegisterBackendApplication.class, args);
	}
}
