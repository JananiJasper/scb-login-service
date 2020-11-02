package com.example.loginservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.example.loginservice")
@SpringBootApplication
public class LoginserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginserviceApplication.class, args);
	}

}
