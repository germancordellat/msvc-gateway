package com.prueba.msvc.gateway.filters;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Component
public class SampleGlobalFilter implements Filter, Ordered {

    @Override
    public int getOrder() {
        return 100;
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException {
        arg2.doFilter(arg0, arg1);
    }

}
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.cloud.gateway.filter.GatewayFilterChain;
// import org.springframework.cloud.gateway.filter.GlobalFilter;
// import org.springframework.core.Ordered;
// import org.springframework.http.ResponseCookie;
// import org.springframework.stereotype.Component;
// import org.springframework.web.server.ServerWebExchange;

// import reactor.core.publisher.Mono;

// @Component
// public class SampleGlobalFilter implements GlobalFilter, Ordered {

//     private final Logger logger = LoggerFactory.getLogger(SampleGlobalFilter.class);

//     @Override
//     public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        
//         ServerWebExchange exchangeMutated = exchange.mutate().request(exchange.getRequest().mutate().header("token", "123456").build()).build();

//         return chain.filter(exchangeMutated).then(Mono.fromRunnable(() -> {
//             String token = exchangeMutated.getRequest().getHeaders().getFirst("token");
//             logger.info("token: {}", token);

//             exchange.getResponse().getHeaders().add("token", token != null ? token : "no-token");
//             exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "blue").build());
//         }));
        
//     }

//     @Override
//     public int getOrder() {
//         return 100;
//     }

// }
