package com.himalayanwc.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HimalayanwcServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(HimalayanwcServiceRegistryApplication.class, args);
	}

}
