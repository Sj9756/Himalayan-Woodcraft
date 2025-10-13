package com.himalayanwc.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HimalayanwcApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(HimalayanwcApiGatewayApplication.class, args);
	}

}
