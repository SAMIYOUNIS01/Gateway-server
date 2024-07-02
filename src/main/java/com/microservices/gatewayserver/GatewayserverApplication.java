package com.microservices.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}


	@Bean
	public RouteLocator microserviceRouteConfiguration(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/myapp/accounts/**")
						.filters(f -> f.rewritePath("/myapp/accounts/(?<segment>.*)" , "/${segment}")
								.addResponseHeader("X-Response-Time" , LocalDateTime.now().toString()))
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/myapp/loans/**")
						.filters(f -> f.rewritePath("/myapp/loans/(?<segment>.*)" , "/${segment}")
								.addResponseHeader("X-Response-Time" , LocalDateTime.now().toString()))
						.uri("lb://LOANS"))
				.route(p -> p
						.path("/myapp/cards/**")
						.filters(f -> f.rewritePath("/myapp/cards/(?<segment>.*)" , "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS"))
				.build();
	}

}
