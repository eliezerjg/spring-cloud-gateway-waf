package br.com.spring.gateway;

import br.com.spring.gateway.security.Waf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringWafApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWafApplication.class, args);
	}

	@Autowired
	public Waf waf;


	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		String intranetWebServiceUrl = "";
		return builder.routes()
				.route(p -> p
						.path("/app")
						.filters(f -> {
							f.addRequestHeader("forwarded-by-gateway", "true");
							f.filters(waf.getFiltersList());
							return f;
						})
						.uri(intranetWebServiceUrl))
				.build();
	}
}
