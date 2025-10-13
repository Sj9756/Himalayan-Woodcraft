package com.himalayanwc.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HimalayanwcAuthenticationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HimalayanwcAuthenticationServiceApplication.class, args);
	}

}
