package br.com.spring.gateway.security.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LogFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println(this.getClass().getName() + ": " + " IP: " + exchange.getRequest().getRemoteAddress() + " /" +  exchange.getRequest().getMethod().name() + " " + exchange.getRequest().getURI() + " params: " + exchange.getRequest().getQueryParams());
        return chain.filter(exchange);
    }
}
