package com.giovanni.msavaliadordecredito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsavaliadordecreditoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsavaliadordecreditoApplication.class, args);
	}

}
