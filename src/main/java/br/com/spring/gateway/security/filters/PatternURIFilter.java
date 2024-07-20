package br.com.spring.gateway.security.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class PatternURIFilter implements GatewayFilter {


    List<String> blockedPatterns = List.of(
            "^(?!\\{\\}$).+"
    );


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String queryParams = exchange.getRequest().getQueryParams().toString();
        for (String pattern : blockedPatterns) {
            if (queryParams.matches(pattern)) {
                System.out.println(this.getClass().getName() + " - PATTERN BLOCKED -  " + " IP: " + exchange.getRequest().getRemoteAddress() + " /" +  exchange.getRequest().getMethod().name() + " " + exchange.getRequest().getURI() + " params: " + exchange.getRequest().getQueryParams());
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                byte[] bytes = "Forbidden".getBytes();
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                return exchange.getResponse().writeWith(Flux.just(buffer));
            }
        }



        return chain.filter(exchange);
    }
}
