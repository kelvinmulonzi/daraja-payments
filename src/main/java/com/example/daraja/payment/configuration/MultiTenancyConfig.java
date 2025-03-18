package com.example.daraja.payment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@Configuration
public class MultiTenancyConfig implements WebMvcConfigurer {

    private static final Logger logger = Logger.getLogger(MultiTenancyConfig.class.getName());

    @Bean
    @RequestScope
    public TenantContext tenantContext() {
        return new TenantContext();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantInterceptor());
    }

    @Bean
    public HandlerInterceptor tenantInterceptor() {
        return new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                String tenantId = request.getHeader("X-Tenant-ID");
                if (tenantId != null) {
                    tenantContext().setTenantId(tenantId);
                    logger.info("Tenant ID set to: " + tenantId);
                } else {
                    logger.warning("No Tenant ID found in request");
                }
                return true;
            }
        };
    }
}