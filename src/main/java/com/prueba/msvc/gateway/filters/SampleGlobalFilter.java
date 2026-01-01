package com.prueba.msvc.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class SampleGlobalFilter implements GlobalFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(SampleGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        
        ServerWebExchange exchangeMutated = exchange.mutate().request(exchange.getRequest().mutate().header("token", "123456").build()).build();

        return chain.filter(exchangeMutated).then(Mono.fromRunnable(() -> {
            String token = exchangeMutated.getRequest().getHeaders().getFirst("token");
            logger.info("token: {}", token);

            exchange.getResponse().getHeaders().add("token", token != null ? token : "no-token");
            exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "blue").build());
            exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        }));
        
    }

    @Override
    public int getOrder() {
        return 100;
    }

}
