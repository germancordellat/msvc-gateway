package com.prueba.msvc.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.stripPrefix;

@SpringBootApplication
public class MsvcGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcGatewayApplication.class, args);
	}

	@Bean
	RouterFunction<ServerResponse> routerConfig(){
		return route("msvc-products")
		.route(path("/api/products"), http())
		.filter((request, next) -> {
			ServerRequest requestMutated = ServerRequest.from(request)
				.header("message-request", "Hello World in Request").build();
			ServerResponse respones = next.handle(requestMutated);
			respones.headers().add("message-response", "Hello World in Response");
			return respones;
		})
		.filter(lb("msvc-products"))
	    .filter(circuitBreaker(config -> config
			.setId("products")
			.setStatusCodes("500")
			.setFallbackPath("forward:/api/items/5")))
			.before(stripPrefix(2)).build();
	}

}
