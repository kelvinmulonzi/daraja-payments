package com.example.daraja.payment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Configuration
public class MultiTenancyConfig {

    private static final Logger logger = Logger.getLogger(MultiTenancyConfig.class.getName());

    @Bean
    public TenantContext tenantContext() {
        return new TenantContext();
    }

    @Bean
    public WebFilter tenantFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            // Extract tenant identifier - could be from header, subdomain, path, or query parameter
            // For Daraja callbacks, we might need to determine tenant from the request data

            // Example: Extract from header
            String tenantId = exchange.getRequest().getHeaders().getFirst("X-TenantId");

            // If not in header, check query parameter
            if (tenantId == null) {
                tenantId = exchange.getRequest().getQueryParams().getFirst("tenantId");
            }

            // If still not found, use default tenant
            if (tenantId == null) {
                tenantId = "default";
            }

            logger.info("Setting tenant context: " + tenantId);
            tenantContext().setTenantId(tenantId);

            return chain.filter(exchange);
        };
    }

    /**
     * Bean to store the current tenant identifier
     */
    public static class TenantContext {
        private String tenantId;

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }
    }
}