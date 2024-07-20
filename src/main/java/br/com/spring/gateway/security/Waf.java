package br.com.spring.gateway.security;

import br.com.spring.gateway.security.filters.LogFilter;
import br.com.spring.gateway.security.filters.PatternURIFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Waf {
    List<GatewayFilter> filtersList = List.of(
            new LogFilter(),
            new PatternURIFilter()
    );

    public List<GatewayFilter> getFiltersList() {
        return filtersList;
    }

    public void setFiltersList(List<GatewayFilter> filtersList) {
        this.filtersList = filtersList;
    }
}